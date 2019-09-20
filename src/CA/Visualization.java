package CA;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualization {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_HEIGHT = 800;

    private Grid myGrid;
    private Pane root;

    public Visualization(Grid grid) {
        myGrid = grid;
    }

    public void setAndShowVisualizationStage(Stage stage) {
        Scene scene = setVisualizationScene();
        stage.setScene(scene);
        stage.setTitle("Cellular Automata");
        stage.setResizable(false);
        displayGrid();
        stage.show();
    }

    private Scene setVisualizationScene() {
        root = new Pane();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        return scene;
    }


    public void displayGrid(){
        root.getChildren().clear();
        Cell[][] cells = myGrid.getCells();
        Rectangle rectangle;
        for (int i=0; i < myGrid.getNum_rows(); i++) {
            for (int j=0; j< myGrid.getNum_columns(); j++) {
                int cellSize = SCENE_WIDTH / myGrid.getNum_rows();
                int spaceBetweenCells = 10;
                rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setX((j) * (cellSize + spaceBetweenCells));
                rectangle.setY((i) * (cellSize + spaceBetweenCells));
                if(cells[i][j].getState() == 0){
                    rectangle.setFill(Color.WHITE);
                }
                else if(cells[i][j].getState() == 1){
                    rectangle.setFill(Color.GREEN);
                }
                else{
                    rectangle.setFill(Color.RED);
                }
                root.getChildren().add(rectangle);
            }
        }
    }
}

