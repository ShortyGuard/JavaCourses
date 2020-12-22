package gb.oo.chat.client;

import gb.oo.chat.client.elements.TextMessagePanel;
import gb.oo.chat.core.AuthRequest;
import gb.oo.chat.core.AuthResponse;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.ChatMessageType;
import gb.oo.chat.core.TextMessage;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SignIn implements MessageListener {

    public TextField login;
    public PasswordField password;
    public Label errorLabel;
    public Button buttonSignIn;

    @Override
    public void receiveMessage(ChatMessage message) {
        if (message.getMessageType() == ChatMessageType.AUTH_RESPONSE) {
            if (ChatClient.getInstance().isAuthorized()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage stageTheEventSourceNodeBelongs = (Stage) login.getScene().getWindow();
                        SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.CHAT_WINDOW);                    }
                });
            } else {
                showErrorMessage(((AuthResponse) message).getMessage());
                return;
            }
        }
    }

    public void initialize() {
        System.out.println("initialize SignIn");
        ChatClient.getInstance().addMessageListener(this);
    }

    public void logIn(ActionEvent actionEvent) {
        clearErrorLabel();

        if (login.getText().trim().length() == 0) {
            this.showErrorMessage("Please, enter your login/email");
            this.login.requestFocus();
            return;
        }

        if (password.getText().trim().length() == 0) {
            showErrorMessage("Please, enter your password");
            this.password.requestFocus();
            return;
        }

        showErrorMessage("Authorizing...");
        try {
            ChatClient.getInstance().setAuthorized(false);
            ChatClient.getInstance().sendMessage(
                AuthRequest.builder()
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

    public void signUp(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.SIGN_UP);
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
