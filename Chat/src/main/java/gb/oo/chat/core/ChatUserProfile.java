package gb.oo.chat.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChatUserProfile {
    private String login;
    private String password;
    private String nickName;
}
