package gb.oo.chat.client;

import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.TextMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleChatClientStarter {
    /**
     * метод можно использовать для консольного запуска клиента
     *
     * @param args
     */
    public static void main(String[] args) {
        ChatClient chatClient = ChatClient.getInstance();
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
