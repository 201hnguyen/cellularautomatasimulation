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

    private Game myCurrentGame;
    private Stage myStage;
    private Pane myRoot;
    private Scene myScene;
    private HashMap<String, String> mySimulationsSupported;
    private ArrayList<String> mySimulationButtons;
    private Color color0;
    private Color color1;
    private Color color2;
    private int mySceneWidth;
    private int mySceneHeight;
    private int mySceneWidthWithBar;

    public Visualization(Game currentGame, Stage stage, HashMap<String, String> simulationsSupported, ArrayList<String> simulationButtons,
    String windowTitle, int sceneWidthWithBar, int sceneWidth, int sceneHeight) {
        mySimulationsSupported = simulationsSupported;
        mySimulationButtons = simulationButtons;
        myCurrentGame = currentGame;
        mySceneWidth = sceneWidth;
        mySceneHeight = sceneHeight;
        mySceneWidthWithBar = sceneWidthWithBar;
        myStage = stage;
        myStage.setTitle(windowTitle);
        myStage.setResizable(false);
        stage.show();
    }

    protected void showIntroScene() {
        myRoot = new Pane();
        setBackground();
        myScene = new Scene(myRoot, mySceneWidthWithBar, mySceneHeight);
        myRoot.getChildren().add(createButtonsForIntro());
        myStage.setScene(myScene);
    }

    protected void showSimulationScene(Grid grid) {
        myRoot = new Pane();
        setBackground();
        myScene = new Scene(myRoot, mySceneWidthWithBar, mySceneHeight);
        myStage.setScene(myScene);
        myRoot.getChildren().add(createButtonsForSimulation());
        displayGrid(grid);
    }

    private void setCellColors(Grid grid){
        String[] cellColors = grid.getCellColors();
        color0 = setColorForCell(cellColors[0]);
        color1 = setColorForCell(cellColors[1]);
        color2 = setColorForCell(cellColors[2]);
    }

    private Color setColorForCell(String color_chosen){
        //Arbitrary Default color
        Color color = Color.WHITE;
            if(color_chosen.equals("Blue")) {
                color = Color.BLUE;
            }
            else if(color_chosen.equals("Dark Blue")) {
                color = Color.DARKBLUE;
            }

            else if(color_chosen.equals("Black")) {
                color = Color.BLACK;
            }
            else if(color_chosen.equals("Green")) {
                color = Color.GREEN;
            }

            else if(color_chosen.equals("Red")){
                color = Color.RED;
            }

            else if(color_chosen.equals("Yellow")){
                color = Color.YELLOW;
            }
            else if(color_chosen.equals("Purple")){
                color = Color.PURPLE;
            }
            else if(color_chosen.equals("Light Blue")){
                color = Color.LIGHTBLUE;
            }

        return color;
    }

    protected void displayGrid(Grid grid){

        setCellColors(grid);
        myRoot.getChildren().clear();
        myRoot.getChildren().add(createButtonsForSimulation());
        Cell[][] cells = grid.getCells();
        Rectangle rectangle;
        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j=0; j< grid.getNumCols(); j++) {
                int cellSize = (mySceneWidth / grid.getNumRows());
                rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setStroke(Color.BLACK);
                rectangle.setX((j) * (cellSize));
                rectangle.setY((i) * (cellSize));
                if(cells[i][j].getState() == 0){
                    rectangle.setFill(color0);
                }
                else if(cells[i][j].getState() == 1){
                    rectangle.setFill(color1);
                }
                else{
                    rectangle.setFill(color2);
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

