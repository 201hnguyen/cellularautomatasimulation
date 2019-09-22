package game;

import elements.Cell;
import elements.Grid;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class Visualization {
    public static final int SCENE_WIDTH = 800; //TODO: Read from config XML
    public static final int SCENE_WIDTH_WITH_INPUT_BAR = 1000; //TODO: Read from config XML
    public static final int SCENE_HEIGHT = 800; //TODO: Read from config XML

    private Game myCurrentGame;
    private Stage myStage;
    private Pane myRoot;
    private Scene myScene;
    private HashMap<String, String> mySimulationsSupported;
    private ArrayList<String> mySimulationButtons;

    public Visualization(Game currentGame, Stage stage, HashMap<String, String> simulationsSupported, ArrayList<String> simulationButtons) {
        mySimulationsSupported = simulationsSupported;
        mySimulationButtons = simulationButtons;
        myCurrentGame = currentGame;
        myStage = stage;
        myStage.setTitle("Cellular Automata"); //TODO: Read from config XML
        myStage.setResizable(false);
        stage.show();
    }

    protected void showIntroScene() {
        myRoot = new Pane();
        setBackground();
        myScene = new Scene(myRoot, SCENE_WIDTH_WITH_INPUT_BAR, SCENE_HEIGHT);
        myRoot.getChildren().add(createButtonsForIntro());
        myStage.setScene(myScene);
    }

    protected void showSimulationScene(Grid grid) {
        myRoot = new Pane();
        setBackground();
        myScene = new Scene(myRoot, SCENE_WIDTH_WITH_INPUT_BAR, SCENE_HEIGHT);
        myStage.setScene(myScene);
        myRoot.getChildren().add(createButtonsForSimulation());
        displayGrid(grid);
    }

    protected void displayGrid(Grid grid){
        myRoot.getChildren().clear();
        myRoot.getChildren().add(createButtonsForSimulation());
        Cell[][] cells = grid.getCells();
        Rectangle rectangle;
        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j=0; j< grid.getNumCols(); j++) {
                int cellSize = (SCENE_WIDTH / grid.getNumRows());
                rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setStroke(Color.BLACK);
                rectangle.setX((j) * (cellSize));
                rectangle.setY((i) * (cellSize));
                if(cells[i][j].getState() == 0){
                    rectangle.setFill(Color.BLACK);
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

    private void setBackground() {
        Image imageForBackground = new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        myRoot.setBackground(background);
    }

    private VBox createButtonsForIntro() {
        VBox buttonsBox = createButtonsVBoxHelper(15, 835, 300);
        int buttonWidth = 130;
        for (String key : mySimulationsSupported.keySet()) {
            Button simulationButton = new Button(key);
            simulationButton.setPrefWidth(buttonWidth);
            simulationButton.setOnAction(e -> myCurrentGame.loadSimulation(mySimulationsSupported.get(key)));
            buttonsBox.getChildren().add(simulationButton);
        }
        return buttonsBox;
    }

    private VBox createButtonsForSimulation() {
        VBox buttonsBox = createButtonsVBoxHelper(15, 850, 80);
        int buttonWidth = 100;
        for (int label=0; label<mySimulationButtons.size(); label++) {
            Button simulationButton = new Button(mySimulationButtons.get(label));
            simulationButton.setPrefWidth(buttonWidth);
            int finalLabel = label;
            simulationButton.setOnAction(e -> {
                if (finalLabel == 0) {
                    myCurrentGame.playSimulation();
                } else if (finalLabel == 1) {
                    myCurrentGame.pauseSimulation();
                } else if (finalLabel == 2) {
                    myCurrentGame.skipStep();
                } else if (finalLabel == 3) {
                    myCurrentGame.adjustSimulationSpeed(1);
                } else if (finalLabel == 4) {
                    myCurrentGame.adjustSimulationSpeed(-1);
                } else if (finalLabel == 5) {
                    myCurrentGame.loadIntro();
                }
            });
            buttonsBox.getChildren().add(simulationButton);
        }
        return buttonsBox;
    }

    private VBox createButtonsVBoxHelper(int spacing, int xPos, int yPos) {
        VBox buttonsVBox = new VBox(spacing);
        buttonsVBox.setLayoutX(xPos);
        buttonsVBox.setLayoutY(yPos);
        return buttonsVBox;
    }

}
