package game;

import config.XMLParser;
import elements.Cell;
import elements.Grid;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Visualization {

    private Game myCurrentGame;
    private Stage myStage;
    private Pane myRoot;
    private Scene myScene;
    private List<String> mySimulationButtons;
    private Color myColor0;
    private Color myColor1;
    private Color myColor2;
    private int mySceneWidth;
    private int mySceneHeight;
    private int mySceneWidthWithBar;

    public Visualization(Game currentGame, Stage stage, List<String> simulationButtons,
    String windowTitle, int sceneWidthWithBar, int sceneWidth, int sceneHeight) {
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
        myRoot.getChildren().add(createLoadFileButton());
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
        myColor0 = setColorForCell(cellColors[0]);
        myColor1 = setColorForCell(cellColors[1]);
        myColor2 = setColorForCell(cellColors[2]);
    }

    private Color setColorForCell(String color_chosen){
        //Arbitrary Default color
        Color color = Color.WHITE;
            if(color_chosen.equals("Blue")) {
                color = Color.BLUE;
            } else if(color_chosen.equals("Dark Blue")) {
                color = Color.DARKBLUE;
            } else if(color_chosen.equals("Black")) {
                color = Color.BLACK;
            } else if(color_chosen.equals("Green")) {
                color = Color.GREEN;
            } else if(color_chosen.equals("Red")){
                color = Color.RED;
            } else if(color_chosen.equals("Yellow")){
                color = Color.YELLOW;
            } else if(color_chosen.equals("Purple")){
                color = Color.PURPLE;
            } else if(color_chosen.equals("Light Blue")) {
                color = Color.LIGHTBLUE;
            }
            return color;
    }

    protected void displayGrid(Grid grid){

        setCellColors(grid);
        myRoot.getChildren().clear();
        myRoot.getChildren().add(createButtonsForSimulation());
        Cell[][] cells = new Cell[grid.getNumRows()][grid.getNumCols()];
        int id = 0;
        for(int i = 0; i < grid.getNumRows(); i++){
            for(int j = 0; j < grid.getNumCols(); j++){
                cells[i][j] = grid.getCell(id);
                id++;
            }
        }

        displayGridAsTriangles(grid, cells);

    }

    private void setBackground() {
        Image imageForBackground = new Image(this.getClass().getClassLoader().getResourceAsStream("images/background.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        myRoot.setBackground(background);
    }

    private Button createLoadFileButton() {
        XMLParser parser = new XMLParser("File button", new File("Resources/GameConfig.xml"));
        Button fileButton = new Button(parser.getIntroButton());
        fileButton.setPrefWidth(150);
        fileButton.setLayoutX(mySceneWidthWithBar / 2 - (150/2));
        fileButton.setLayoutY(650);
        fileButton.setOnAction(e -> {
            myCurrentGame.loadUserInputFile();
        });
        return fileButton;
    }

    private VBox createButtonsForSimulation() {
        VBox buttonsBox = createButtonsVBoxHelper(15, 850, 80);
        int buttonWidth = 100;
        for (String buttonTitle : mySimulationButtons) {
            Button simulationButton = new Button(buttonTitle);
            simulationButton.setPrefWidth(buttonWidth);
            simulationButton.setOnAction(e -> {
                if (buttonTitle.equals("Play")) {
                    myCurrentGame.playSimulation();
                } else if (buttonTitle.equals("Pause")) {
                    myCurrentGame.pauseSimulation();
                } else if (buttonTitle.equals("Skip Forward")) {
                    myCurrentGame.skipStep();
                } else if (buttonTitle.equals("Speed Up")) {
                    myCurrentGame.adjustSimulationSpeed(1);
                } else if (buttonTitle.equals("Slow Down")) {
                    myCurrentGame.adjustSimulationSpeed(-1);
                } else if (buttonTitle.equals("Reload")) {
                    myCurrentGame.loadUserInputFile();
                } else if (buttonTitle.equals("Home")) {
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

    private void displayGridAsTriangles(Grid grid, Cell[][] cells) {
        boolean flip1 = true;
        int downward = -1;
        int upward = -1;
        Rectangle rectangle = new Rectangle(mySceneWidth, mySceneHeight, Color.WHITE);
        rectangle.setX(0);
        rectangle.setY(0);
        myRoot.getChildren().add(rectangle);
        Polygon triangle;
        for (int i = 0; i < grid.getNumRows(); i++) {
            flip1 = !flip1;
            downward = -1;
            upward = -1;
                for (int j = 0; j < grid.getNumCols(); j++) {
                    System.out.println(flip1 + " " + downward + " " + upward );
                    triangle = new Polygon();
                    double cellSize = mySceneWidth / (grid.getNumRows());
                    double cellSize2 = mySceneHeight / (grid.getNumRows());

                    if(!flip1){
                        flip1 = true;
                        createDownwardTriangle(triangle, cellSize, cellSize2);
                        downward ++;
                        triangle.setLayoutY(i*cellSize2);
                        if(j%2 == 0)
                        triangle.setLayoutX(downward*cellSize);
                        else{
                            triangle.setLayoutX(downward*cellSize + cellSize/2);
                        }
                    }
                    else{
                        flip1 = false;
                        createUpwardTriangle(triangle, cellSize, cellSize2);
                        upward++;
                        triangle.setLayoutY(i*cellSize2 - cellSize2);
                        if(j%2 == 0){
                        triangle.setLayoutX(upward*cellSize);}
                        else{
                            triangle.setLayoutX(upward*cellSize + cellSize/2);}

                    }
                    System.out.println(j%2);

                    triangle.setStroke(Color.BLACK);
                    if (cells[i][j].getState() == 0) {
                        triangle.setFill(myColor0);
                    } else if (cells[i][j].getState() == 1) {
                        triangle.setFill(myColor1);
                    } else {
                        triangle.setFill(myColor2);
                    }
                    myRoot.getChildren().add(triangle);
                }
            }
        }

        private void createUpwardTriangle(Polygon triangle, double cellSize, double cellSize2){
            triangle.getPoints().addAll(cellSize/2, 0.0,
                    0.0, cellSize2,
                    cellSize, cellSize2);
        }

        private void createDownwardTriangle(Polygon triangle, double cellSize, double cellSize2){
            triangle.getPoints().addAll(0.0, 0.0,
                    cellSize, 0.0,
                    cellSize / 2, cellSize2);
        }

    }


