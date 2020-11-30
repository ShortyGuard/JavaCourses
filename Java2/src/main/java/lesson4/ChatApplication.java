package lesson4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent signIn = FXMLLoader.load(getClass().getResource("sign_in.fxml"));
        SceneController.getInstance().addScreen(SceneEnum.SIGN_IN.getName(), new Scene(signIn));
        Parent signUp = FXMLLoader.load(getClass().getResource("sign_up.fxml"));
        SceneController.getInstance().addScreen(SceneEnum.SIGN_UP.getName(), new Scene(signUp));
        Parent chatWindow = FXMLLoader.load(getClass().getResource("chat_window.fxml"));
        SceneController.getInstance().addScreen(SceneEnum.CHAT_WINDOW.getName(), new Scene(chatWindow));

        primaryStage.setTitle(SceneEnum.SIGN_IN.getTitle());
        primaryStage.setScene(SceneController.getInstance().getScene(SceneEnum.SIGN_IN.getName()));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}