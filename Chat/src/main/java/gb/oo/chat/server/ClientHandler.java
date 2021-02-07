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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

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
        logger.info("ClientHandler starting...");
        this.isRunning = true;
        this.isAuthorized = false;
        try {
            logger.debug("  Getting outputStream... ");
            outputStream = new ObjectOutputStream(acceptSocket.getOutputStream());
            logger.debug("Done\n  Getting inputStream... ");
            inputStream = new ObjectInputStream(acceptSocket.getInputStream());
            logger.debug("Done\n  ClientHandler connected and ready to chat!!");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("ClientHandler couldn't get socket. Abort!");
            return;
        }
        try {

            while (this.isRunning) {
                try {
                    ChatMessage message = (ChatMessage) this.inputStream.readObject();
                    logger.debug("Read message: " + message);
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
                            this.chatServer.changeNickName(user, ((ChangeNickNameRequest)message).getNewNickName());
                            break;
                        case TEXT_MESSAGE:
                            chatServer.sendBroadcast(message);
                            break;
                        case ICON_MESSAGE:
                            break;
                        case QUIT_MESSAGE:
                            break;

                    }

                } catch (ClassNotFoundException | WrongCredentialsException | SQLException | ChatUserNotFoundException e) {
                    logger.error("Couldn't get object class. Message skiped.", e);
                } catch (IOException e) {
                    logger.error("Couldn't get object class. Message skiped.", e);
                    this.isRunning = false;
                    break;
                }
            }

        } finally {
            this.isAuthorized = false;
            try {
                logger.debug("Closing inputStream...");
                this.inputStream.close();
                logger.debug("Done\n");
            } catch (IOException e) {
                logger.debug("Error while closing inputStream", e);
            }
            try {
                logger.debug("Closing outputStream...");
                this.chatServer.unregisterClient(this);
                this.outputStream.close();
                logger.debug("Done\n");
            } catch (IOException e) {
                logger.debug("Error while closing outputStream", e);
            }
        }
        logger.info("ClientHandler stopped.");
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
