package gb.oo.chat.client;

import gb.oo.chat.core.AuthResponse;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.ChatMessageType;
import gb.oo.chat.core.RegisterRequest;
import gb.oo.chat.core.RegisterResponse;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUp implements Initializable, MessageListener{
    public Label errorLabel;
    public TextField login;
    public PasswordField password;
    public PasswordField rePassword;

    @Override
    public void receiveMessage(ChatMessage message) {
        System.out.println("SignUp receiveMessage message = " + message);
        if (message.getMessageType() == ChatMessageType.REGISTER_RESPONSE) {
            this.showErrorMessage(((RegisterResponse)message).getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize SingUp");
        ChatClient.getInstance().addMessageListener(this);
    }


    public void register(ActionEvent actionEvent) {
        this.errorLabel.setText("");

        if (login.getText().trim().length() == 0) {
            this.errorLabel.setText("Please, enter your login/email");
            this.login.requestFocus();
            return;
        }

        if (password.getText().trim().length() == 0) {
            this.errorLabel.setText("Please, enter password");
            this.password.requestFocus();
            return;
        }

        if (rePassword.getText().trim().length() == 0) {
            this.errorLabel.setText("Please, confirm your password");
            this.rePassword.requestFocus();
            return;
        }

        if(!password.getText().equals(rePassword.getText())){
            this.errorLabel.setText("Passwords don't match");
            this.password.requestFocus();
            return;
        }

        showErrorMessage("Registering...");
        try {
            ChatClient.getInstance().setAuthorized(false);
            ChatClient.getInstance().sendMessage(
                RegisterRequest.builder()
                    .author(login.getText().trim())
                    .password(password.getText().trim())
                    .build());
        } catch (IOException e) {
            e.printStackTrace();
            showErrorMessage("Couldn't connect to server. Please, try again");
            this.login.requestFocus();
            return;
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void signIn(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.SIGN_IN);
    }

    private void clearErrorLabel() {
        this.errorLabel.setText("");
    }

    private void showErrorMessage(String errorMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                errorLabel.setText(errorMessage);
            }
        });
    }
}
