package gb.oo.chat.server;

import gb.oo.chat.core.ChatUserNotFoundException;
import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.core.WrongCredentialsException;
import gb.oo.chat.server.data.UserEntity;
import gb.oo.chat.server.data.UserRepository;
import java.io.IOException;
import java.sql.SQLException;

public class AuthServerImpl implements AuthServer {

    private static AuthServer instance;
    private UserRepository userRepository;

    private AuthServerImpl() throws ClassNotFoundException, SQLException {
        userRepository = UserRepositorySgliteImpl.getInstance();
    }

    public static AuthServer getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new AuthServerImpl();
        }
        return instance;
    }

    @Override
    public ChatUserProfile passAuthentication(String login, String password) throws ChatUserNotFoundException, SQLException {
        UserEntity userEntity = userRepository.getUserByLoginAndPassword(login, password);

        if (userEntity == null) {
            throw new ChatUserNotFoundException("User with such login/password was not found");
        }
        return UserMapper.mapToChatUserProfile(userEntity);
    }

    @Override
    public void registerUser(String login, String password, String nickName) throws WrongCredentialsException, SQLException {
        userRepository.saveUser(UserEntity.builder()
            .login(login)
            .password(password)
            .nickName(nickName)
            .build());
    }

    @Override
    public void changeChatUserNickName(String newNickName, Long id) throws ChatUserNotFoundException, SQLException {
        UserEntity userEntity = userRepository.getUserById(id);

        if (userEntity == null) {
            throw new ChatUserNotFoundException("User with id:" + id + " was not found");
        }

        userEntity.setNickName(newNickName);
        userRepository.updateUser(userEntity);
    }

    @Override
    public void close() throws IOException {
        userRepository.close();
    }
}
