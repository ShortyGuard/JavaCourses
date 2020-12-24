package gb.oo.chat.server;

import gb.oo.chat.core.ChatUserNotFoundException;
import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.core.WrongCredentialsException;
import java.io.Closeable;
import java.sql.SQLException;

public interface AuthServer extends Closeable {

    ChatUserProfile passAuthentication(String login, String password) throws ChatUserNotFoundException, SQLException;

    void registerUser(String login, String password, String nickName) throws WrongCredentialsException, SQLException;

    void changeChatUserNickName(String newNickName, Long id) throws WrongCredentialsException,
        ChatUserNotFoundException, SQLException;
}
