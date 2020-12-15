package lesson7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

public class ChatClient implements Runnable {

    private final int port;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    @Getter
    @Setter
    private String nickName;
    private boolean running = true;

    public ChatClient(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("ChatClient starting. Trying to connect.");
        try (Socket socket = new Socket("localhost", this.port)) {
            System.out.print("  Getting inputStream... ");
            inputStream = new ObjectInputStream(socket.getInputStream());
            System.out.print("Done\n  Getting outputStream... ");
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Done\n  Client connected and ready to chat!!");
            while (this.running) {
                ChatMessage message = (ChatMessage) inputStream.readObject();
                switch (message.getMessageType()) {
                    case AUTH_REQUEST:
                        break;
                    case AUTH_RESPONSE:
                        AuthResponse authResponse = (AuthResponse) message;
                        if (authResponse.isAccepted()) {
                            System.out.println("Received AuthResponse with accepted TRUE.");
                            this.nickName = authResponse.getNickName();
                        } else {
                            System.out.println("Received AuthResponse with accepted FALSE. Stopping..");
                            this.running = false;
                        }
                        break;
                    case TEXT_MESSAGE:
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Read message: " + textMessage);
                        break;
                    case ICON_MESSAGE:
                        break;
                    case QUIT_MESSAGE:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(ChatMessage message) throws IOException {
        if (this.outputStream != null) {
            this.outputStream.writeObject(message);
            this.outputStream.flush();
        } else {
            System.out.println("Client has nto connected yet. try later");
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient(8088);
        Thread clientThread = new Thread(chatClient);
        clientThread.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNext()) {
                String message = scanner.nextLine();
                ChatMessage chatMessage = null;
                if (message.startsWith("/w ")) {
                    String[] command = message.split(" ");
                    chatMessage = TextMessage.builder()
                        .author(chatClient.getNickName())
                        .text(Arrays.stream(command).skip(2).collect(Collectors.joining(" ")).toString())
                        .isPrivateMessage(true)
                        .recipientNickName(command[1])
                        .build();

                } else {
                    chatMessage = TextMessage.builder()
                        .author(chatClient.getNickName())
                        .text(message)
                        .isPrivateMessage(false)
                        .build();
                }

                try {
                    chatClient.sendMessage(chatMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
