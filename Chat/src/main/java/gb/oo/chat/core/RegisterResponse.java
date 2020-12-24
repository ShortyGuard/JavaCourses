package gb.oo.chat.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RegisterResponse implements ChatMessage {

    private String author;
    private String nickName;
    @Builder.Default
    private boolean isAccepted  = false;
    @Builder.Default
    private boolean isPrivateMessage = true;
    private String recipientNickName;
    private String message;

    @Override
    public ChatMessageType getMessageType() {
        return ChatMessageType.REGISTER_RESPONSE;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
            "author='" + author + '\'' +
            ", nickName='" + nickName + '\'' +
            ", isAccepted=" + isAccepted +
            ", isPrivateMessage=" + isPrivateMessage +
            ", recipientNickName='" + recipientNickName + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
