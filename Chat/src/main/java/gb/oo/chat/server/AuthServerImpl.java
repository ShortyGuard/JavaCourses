package gb.oo.chat.server;

import gb.oo.chat.core.AuthServer;
import gb.oo.chat.core.ChatUserProfile;
import gb.oo.chat.core.ChatUserNotFoundException;
import gb.oo.chat.core.WrongCredentialsException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthServerImpl implements AuthServer {

    private static AuthServer instance;
    private Map<String, ChatUserProfile> users;

    private AuthServerImpl() {
        this.users = new ConcurrentHashMap<>();
        initialize();
    }

    private void initialize() {
        // TODO: 11.12.2020 тут можно сделать подгрузку зарегистрированных пользователей
    }

    public static AuthServer getInstance() {
        if (instance == null) {
            instance = new AuthServerImpl();
        }
        return instance;
    }

    @Override
    public ChatUserProfile passAuthentication(String login, String password) throws ChatUserNotFoundException {
        //любого пользователя будем авторизовывать
        // TODO: 11.12.2020 переделать на настоящую авторизацию

        ChatUserProfile user = this.users.get(login);
        if (user == null) {
            user = ChatUserProfile.builder()
                .login(login)
                .nickName(login)
                .password(password)
                .build();
            this.users.put(login, user);
        } else {
            if (!user.getLogin().equals(login) || !user.getPassword().equals(password)) {
                throw new ChatUserNotFoundException("User with such login/password was not found");
            }
        }
        return user;
    }

    @Override
    public void registerUser(String login, String password, String nickName) throws WrongCredentialsException {
        ChatUserProfile user = this.users.get(login);
        if (user == null) {
            user = ChatUserProfile.builder()
                .login(login)
                .nickName(nickName)
                .password(password)
                .build();
            this.users.put(login, user);
        } else {
            throw new WrongCredentialsException("There is no user with such login");
        }
    }

    @Override
    public void changeUserByLogin(String login, ChatUserProfile chatUserProfile)
        throws WrongCredentialsException, ChatUserNotFoundException {
        if (login == null || !login.equals(chatUserProfile.getLogin())){
            throw new WrongCredentialsException("User login is wrong");
        }
        if (this.users.get(login) != null) {
            this.users.put(login, chatUserProfile);
        } else {
            throw new WrongCredentialsException("There is no user with such login");
        }
    }
}
