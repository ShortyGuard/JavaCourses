package lesson6.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient implements Runnable {

    private final String remoteHost;
    private final int remotePort;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private boolean running = true;


    public ChatClient(String host, int port) {
        this.remoteHost = host;
        this.remotePort = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(this.remoteHost, this.remotePort);

            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

            System.out.println("ChatClient started.");
            while (this.running) {
                System.out.println("ChatClient is waiting for message.");
                System.out.println(dataInputStream.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ChatClient stopped.");
    }

    public void stop() {
        this.running = false;
    }

    public void sendMessage(String message) {
        try {
            this.dataOutputStream.writeUTF(message);
            this.dataOutputStream.flush();
            System.out.println("ChatClient sent message: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}