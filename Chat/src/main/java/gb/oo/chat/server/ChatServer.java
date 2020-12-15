package gb.oo.chat.server;

import gb.oo.chat.core.AuthRequest;
import gb.oo.chat.core.AuthResponse;
import gb.oo.chat.core.AuthServer;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.core.ChatUserNotFoundException;
import gb.oo.chat.core.TextMessage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;
import lombok.Getter;
import lombok.Setter;

public class ChatServer {

    public static final String SERVER_NAME = "ChatServer";
    private final int port;

    private final ConcurrentLinkedDeque<ClientHandler> clientHandlers = new ConcurrentLinkedDeque<>();

    @Getter
    @Setter
    private boolean isRunning;

    @Getter
    private AuthServer authServer;

    public ChatServer(int port) {
        this.port = port;
        this.authServer = AuthServerImpl.getInstance();
    }

    private void startServer() {
        System.out.println("EchoServer started!!!");
        this.isRunning = true;
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            while (this.isRunning) {
                System.out.println("EchoServer is waiting for new ChatClient...");
                Socket acceptSocket = serverSocket.accept();
                System.out.println("EchoServer accepted new ChatClient...");
                ClientHandler clientHandler = new ClientHandler(acceptSocket, this);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();

                System.out.printf("ClientHandler #%d accepted!", clientHandler.getClientId());
            }
        } catch (Exception e) {
            System.out.println("Server crashed");
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
        } catch (ChatUserNotFoundException e) {
            clientHandler.sendMessage(AuthResponse.builder()
                .isAccepted(false)
                .message(e.getUserMessage())
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

    public void unregisterClient(ClientHandler clientHandler) throws IOException {
        this.clientHandlers.remove(clientHandler);
        this.sendBroadcast(TextMessage.builder()
            .author(ChatServer.SERVER_NAME)
            .text("Client [" + clientHandler.getUser().getNickName() + "] left chat")
            .build());
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(8088);
        chatServer.startServer();
    }
}
