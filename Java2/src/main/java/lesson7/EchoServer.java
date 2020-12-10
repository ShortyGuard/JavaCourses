package lesson7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {

    private final ServerSocket serverSocket;

    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public EchoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void startServer() throws IOException {
        System.out.println("EchoServer started!!!");

        while (true) {
            System.out.println("EchoServer is waiting for new ChatClient...");
            Socket acceptSocket = this.serverSocket.accept();
            System.out.println("EchoServer accepted new ChatClient...");
            ClientHandler clientHandler = new ClientHandler(acceptSocket, this);
            this.clientHandlers.add(clientHandler);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();

            System.out.printf("ClientHandler #%d accepted!", clientHandler.getClientId());
        }
    }

    public static void main(String[] args) {
        try {
            EchoServer echoServer = new EchoServer(8088);
            echoServer.startServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcast(ChatMessage message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            if (message.isPrivateMessage()) {
                if (clientHandler.getClientNickName().equals(message.getRecipientNickName())
                    || clientHandler.getClientNickName().equals(message.getAuthor())) {
                    clientHandler.sendMessage(message);
                }
            } else {
                clientHandler.sendMessage(message);
            }
        }
    }
}
