package gb.oo.chat.core;

public interface AuthServer {

    ChatUserProfile passAuthentication(String login, String password) throws ChatUserNotFoundException;

    void registerUser(String login, String password, String nickName) throws WrongCredentialsException;

    void changeUserByLogin(String login, ChatUserProfile chatUserProfile) throws WrongCredentialsException,
        ChatUserNotFoundException;
}
