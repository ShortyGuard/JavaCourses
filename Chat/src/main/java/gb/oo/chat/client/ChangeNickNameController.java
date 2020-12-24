package gb.oo.chat.client;

import gb.oo.chat.core.TextMessage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeNickNameController {
    public TextField nickName;

    public void changeNickName(ActionEvent actionEvent) {
        try {
            ChatClient.getInstance().changeNickName(this.nickName.getText().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }

        closeStage(actionEvent);
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
