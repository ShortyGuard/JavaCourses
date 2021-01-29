package gb.oo.chat.server;

import com.sun.javafx.binding.StringFormatter;
import gb.oo.chat.core.AuthRequest;
import gb.oo.chat.core.AuthResponse;
import gb.oo.chat.core.ChangeNickNameEvent;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.ChatUserNotFoundException;
import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.core.RegisterRequest;
import gb.oo.chat.core.RegisterResponse;
import gb.oo.chat.core.TextMessage;
import gb.oo.chat.core.WrongCredentialsException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedDeque;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ChatServer {

    private static final Logger logger = LogManager.getLogger(ChatServer.class);

    public static final String SERVER_NAME = "ChatServer";
    private final int port;

    private final ConcurrentLinkedDeque<ClientHandler> clientHandlers = new ConcurrentLinkedDeque<>();

    @Getter
    @Setter
    private boolean isRunning;

    @Getter
    private AuthServer authServer;

    public ChatServer(int port) throws SQLException, ClassNotFoundException {
        this.port = port;
        this.authServer = AuthServerImpl.getInstance();
    }

    private void startServer() {
        logger.info("EchoServer started!!!");
        this.isRunning = true;
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (this.isRunning) {
                logger.info("EchoServer is waiting for new ChatClient...");
                Socket acceptSocket = serverSocket.accept();
                logger.info("EchoServer accepted new ChatClient...");
                ClientHandler clientHandler = new ClientHandler(acceptSocket, this);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                logger.info(StringFormatter.format("ClientHandler #%d accepted!",
                    clientHandler.getClientId()).getValue());
            }
        } catch (Exception e) {
            logger.error("Server crashed");
        }

    }

    public void sendBroadcast(ChatMessage message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            if (message.isPrivateMessage()) {
                if (clientHandler.getUser().getNickName().equals(message.getRecipientNickName())
                    || clientHandler.getUser().getNickName().equals(message.getAuthor())) {
                    clientHandler.sendMessage(message);
                }
            } else {
                clientHandler.sendMessage(message);
            }
        }
    }

    public ChatUserProfile passClientAuthentication(AuthRequest authRequest, ClientHandler clientHandler) throws IOException {
        ChatUserProfile chatUserProfile;
        try {
            chatUserProfile = this.authServer.passAuthentication(authRequest.getAuthor(), authRequest.getPassword());
        } catch (ChatUserNotFoundException | SQLException e) {
            clientHandler.sendMessage(AuthResponse.builder()
                .isAccepted(false)
                .message(e.getMessage())
                .build());
            return null;
        }

        clientHandler.sendMessage(AuthResponse.builder()
            .isAccepted(true)
            .nickName(chatUserProfile.getNickName())
            .build());
        this.clientHandlers.add(clientHandler);
        this.sendBroadcast(TextMessage.builder()
            .author(ChatServer.SERVER_NAME)
            .text("Client [" + chatUserProfile.getNickName() + "] joined to chat")
            .build());

        return chatUserProfile;
    }

    public void registerNewUser(RegisterRequest registerRequest, ClientHandler clientHandler)
        throws IOException {

        try {
            authServer.registerUser(registerRequest.getAuthor(), registerRequest.getPassword(),
                registerRequest.getAuthor());

            clientHandler.sendMessage(RegisterResponse.builder()
                .isAccepted(true)
                .message("User registered. Please, Sign In!")
                .build());

        } catch (WrongCredentialsException | SQLException e) {
            e.printStackTrace();
            clientHandler.sendMessage(RegisterResponse.builder()
                .isAccepted(false)
                .message("User with such login is already Registered. Try another login")
                .build());
        }
    }

    public void unregisterClient(ClientHandler clientHandler) throws IOException {
        this.clientHandlers.remove(clientHandler);
        this.sendBroadcast(TextMessage.builder()
            .author(ChatServer.SERVER_NAME)
            .text("Client [" + clientHandler.getUser().getNickName() + "] left chat")
            .build());
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ChatServer chatServer = new ChatServer(8088);
        chatServer.startServer();
    }

    public void changeNickName(ChatUserProfile user, String newNickName) throws ChatUserNotFoundException,
        WrongCredentialsException, SQLException, IOException {
        authServer.changeChatUserNickName(newNickName, user.getId());
        user.setNickName(newNickName);
        sendBroadcast(ChangeNickNameEvent.builder()
            .userProfile(user)
            .build());
    }
}
