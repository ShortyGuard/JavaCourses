package gb.oo.chat.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangeNickNameEvent implements ChatMessage {

    private String author;
    @Builder.Default
    private boolean isPrivateMessage = true;
    private String recipientNickName;

    private ChatUserProfile userProfile;

    @Override
    public ChatMessageType getMessageType() {
        return ChatMessageType.CHANGE_NICK_NAME_EVENT;
    }
}
