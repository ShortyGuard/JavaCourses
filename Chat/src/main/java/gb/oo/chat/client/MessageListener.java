package gb.oo.chat.client;

import gb.oo.chat.core.ChatMessage;

public interface MessageListener {
    void receiveMessage(ChatMessage message);
}
