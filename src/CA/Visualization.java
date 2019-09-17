//package CA;
//
//import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//
//public class Visualization {
//    public static final int SCENE_WIDTH = 1000;
//    public static final int SCENE_HEIGHT = 800;
//    private Pane root;
//    private Cell[][] cells;
//
//    public Visualization(Cell[][] cells) {
//        this.cells = cells;
//    }
//
//    public void setAndShowVisualizationStage(Stage stage) {
//        Scene scene = setVisualizationScene();
//        stage.setScene(scene);
//        stage.setTitle("Cellular Automata");
//        stage.setResizable(false);
////        displayGrid();
////        stage.show();
//    }
//
////    private void displayGrid(){
////        for(Cell[] cellrow : cells){
////            for(Cell cell : cellrow){
////                if(cell.getMyState() == 0){
////
////                    root.getChildren().add()
////                }
////            }
////        }
////    }
//
//    private Scene setVisualizationScene() {
//        root = new Pane();
//        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
//        return scene;
//    }
//}


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
                    rectangle.setFill(Color.BLACK);
                }
                else{
                    rectangle.setFill(Color.GREEN);
                }
                root.getChildren().add(rectangle);
            }
        }
    }
}

