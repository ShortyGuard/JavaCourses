package lesson7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import lombok.Getter;
import lombok.Setter;

public class ClientHandler implements Runnable {
    private static int idSequence = 0;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private final EchoServer echoServer;
    private final Socket acceptSocket;

    @Getter
    @Setter
    private String clientNickName;

    @Getter
    private int clientId;

    public ClientHandler(Socket acceptSocket, EchoServer echoServer) throws IOException {
        idSequence++;
        this.clientId = idSequence;
        this.clientNickName = "User#" + idSequence;

        this.acceptSocket = acceptSocket;
        this.echoServer = echoServer;
    }

    @Override
    public void run() {
        System.out.println("ClientHandler starting...");
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
            this.sendMessage(AuthResponse.builder()
                .isAccepted(true)
                .nickName(this.clientNickName)
                .build());
            while (true) {
                try {
                    ChatMessage message = (ChatMessage) this.inputStream.readObject();
                    System.out.println("Read message: " + message);
                    switch (message.getMessageType()) {
                        case AUTH_REQUEST:
                            break;
                        case AUTH_RESPONSE:
                            break;
                        case TEXT_MESSAGE:
                            echoServer.sendBroadcast(message);
                            break;
                        case ICON_MESSAGE:
                            break;
                        case QUIT_MESSAGE:
                            break;
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("Couldn't get object class. Message skiped.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Connection reset. Stopping...");
        } finally {
            try {
                System.out.print("Closing inputStream...");
                this.inputStream.close();
                System.out.println("Done\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                System.out.print("Closing outputStream...");
                this.outputStream.close();
                System.out.println("Done\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(ChatMessage message) throws IOException {
        this.outputStream.writeObject(message);
        this.outputStream.flush();
    }
}
