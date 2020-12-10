package gb.oo.chat.client;

import gb.oo.chat.core.AuthRequest;
import gb.oo.chat.core.AuthResponse;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.ChatMessageType;
import gb.oo.chat.core.TextMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

public class ChatClient implements Runnable{

    private static ChatClient chatClient;

    private int port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    @Getter
    @Setter
    private String nickName;
    @Setter
    private boolean isRunning;
    @Getter
    @Setter
    private boolean isAuthorized;
    private ChatMessage lastAuthRequest;

    @Getter
    private boolean isConnected;

    @Setter
    private MessageListener messageListener;


    private ChatClient(int port) {
        this.port   = port;
    }

    public static ChatClient getInstance() {
        if (chatClient == null) {
            chatClient = new ChatClient(8088);
            Thread thread = new Thread(chatClient);
            thread.setDaemon(true);
            thread.start();
        }
        return chatClient;
    }

    @Override
    public void run() {
        System.out.println("ChatClient starting. Trying to connect.");
        this.isRunning = true;
        this.isAuthorized = false;
        while (this.isRunning) {
            if (!isConnected)
            {
                this.connect();
            }
            try {
                ChatMessage message = null;
                if (!isConnected) continue;
                message = (ChatMessage) inputStream.readObject();

                switch (message.getMessageType()) {
                    case AUTH_REQUEST:
                        break;
                    case AUTH_RESPONSE:
                        AuthResponse authResponse = (AuthResponse) message;
                        if (authResponse.isAccepted()) {
                            System.out.println("Received AuthResponse with accepted TRUE.");
                            this.nickName = authResponse.getNickName();
                            this.isAuthorized = true;
                        } else {
                            System.out.println("Received AuthResponse with accepted FALSE.");
                            this.isAuthorized = false;
                        }
                        break;
                    case TEXT_MESSAGE:
                        this.receiveMessage(message);
                        break;
                    case ICON_MESSAGE:
                        break;
                    case QUIT_MESSAGE:
                        break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Connection aborted. Try to reconnect");
                this.connect();
            }
        }
        this.isRunning = false;
        System.out.println("ChatClient stopped.");
    }

    private void receiveMessage(ChatMessage message) {
        System.out.println("Message received: " + message);
        if (this.messageListener != null) {
            this.messageListener.receiveMessage(message);
        }
    }

    private void connect() {
        this.closeCurrentConnection();
        System.out.println("Connecting to localhost:" + this.port);
        try {
            Socket socket = new Socket("localhost", this.port);
            System.out.print("  Getting inputStream... ");
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.print("Done\n  Getting outputStream... ");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Done\n  Client connected and ready to chat!!");
            this.isConnected = true;
            System.out.println("Connected to localhost:" + this.port);
            if (this.isAuthorized == true && this.lastAuthRequest != null) {
                System.out.println("Sending lastAuthRequest: " + this.lastAuthRequest);
                this.sendMessage(this.lastAuthRequest);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeCurrentConnection() {
        System.out.println("Closing current connection.");
        try {
            this.outputStream.close();
        } catch (IOException | NullPointerException e) {
        }
        try {
            this.inputStream.close();
        } catch (IOException | NullPointerException e) {
        }
        System.out.println("Current connection closed.");
        this.isConnected = false;
    }

    public void sendMessage(ChatMessage message) throws IOException {
        if (this.outputStream != null) {
            if (message.getMessageType() == ChatMessageType.AUTH_REQUEST) {
                this.isAuthorized = false;
                this.lastAuthRequest = message;
            }
            this.outputStream.writeObject(message);
            this.outputStream.flush();
        } else {
            System.out.println("Client has not connected yet. try later");
            throw new IOException("Client has not connected yet. try later");
        }
    }

    public boolean isOwnMessage(ChatMessage message) {
        return message.getAuthor().equals(this.nickName);
    }
}
