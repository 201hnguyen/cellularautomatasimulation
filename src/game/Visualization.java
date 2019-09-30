package game;

import config.XMLSimulationParser;
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
import javafx.scene.shape.Shape;
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

    private Color setColorForCell(String color_chosen){ //TODO: Change color strings to resource files
        Color color = Color.WHITE;
            if(color_chosen.equals("Blue")) {
                color = Color.BLUE;
            } else if(color_chosen.equals("DarkBlue")) {
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
            } else if(color_chosen.equals("LightBlue")) {
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
        displayGridAsHexagons(grid, cells);
    }

    private void displayGridAsRectangles(Grid grid, Cell[][] cells) {
        Rectangle rectangle;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j< grid.getNumCols(); j++) {
                int cellSize = (mySceneWidth / grid.getNumRows());
                rectangle = new Rectangle(cellSize, cellSize);
                rectangle.setStroke(Color.BLACK);
                rectangle.setX((j) * (cellSize));
                rectangle.setY((i) * (cellSize));
                if(cells[i][j].getState() == 0){
                    rectangle.setFill(myColor0);
                }
                else if(cells[i][j].getState() == 1){
                    rectangle.setFill(myColor1);
                }
                else{
                    rectangle.setFill(myColor2);
                }
                myRoot.getChildren().add(rectangle);
            }
        }
    }

//    private void displayGridAsTriangles(Grid grid, Cell[][] cells) {
//        Rectangle rect = new Rectangle(mySceneWidth, mySceneHeight);
//        rect.setFill(Color.BLACK);
//        myRoot.getChildren().add(rect);
//        Polygon triangle;
//        double xPos = 0.0;
//        double yPos = 0.0;
//        double cellSize = mySceneWidth / grid.getNumCols();
//        for (int i=0; i < grid.getNumRows(); i++) {
//            for (int j=0; j<grid.getNumCols(); j++) {
//                triangle = new Polygon();
//                if (i % 2 == 0 && j%2 == 0) {
//                    triangle.getPoints().addAll(new Double[] {
//                            xPos, yPos, xPos + cellSize, yPos, xPos + cellSize / 2, yPos + cellSize
//                    });
//                    xPos += cellSize;
//                } else if (i % 2 == 0 && j % 2 == 1) {
//                    triangle.getPoints().addAll(new Double[] {
//                            xPos - cellSize / 2, yPos + cellSize, xPos + cellSize / 2, yPos + cellSize, xPos, yPos
//                    });
//                } else if (i % 2 == 0 && j % 2 == 1) {
//
//                } else {
//
//                }
//                triangle.setStroke(Color.BLACK);
//                setCellColor(cells[i][j].getState(), triangle);
//                myRoot.getChildren().add(triangle);
//            } if (i % 3 == 1) {
//                yPos += cellSize;
//                xPos = 0.0;
//            }
//        }
//    }


    private void displayGridAsTriangles(Grid grid, Cell[][] cells) {

        Rectangle rectangle = new Rectangle(mySceneWidth, mySceneHeight, Color.WHITE);
        rectangle.setX(0);
        rectangle.setY(0);
        myRoot.getChildren().add(rectangle);

        int myCounter = 1;
        int height = 0;
        int downward = 0;
        int upward = 0;
        Polygon triangle;

        double cellSize = mySceneWidth / (grid.getNumCols());
        double cellSize2 = mySceneHeight / (grid.getNumRows() / 2);

        for (int i = 0; i < grid.getNumRows(); i++, myCounter++) {
            if(myCounter == 5){
                myCounter = 1;
            }
            if(i % 2 == 0){
                height++;
                downward = 0;
                upward = 0;
            }
            for (int j = 0; j < grid.getNumCols(); j++) {
                triangle = new Polygon();
                System.out.println(myCounter);
                if (myCounter == 3 || myCounter == 4) {
                    //Create upward
                    if(j%2 == 0){
                        createDownwardTriangle(triangle, cellSize, cellSize2);
                        triangle.setLayoutX(downward * cellSize);
                        triangle.setLayoutY(height * cellSize2);
                        downward++;
                    }
                    else {
                        createUpwardTriangle(triangle, cellSize, cellSize2);
                        triangle.setLayoutX(upward * cellSize + cellSize / 2);
                        triangle.setLayoutY(height * cellSize2);
                        upward++;
                    }
                } else if (myCounter == 1 || myCounter == 2) {
                    //Create downward
                    if(j%2 == 0){
                        createUpwardTriangle(triangle, cellSize, cellSize2);
                        triangle.setLayoutX(upward * cellSize);
                        triangle.setLayoutY(height * cellSize2);
                        upward++;
                    }
                    else {
                        createDownwardTriangle(triangle, cellSize, cellSize2);
                        triangle.setLayoutX(downward * cellSize + cellSize / 2);
                        triangle.setLayoutY(height * cellSize2);
                        downward++;
                    }
                }


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

    private void displayGridAsHexagons(Grid grid, Cell[][] cells) {

        Rectangle rectangle = new Rectangle(mySceneWidth, mySceneHeight, Color.WHITE);
        rectangle.setX(0);
        rectangle.setY(0);
        myRoot.getChildren().add(rectangle);
        Polygon hexagon;
        int even_rows = -1;
        int odd_rows = -1;
        double cellHeight = (mySceneHeight / grid.getNumRows()) * 1.5;
        double cellWidth = (mySceneWidth / grid.getNumCols()) * 0.9;

        for (int i = 0; i < cells.length; i++) {
            if(i%2 == 0){
                even_rows++;
            }
            else{
                odd_rows++;
            }
            for (int j = 0; j < grid.getNumCols(); j++) {
                hexagon = new Polygon();
                hexagon.getPoints().addAll(0.0, cellHeight/3,
                        cellWidth/2, 0.0,
                        cellWidth, cellHeight/3,
                        cellWidth, 2*cellHeight/3,
                        cellWidth/2, cellHeight,
                        0.0, 2*cellHeight/3);

                hexagon.setStroke(Color.BLACK);

                if(i%2 == 0){
                    hexagon.setLayoutX((j) * (cellWidth));
                    hexagon.setLayoutY(even_rows * cellHeight);

                }
                else{
                    hexagon.setLayoutX(j * cellWidth + cellWidth/2);
                    hexagon.setLayoutY((even_rows+1) * cellHeight - cellHeight/2);
                }


                if (cells[i][j].getState() == 0) {
                    hexagon.setFill(myColor0);
                } else if (cells[i][j].getState() == 1) {
                    hexagon.setFill(myColor1);
                } else {
                    hexagon.setFill(myColor2);
                }
                myRoot.getChildren().add(hexagon);
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

    private void setCellColor(int state, Shape polygon) {
        if(state == 0){
            polygon.setFill(myColor0);
        }
        else if(state == 1){
            polygon.setFill(myColor1);
        }
        else{
            polygon.setFill(myColor2);
        }
    }


    private void setBackground() {
        Image imageForBackground = new Image(this.getClass().getClassLoader().getResourceAsStream("images/background.jpg")); //TODO: Change background string to resource files
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        myRoot.setBackground(background);
    }

    private Button createLoadFileButton() {
        XMLSimulationParser parser = new XMLSimulationParser(new File("Resources/GameConfig.xml"));
        Button fileButton = new Button(parser.getIntroButton());
        fileButton.setPrefWidth(150);
        fileButton.setLayoutX(mySceneWidthWithBar / 2 - (150/2));
        fileButton.setLayoutY(650);
        fileButton.setOnAction(e -> {
            myCurrentGame.loadUserInputFile();
        });
        return fileButton;
    }

    private VBox createButtonsForSimulation() { //TODO: change button strings to resource file
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
                } else if (buttonTitle.equals("Skip forward")) {
                    myCurrentGame.skipStep();
                } else if (buttonTitle.equals("Speed up")) {
                    myCurrentGame.adjustSimulationSpeed(1);
                } else if (buttonTitle.equals("Slow down")) {
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

}

