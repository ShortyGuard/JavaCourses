package gb.oo.chat.client;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
    private static SceneController instance;

    private HashMap<String, Scene> screenMap = new HashMap<>();

    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }

        return instance;
    }

    public void addScreen(String name, Scene pane) {
        screenMap.put(name, pane);
    }

    public void removeScreen(String name) {
        screenMap.remove(name);
    }

    public Scene getScene(String name) {
        return this.screenMap.get(name);
    }

    public void changeScene(Stage stage, SceneEnum sceneEnum){
        Scene scene = SceneController.getInstance().getScene(sceneEnum.getName());
        stage.setScene(scene);
        stage.setTitle(sceneEnum.getTitle());
    }
}