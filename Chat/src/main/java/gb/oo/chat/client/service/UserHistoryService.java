package gb.oo.chat.client.service;

import gb.oo.chat.core.ChatMessage;
import java.io.IOException;
import java.util.List;

public interface UserHistoryService {

    void storeMessage(ChatMessage message) throws IOException;

    List<ChatMessage> loadHistory(int lastMessagesCount);
}
