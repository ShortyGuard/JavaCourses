package gb.oo.chat.client;

import gb.oo.chat.core.AuthRequest;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignIn {

    public TextField login;
    public PasswordField password;
    public Label errorLabel;
    public Button buttonSignIn;

    public void logIn(ActionEvent actionEvent) {
        this.errorLabel.setText("");

        if (login.getText().trim().length() == 0) {
            this.errorLabel.setText("Please, enter your login/email");
            this.login.requestFocus();
            return;
        }

        if (password.getText().trim().length() == 0) {
            this.errorLabel.setText("Please, enter your password");
            this.password.requestFocus();
            return;
        }

        try {
            ChatClient.getInstance().setAuthorized(false);
            ChatClient.getInstance().sendMessage(
                AuthRequest.builder()
                    .author(login.getText().trim())
                    .password(password.getText().trim())
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            this.errorLabel.setText("Couldn't connect to server. Please, try again");
            this.login.requestFocus();
            return;
        }
        Long currentTimeMillis = System.currentTimeMillis();
        while (!ChatClient.getInstance().isAuthorized()
            && (System.currentTimeMillis() - currentTimeMillis) < 10000) {//ждем авторизацию 10сек
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (ChatClient.getInstance().isAuthorized()) {
            Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.CHAT_WINDOW);
        } else {
            this.errorLabel.setText("Connection timeout. Please, try again");
            this.buttonSignIn.requestFocus();
            return;
        }
    }

    public void signUp(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.SIGN_UP);
    }
}
