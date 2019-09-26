package game;

import config.XMLParser;
import elements.RectangularGrid;
import javafx.stage.FileChooser;
import simulation.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

public class Game {

    private int myFramesPerSecond = 1;
    private int myMillisecondDelay = 1000 / myFramesPerSecond;
    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private List<String> mySimulationButtons;
    private Stage myStage;

    public Game(Stage stage) {
        myStage = stage;
        myTimeline = new Timeline();
        XMLParser parser = new XMLParser("Game", new File("Resources/GameConfig.xml"));
        mySimulationButtons = parser.getSimulationButtons();
        String windowTitle = parser.getTitle();
        int sceneWidthWithBar = parser.getSceneWidthWithBar();
        int sceneWidthJustCells = parser.getSceneWidth();
        int sceneHeight = parser.getSceHeight();

        myVisualization = new Visualization(this, stage, mySimulationButtons, windowTitle, sceneWidthWithBar, sceneWidthJustCells, sceneHeight);
        myVisualization.showIntroScene();
    }

    protected void loadSimulation(File simulationFile) {
        RectangularGrid rectangularGrid = new RectangularGrid(simulationFile);
        rectangularGrid.configureCells();
        mySimulation = null;

        XMLParser parser = new XMLParser("Simulation parser", simulationFile);
        if (parser.getSimulationType().equals("Game of Life")) {
            mySimulation = new GameOfLifeSimulation(rectangularGrid);
        } else if (parser.getSimulationType().equals("Segregation")) {
            mySimulation = new SegregationSimulation(rectangularGrid);
        } else if (parser.getSimulationType().equals("Predator and Prey")) {
            //mySimulation = new PredatorPreySimulation(rectangularGrid);
        } else if (parser.getSimulationType().equals("Spreading of Fire")) {
            mySimulation = new SpreadingOfFireSimulation(rectangularGrid);
        } else if (parser.getSimulationType().equals("Percolation")) {
            mySimulation = new PercolationSimulation(rectangularGrid);
        }
        myVisualization.showSimulationScene(rectangularGrid);
        setGameLoop();
    }

    protected void loadIntro() {
        myTimeline.getKeyFrames().clear();
        myTimeline.stop();
        myVisualization.showIntroScene();
    }

    protected void adjustSimulationSpeed(int value) {
        myTimeline.stop();
        myTimeline.getKeyFrames().clear();
        myFramesPerSecond += value;
        myMillisecondDelay = 1000 / myFramesPerSecond;
        var frame = new KeyFrame(Duration.millis(myMillisecondDelay), e -> playGameLoop());
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
    }

    protected void pauseSimulation(){
        myTimeline.stop();
    }

    protected void playSimulation(){
        myTimeline.play();
    }

    protected void skipStep(){
            playGameLoop();
    }

    protected void loadUserInputFile() {
        myTimeline.stop();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Resources/simulation_config_files"));
        File selectedFile  = fileChooser.showOpenDialog(myStage);
        loadSimulation(selectedFile);
    }

    private void setGameLoop() {
        var frame = new KeyFrame(Duration.millis(myMillisecondDelay), e -> playGameLoop());
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
        pauseSimulation();
    }

    private void playGameLoop() {
        mySimulation.analyzeCells();
        mySimulation.updateCells();
        myVisualization.displayGrid(mySimulation.getGrid());
    }

}
