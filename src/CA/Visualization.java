package CA;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Visualization {
    public static final int SCENE_WIDTH = 1000;
    public static final int SCENE_HEIGHT = 800;

    public Visualization() {

    }

    public void setAndShowVisualizationStage(Stage stage) {
        Scene scene = setVisualizationScene();
        stage.setScene(scene);
        stage.setTitle("Cellular Automata");
        stage.setResizable(false);
        stage.show();
    }

    private Scene setVisualizationScene() {
        Pane root = new Pane();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
}
