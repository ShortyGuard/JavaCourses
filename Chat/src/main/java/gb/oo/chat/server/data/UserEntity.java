package gb.oo.chat.server.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntity {
    private Long id;
    private String login;
    private String password;
    private String nickName;
}
