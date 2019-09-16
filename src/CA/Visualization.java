package CA;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Visualization {
    public static final int SCENE_WIDTH = 1000;
    public static final int SCENE_HEIGHT = 800;
    private Pane root;
    private Cell[][] cells;

    public Visualization(Cell[][] cells) {
        this.cells = cells;
    }

    public void setAndShowVisualizationStage(Stage stage) {
        Scene scene = setVisualizationScene();
        stage.setScene(scene);
        stage.setTitle("Cellular Automata");
        stage.setResizable(false);
        displayGrid();
        stage.show();
    }

    private void displayGrid(){
        for(Cell[] cellrow : cells){
            for(Cell cell : cellrow){
                if(cell.getMyState() == 0){
                    
                    root.getChildren().add()
                }
            }
        }
    }

    private Scene setVisualizationScene() {
        root = new Pane();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }
}
