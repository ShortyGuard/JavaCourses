package lesson6.server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, Closeable {

    private static int cnt = 0;
    private final int connectionId;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public ClientHandler(Socket socket) throws IOException {
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        ClientHandler.cnt++;
        this.connectionId = cnt;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String messageFromClient = dataInputStream.readUTF();
                System.out.println("Received from " + connectionId + ": " + messageFromClient);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            this.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws IOException {
        dataOutputStream.close();
        dataInputStream.close();
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void sendMessage(String message) {
        try {
            this.dataOutputStream.writeUTF(message);
            this.dataOutputStream.flush();
            System.out.println("ClientHandler[" + this.connectionId + "] sent message: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}