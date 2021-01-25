package gb.oo.chat.server;

import gb.oo.chat.core.AuthRequest;
import gb.oo.chat.core.ChangeNickNameRequest;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.ChatMessageType;
import gb.oo.chat.core.ChatUserNotFoundException;
import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.core.RegisterRequest;
import gb.oo.chat.core.WrongCredentialsException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;

public class ClientHandler implements Runnable {
    private static int idSequence = 0;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final ChatServer chatServer;
    private final Socket acceptSocket;

    @Getter
    private int clientId;

    @Getter
    @Setter
    private boolean isRunning;
    private boolean isAuthorized;
    @Getter
    private ChatUserProfile user;

    public ClientHandler(Socket acceptSocket, ChatServer chatServer) throws IOException {
        idSequence++;
        this.clientId = idSequence;

        this.acceptSocket = acceptSocket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        System.out.println("ClientHandler starting...");
        this.isRunning = true;
        this.isAuthorized = false;
        try {
            System.out.print("  Getting outputStream... ");
            outputStream = new ObjectOutputStream(acceptSocket.getOutputStream());
            System.out.print("Done\n  Getting inputStream... ");
            inputStream = new ObjectInputStream(acceptSocket.getInputStream());
            System.out.println("Done\n  ClientHandler connected and ready to chat!!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ClientHandler couldn't get socket. Abort!");
            return;
        }
        try {

            while (this.isRunning) {
                try {
                    ChatMessage message = (ChatMessage) this.inputStream.readObject();
                    System.out.println("Read message: " + message);
                    switch (message.getMessageType()) {
                        case AUTH_REQUEST:
                            isAuthorized = false;
                            user = this.chatServer.passClientAuthentication((AuthRequest) message, this);
                            if (user != null) {
                                isAuthorized = true;
                            }
                            break;
                        case AUTH_RESPONSE:
                            break;
                        case REGISTER_REQUEST:
                            this.chatServer.registerNewUser((RegisterRequest) message, this);
                            break;
                        case REGISTER_RESPONSE:
                            break;
                        case CHANGE_NICK_NAME_REQUEST:
                            this.chatServer.changeNickName(user, ((ChangeNickNameRequest) message).getNewNickName());
                            break;
                        case TEXT_MESSAGE:
                            chatServer.sendBroadcast(message);
                            break;
                        case ICON_MESSAGE:
                            break;
                        case QUIT_MESSAGE:
                            break;

                    }

                } catch (ClassNotFoundException | ChatUserNotFoundException | WrongCredentialsException
                    | SQLException e) {
                    System.out.println("Couldn't get object class. Message skiped.");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("Couldn't get object class. Message skiped.");
                    e.printStackTrace();
                    this.isRunning = false;
                    break;
                }
            }

        } finally {
            this.isAuthorized = false;
            try {
                System.out.print("Closing inputStream...");
                this.inputStream.close();
                System.out.println("Done\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.print("Closing outputStream...");
                this.chatServer.unregisterClient(this);
                this.outputStream.close();
                System.out.println("Done\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ClientHandler stopped.");
    }

    public void sendMessage(ChatMessage message) throws IOException {
        if (message.getMessageType() != ChatMessageType.AUTH_RESPONSE
            && message.getMessageType() != ChatMessageType.REGISTER_RESPONSE
            && !isAuthorized) {
            //надо аутентифицироваться
            return;
        }
        this.outputStream.writeObject(message);
        this.outputStream.flush();
    }
}
