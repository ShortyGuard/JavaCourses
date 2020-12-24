package gb.oo.chat.server.data;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends Closeable {

    List<UserEntity> findAll();

    UserEntity getUserById(Long id);

    UserEntity getUserByLoginAndPassword(String login, String password) throws SQLException;

    void saveUser(UserEntity userEntity) throws SQLException;

    void updateUser(UserEntity userEntity) throws SQLException;
}
