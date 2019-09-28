package game;

import config.XMLGameParser;
import elements.Cell;
import elements.Grid;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Visualization {
    private Game myCurrentGame;
    private Stage myStage;
    private Pane myRoot;
    private Scene myScene;
    private String[] mySimulationButtons;
    private Color myColor0;
    private Color myColor1;
    private Color myColor2;
    private int mySceneWidth;
    private int mySceneHeight;
    private int mySceneWidthWithBar;

    public Visualization(Game currentGame, Stage stage, String[] simulationButtons,
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

    protected void showSimulationScene(Grid grid, Map<Integer, Integer> recorder) {
        myRoot = new Pane();
        setBackground();
        myScene = new Scene(myRoot, mySceneWidthWithBar, mySceneHeight);
        myStage.setScene(myScene);
        myRoot.getChildren().add(createButtonsForSimulation());
        myRoot.getChildren().add(createLineChartTracker(recorder));
        //how to add chart without passing entire Simulation class to get HashMap of data
        displayGrid(grid, recorder);
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

    protected void displayGrid(Grid grid, Map<Integer, Integer> recorder){
        setCellColors(grid);
        myRoot.getChildren().clear();
        myRoot.getChildren().add(createButtonsForSimulation());
        myRoot.getChildren().add(createLineChartTracker(recorder));
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

    private void displayGridAsRectangles(Grid grid, Cell[][] cells) {
        Rectangle rectangle;
        for (int i = 0; i < grid.getNumRows(); i++) {
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

    private void displayGridAsTriangles(Grid grid, Cell[][] cells) {
        Rectangle rectangle = new Rectangle(mySceneWidth, mySceneHeight, Color.WHITE);
        rectangle.setX(0);
        rectangle.setY(0);
        myRoot.getChildren().add(rectangle);
        Polygon triangle;
        for (int i = 0; i < grid.getNumRows(); i++) {
            for (int j = 0; j< grid.getNumCols(); j++) {
                triangle = new Polygon();
                double cellSize = mySceneWidth / (grid.getNumRows());
                if (i%2==0) {
                    triangle.getPoints().addAll(new Double[] {
                            0.0, 0.0,
                            cellSize, 0.0,
                            cellSize/2, cellSize
                    });
                    triangle.setLayoutX((j) * (cellSize));
                    triangle.setLayoutY((i) * (cellSize) - cellSize);
                } else if (i%2==1) {
                    triangle.getPoints().addAll( new Double[] {
                            0.0, 0.0,
                            -cellSize/2, cellSize,
                            cellSize/2, cellSize
                    });
                    triangle.setLayoutX((j) * (cellSize));
                    triangle.setLayoutY((i) * (cellSize));
                }

                triangle.setStroke(Color.BLACK);
                if(cells[i][j].getState() == 0){
                    triangle.setFill(myColor0);
                }
                else if(cells[i][j].getState() == 1){
                    triangle.setFill(myColor1);
                }
                else{
                    triangle.setFill(myColor2);
                }
                myRoot.getChildren().add(triangle);
            }
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
        XMLGameParser parser = new XMLGameParser(new File("Resources/GameConfig.xml"));
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

    private LineChart createLineChartTracker(Map<Integer,Integer> stateRecorder){
       // VBox chartBox = createChartVBoxHelper(0,50, 50);

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (sec)");
        xAxis.setAnimated(false);
        yAxis.setLabel("Number of Cells");
        yAxis.setAnimated(false);

        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Number of Cells in a Given State");
        lineChart.setAnimated(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        //XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        //XYChart.Series<String, Number> series3 = new XYChart.Series<>();

        lineChart.getData().add(series1);
        //lineChart.getData().add(series2);
        //lineChart.getData().add(series3);


        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();

        series1.getData().add(new XYChart.Data<>(simpleDateFormat.format(date), stateRecorder.get(0)));
        //series2.getData().add(new XYChart.Data<>(simpleDateFormat.format(date), stateRecorder.get(1)));
        //series3.getData().add(new XYChart.Data<>(simpleDateFormat.format(date), stateRecorder.get(2)));

        //chartBox.getChildren().add(lineChart);

        return lineChart;

    }

    private VBox createChartVBoxHelper(int spacing, int xPos, int yPos) {
        VBox chartVBox = new VBox(spacing);
        chartVBox.setLayoutX(xPos);
        chartVBox.setLayoutY(yPos);
        return chartVBox;
    }

}

