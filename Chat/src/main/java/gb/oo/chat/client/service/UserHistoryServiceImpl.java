package gb.oo.chat.client.service;

import gb.oo.chat.core.ChatMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserHistoryServiceImpl implements UserHistoryService {

    private ObjectOutputStream outputStream;

    List<ChatMessage> history = new ArrayList<>();

    public UserHistoryServiceImpl() throws IOException {

        File histroryFile = new File("userHistory.data");
        if (!histroryFile.exists()) {
            histroryFile.createNewFile();
        }
        loadHistory();
        outputStream = new ObjectOutputStream(new FileOutputStream("userHistory.data", true));
    }

    @Override
    public void storeMessage(ChatMessage message) throws IOException {
        outputStream.writeObject(message);
        outputStream.flush();
    }

    @Override
    public List<ChatMessage> loadHistory(int lastMessagesCount) {
        if (history.size() <= lastMessagesCount) {
            return history;
        }
        return history.subList(history.size() - lastMessagesCount - 1, history.size() - 1);
    }

    private void loadHistory() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userHistory.data"))) {
            ChatMessage readMessage;
            while (true) {
                readMessage = (ChatMessage) inputStream.readObject();
                history.add(readMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
