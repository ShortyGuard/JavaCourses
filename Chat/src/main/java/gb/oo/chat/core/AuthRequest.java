package gb.oo.chat.core;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthRequest implements ChatMessage {

    @NonNull
    private String author;
    @NonNull
    private String password;
    private boolean isPrivateMessage = true;
    private String recipientNickName;

    @Override
    public ChatMessageType getMessageType() {
        return ChatMessageType.AUTH_REQUEST;
    }
}
