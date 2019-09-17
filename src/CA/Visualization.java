package CA;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualization {
    public static final int SCENE_WIDTH = 1000;
    public static final int SCENE_HEIGHT = 800;
    public static final int CELL_SIZE = 20;
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

    public void printCells() {
        for (Cell[] cellRow : myGrid.getCells()) {
            for (Cell cell : cellRow) {
                System.out.print(cell.getState());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public void displayGrid(){
        root.getChildren().clear();
        Cell[][] cells = myGrid.getCells();
        Rectangle rectangle;
        for(int i = 0; i < myGrid.getNum_rows(); i++){
            for(int j = 0; j < myGrid.getNum_columns(); j++){
                rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setX((j) * (CELL_SIZE + 5));
                rectangle.setY((i) * (CELL_SIZE + 5));
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

