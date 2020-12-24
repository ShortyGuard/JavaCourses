package gb.oo.chat.server;

import com.sun.javafx.binding.StringFormatter;
import gb.oo.chat.server.data.UserEntity;
import gb.oo.chat.server.data.UserRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserRepositorySgliteImpl implements UserRepository {

    private final String CREATE_USERS_TABLE  = "CREATE TABLE IF NOT EXISTS Users\n" +
        "(\n" +
        "  ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
        "  Login TEXT NOT NULL,\n" +
        "  Password TEXT NOT NULL,\n" +
        "  NickName TEXT NOT NULL\n" +
        ");";

    private final String INSERT_USER     = "INSERT INTO Users (Login, Password, NickName) VALUES ('%s', '%s' ,'%s')";

    private  final String LOAD_USER_BY_LOGIN_AND_PASSWORD   = "SELECT * FROM Users WHERE Login='%s' AND Password='%s'";

    private final String UPDATE_USER    = "UPDATE Users SET Login='%s', Password='%s', NickName='%s' WHERE ID=%d";

    private static UserRepositorySgliteImpl instance;
    Connection connection;
    Statement statement;

    private UserRepositorySgliteImpl() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection  = DriverManager.getConnection("jdbc:sqlite:chat.db");
        statement   = connection.createStatement();

        init();
    }

    private void init() throws SQLException {
        statement.execute(CREATE_USERS_TABLE);
    }

    public static UserRepositorySgliteImpl getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new UserRepositorySgliteImpl();
        }
        return instance;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public UserEntity getUserById(Long id) {
        return null;
    }

    @Override
    public UserEntity getUserByLoginAndPassword(String login, String password) throws SQLException {
        String loadStatement = StringFormatter.format(LOAD_USER_BY_LOGIN_AND_PASSWORD, login, password).getValue();
        ResultSet resultSet = statement.executeQuery(loadStatement);
        if (resultSet.next()){
            return UserEntity.builder()
                .id(resultSet.getLong("login"))
                .nickName(resultSet.getString("NickName"))
                .login(resultSet.getString("Login"))
                .password(resultSet.getString("Password"))
                .build();
        }

        return null;
    }

    @Override
    public void saveUser(UserEntity userEntity) throws SQLException {
        String insertStatement = StringFormatter.format(INSERT_USER, userEntity.getLogin(),
            userEntity.getPassword(), userEntity.getNickName()).getValue();
        statement.execute(insertStatement);
    }

    @Override
    public void updateUser(UserEntity userEntity) throws SQLException {
        String updateStatement = StringFormatter.format(UPDATE_USER, userEntity.getLogin(),
            userEntity.getPassword(), userEntity.getNickName(), userEntity.getId()).getValue();
        statement.execute(updateStatement);
    }

    @Override
    public void close() throws IOException {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
