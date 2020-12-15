package gb.oo.chat.core;

import java.io.Serializable;

public interface ChatMessage extends Serializable {

    ChatMessageType getMessageType();

    String getAuthor();

    boolean isPrivateMessage();

    String getRecipientNickName();

}
