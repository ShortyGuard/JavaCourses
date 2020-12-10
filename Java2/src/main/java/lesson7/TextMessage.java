package lesson7;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TextMessage implements ChatMessage {
    private String author;
    private String text;
    private boolean isPrivateMessage = false;
    private String recipientNickName;

    @Override
    public ChatMessageType getMessageType() {
        return ChatMessageType.TEXT_MESSAGE;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
            "author='" + author + '\'' +
            ", text='" + text + '\'' +
            ", isPrivateMessage=" + isPrivateMessage +
            ", RecipientNickName='" + recipientNickName + '\'' +
            '}';
    }
}
