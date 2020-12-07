package lesson4;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SignIn {

    public void logIn(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.CHAT_WINDOW);
    }

    public void signUp(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        SceneController.getInstance().changeScene(stageTheEventSourceNodeBelongs, SceneEnum.SIGN_UP);
    }
}
