package gb.oo.chat.client;

import gb.oo.chat.client.elements.TextMessagePanel;
import gb.oo.chat.core.ChatMessage;
import gb.oo.chat.core.TextMessage;
import gb.oo.chat.server.ClientHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChatWindow implements Initializable, MessageListener {
    public TextField messageToSend;
    public Label errorLabel;
    public VBox messagesVBox;
    public ScrollPane messagesScrollPanel;

    public void sendMessage(ActionEvent actionEvent) {

        if (this.messageToSend.getText().trim().length() > 0) {
            try {
                ChatClient.getInstance().sendMessage(TextMessage.builder()
                    .text(this.messageToSend.getText().trim())
                    .author(ChatClient.getInstance().getNickName())
                    .build());

                this.messageToSend.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize ChatWindow");
        ChatClient.getInstance().addMessageListener(this);
        this.messagesVBox.heightProperty().addListener(
            (observable, oldValue, newValue) -> {
                messagesScrollPanel.setVvalue( 1.0d );
            }
        );
    }

    @Override
    public void receiveMessage(ChatMessage message) {
        switch (message.getMessageType()) {
            case AUTH_REQUEST:
                break;
            case AUTH_RESPONSE:
                break;
            case REGISTER_REQUEST:
                break;
            case REGISTER_RESPONSE:
                break;
            case TEXT_MESSAGE:
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HBox box = new HBox();

                        TextMessagePanel textMessagePanel = new TextMessagePanel((TextMessage) message,
                            ChatClient.getInstance().isOwnMessage(message));

                        messagesVBox.getChildren().add(textMessagePanel);
                    }
                });

                break;
            case ICON_MESSAGE:
                break;
            case QUIT_MESSAGE:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }
    }

    public void openChangeNickName(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("change_nick_name.fxml"));
        Parent parent = fxmlLoader.load();
        //ChangeNickNameController changeNickNameController = fxmlLoader.getController();
       // changeNickNameController.setAppMainObservableList(tvObservableList);

        Scene scene = new Scene(parent, 480, 285);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
