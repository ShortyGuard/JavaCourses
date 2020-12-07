package lesson6.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer implements Runnable {

    Map<Integer, ClientHandler> clientHandlers = new HashMap<>();

    private int serverPort;
    private boolean started = true;

    public ChatServer(int serverPort) {
        this.serverPort = serverPort;
    }


    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(this.serverPort)) {
            System.out.println("Server started!");
            while (started) {
                System.out.println("Server is waiting connection");
                Socket socket = server.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                this.clientHandlers.put(clientHandler.getConnectionId(), clientHandler);
                new Thread(clientHandler).start();
                System.out.println("Client accepted!");
                System.out.println("Client info: " + socket.getInetAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        this.started = false;
    }

    public void sendMessage(String message) {
        String[] commands = message.split(" ");
        if (commands.length < 2) {
            System.out.println("Wrong command format. Use command  = \"[client number] message\"");
        }
        try {
            int clientConnectionId = Integer.parseInt(commands[0]);
            ClientHandler clientHandler = this.clientHandlers.get(clientConnectionId);
            if (clientHandler == null) {
                System.out.println("There is no client with number: " + clientConnectionId);
            } else {
                clientHandler.sendMessage(message.substring(commands[0].length() + 1));
            }

        } catch (NumberFormatException e) {
            System.out.println("Wrong command format. Use command  = \"[client number] message\"");
        }
    }

}
