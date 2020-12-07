package lesson6.client;

import java.util.Scanner;

public class StartClient {

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient("localhost", 8088);
        Thread clientThread = new Thread(chatClient);
        clientThread.start();

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String message = in.nextLine();
            chatClient.sendMessage(message);
        }

    }
}
