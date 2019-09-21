package CA;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashMap;

public class Visualization {
    public static final int SCENE_WIDTH = 800;
    public static final int SCENE_WIDTH_WITH_INPUT_BAR = 1000;
    public static final int SCENE_HEIGHT = 800;

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
        Image imageForBackground = new Image(this.getClass().getClassLoader().getResourceAsStream("IntroBackground.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        myRoot.setBackground(background);

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

    private Button createBackButton() {
        Button backButton = new Button("Back");
        int buttonYPos = 30;
        int buttonXPos = SCENE_WIDTH + 80;
        backButton.setLayoutX(buttonXPos);
        backButton.setLayoutY(buttonYPos);
        backButton.setOnAction( e -> {
            myCurrentGame.loadIntro();
        });
        return backButton;
    }

    public void showSimulationScene(Grid grid) {
        myRoot = new Pane();
        Scene scene = new Scene(myRoot, SCENE_WIDTH_WITH_INPUT_BAR, SCENE_HEIGHT);
        myStage.setScene(scene);
        myRoot.getChildren().add(createBackButton());
        displayGrid(grid);
    }

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

