package lesson6.server;

import java.util.Scanner;

public class StartServer {

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(8088);
        Thread threadServer = new Thread(chatServer);
        threadServer.start();

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String message = in.nextLine();
            chatServer.sendMessage(message);
        }
    }
}
