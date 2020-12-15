package gb.oo.chat.client.elements;

import gb.oo.chat.core.TextMessage;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TextMessagePanel extends VBox {
    public TextMessagePanel(TextMessage textMessage, boolean isOwn) {
        super();
        this.setPadding(new Insets(0, 0, 5, isOwn ? 0 : 5));
        this.setNodeOrientation(isOwn ? NodeOrientation.LEFT_TO_RIGHT : NodeOrientation.RIGHT_TO_LEFT);
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5, 5, 5, 5));

        hBox.setMaxWidth(500);
        hBox.getStyleClass().add(isOwn ? "ownMessageInChat" : "foreignMessageInChat");

        Text text = new Text();
        text.getStyleClass().add("chatMessage");
        text.setWrappingWidth(490);
        text.setText(textMessage.getText());

        hBox.getChildren().add(text);

        this.getChildren().add(hBox);

        HBox hBoxTime = new HBox();
        hBoxTime.setNodeOrientation(isOwn ? NodeOrientation.LEFT_TO_RIGHT : NodeOrientation.RIGHT_TO_LEFT);
        hBoxTime.setPadding(new Insets(2, 5, 0, 5));
        Label timeLabel = new Label(textMessage.getFormattedInstant());
        timeLabel.getStyleClass().add("dateMessageInChat");
        hBoxTime.getChildren().add(timeLabel);
        if (!isOwn) {
            Label nicknameLabel = new Label(textMessage.getAuthor() + " at ");
            nicknameLabel.getStyleClass().add("nickNameMessageInChat");
            hBoxTime.getChildren().add(nicknameLabel);
        }

        this.getChildren().add(hBoxTime);
    }
}
