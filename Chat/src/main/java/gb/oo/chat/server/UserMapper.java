package gb.oo.chat.server;

import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.server.data.UserEntity;

public class UserMapper {
    public static ChatUserProfile mapToChatUserProfile(UserEntity userEntity) {
        return ChatUserProfile.builder()
            .login(userEntity.getLogin())
            .nickName(userEntity.getNickName())
            .build();
    }
}
