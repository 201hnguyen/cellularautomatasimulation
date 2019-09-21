package CA;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;

public class Visualization {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_WIDTH_WITH_INPUT_BAR = 1000;
    public static final int SCENE_HEIGHT = 800;

    public static final String[] BUTTONS_PATHS = {"homebutton.png"};

    private Game myCurrentGame;
    private Stage myStage;
    private Pane myRoot;

    public Visualization(Game currentGame, Stage stage) {
        myCurrentGame = currentGame;
        myStage = stage;
        myStage.setTitle("Cellular Automata"); //TODO: Read from XML File
        myStage.setResizable(false);
        stage.show();
    }

    public void showIntroScene(HashMap<String, String> simulationsSupported) {
        myRoot = new Pane();
        Scene scene = new Scene(myRoot, SCENE_WIDTH_WITH_INPUT_BAR, SCENE_HEIGHT);
        myRoot.getChildren().add(createButtonsForIntro(simulationsSupported));
        myStage.setScene(scene);
    }

    private VBox createButtonsForIntro(HashMap<String, String> simulationsSupported) {
        int buttonsSpacing = 15;
        int vBoxX = 800;
        int vBoxY = 300;
        VBox buttonsBox = new VBox(buttonsSpacing);
        buttonsBox.setLayoutX(vBoxX);
        buttonsBox.setLayoutY(vBoxY);
        buttonsBox.setAlignment(Pos.CENTER);
        for (String key : simulationsSupported.keySet()) {
            Button simulationButton = new Button(key);
            simulationButton.setOnAction(e -> {
                myCurrentGame.loadSimulation(simulationsSupported.get(key));
            });
            buttonsBox.getChildren().add(simulationButton);
        }
        return buttonsBox;
    }

    public void showSimulationScene(Grid grid) {
        myRoot = new Pane();
        Scene scene = new Scene(myRoot, SCENE_WIDTH_WITH_INPUT_BAR, SCENE_HEIGHT);
        myStage.setScene(scene);
        myRoot.getChildren().add(createBackButton());
        displayGrid(grid);
    }

    private ImageView createBackButton() {
        ImageView backButton = readImageView(0);
        int size = 30;
        backButton.setFitWidth(size);
        backButton.setFitHeight(size);
        int xPos = ((SCENE_WIDTH + (SCENE_WIDTH_WITH_INPUT_BAR - SCENE_WIDTH) / 2) - size / 2); ;
        int yPos = 30;
        backButton.setLayoutX(xPos);
        backButton.setLayoutY(yPos);
        return backButton;
    }

    private ImageView readImageView(int buttonPathIndex) {
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BUTTONS_PATHS[buttonPathIndex]));
        return new ImageView(image);
    }

//    private Button createPlayButton() {
//
//    }

    public void displayGrid(Grid grid){ //TODO: Temporary stroke; we have to redo logic for rectangle borders (old logic doesn't work if we spread rectangles out across scene)
        myRoot.getChildren().clear();
        myRoot.getChildren().add(createBackButton());
        Cell[][] cells = grid.getCells();
        Rectangle rectangle;
        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j=0; j< grid.getNumCols(); j++) {
                int cellSize = (SCENE_WIDTH / grid.getNumRows());
                rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setStroke(Color.WHITE);
                rectangle.setX((j) * (cellSize));
                rectangle.setY((i) * (cellSize));
                if(cells[i][j].getState() == 0){
                    rectangle.setFill(Color.WHITE);
                }
                else if(cells[i][j].getState() == 1){
                    rectangle.setFill(Color.GREEN);
                }
                else{
                    rectangle.setFill(Color.RED);
                }
                myRoot.getChildren().add(rectangle);
            }
        }
    }

}

