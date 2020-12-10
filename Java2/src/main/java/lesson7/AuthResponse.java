package lesson7;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthResponse implements ChatMessage {

    private String author;
    private String nickName;
    private boolean isAccepted  = false;
    private boolean isPrivateMessage = true;
    private String recipientNickName;

    @Override
    public ChatMessageType getMessageType() {
        return ChatMessageType.AUTH_RESPONSE;
    }
}
