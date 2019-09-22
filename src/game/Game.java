package game;

import config.XMLParser;
import elements.Grid;
import simulation.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public static final String GAME_OF_LIFE_CONFIGURATION = "Resources/GameOfLifeConfig.xml";
    public static final String SEGREGATION_CONFIGURATION = "Resources/SegregationConfig.xml";
    public static final String PREDATOR_PREY_CONFIGURATION = "Resources/PredatorPreyConfig.xml";
    public static final String PERCOLATION_CONFIGURATION = "Resources/PercolationConfig.xml";
    public static final String SPREADING_OF_FIRE_CONFIGURATION = "Resources/SpreadingOfFireConfig.xml";

    private int myFramesPerSecond = 1;
    private int myMillisecondDelay = 1000 / myFramesPerSecond;
    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private HashMap<String, String> mySimulationsSupported; //TODO: Read from config XML
    private ArrayList<String> mySimulationButtons; //TODO: Read from config XML

    public Game(Stage stage) {
        myTimeline = new Timeline();
        mySimulationsSupported = new HashMap<>();
        mySimulationsSupported.put("Game of Life", GAME_OF_LIFE_CONFIGURATION);
        mySimulationsSupported.put("Segregation", SEGREGATION_CONFIGURATION);
        mySimulationsSupported.put("Predator and Prey", PREDATOR_PREY_CONFIGURATION);
        mySimulationsSupported.put("Spreading of Fire", SPREADING_OF_FIRE_CONFIGURATION);
        mySimulationsSupported.put("Percolation", PERCOLATION_CONFIGURATION);

        mySimulationButtons = new ArrayList<>();
        mySimulationButtons.add("Play");
        mySimulationButtons.add("Pause");
        mySimulationButtons.add("Step forward");
        mySimulationButtons.add("Speed up");
        mySimulationButtons.add("Slow down");
        mySimulationButtons.add("Home");

        myVisualization = new Visualization(this, stage, mySimulationsSupported, mySimulationButtons);
        myVisualization.showIntroScene();
    }

    protected void loadSimulation(String simulationFilePath) {

        File simulationFile = new File(simulationFilePath);
        Grid grid = new Grid(simulationFile);
        grid.configureCells();
        mySimulation = null;

        if (simulationFilePath.equals(GAME_OF_LIFE_CONFIGURATION)) {
            mySimulation = new GameOfLifeSimulation(grid);
        } else if (simulationFilePath.equals(SEGREGATION_CONFIGURATION)) {
            mySimulation = new SegregationSimulation(grid);
        } else if (simulationFilePath.equals(PREDATOR_PREY_CONFIGURATION)) {
            mySimulation = new PredatorPreySimulation(grid);
        } else if (simulationFilePath.equals(SPREADING_OF_FIRE_CONFIGURATION)) {
            mySimulation = new SpreadingOfFireSimulation(grid);
        } else if (simulationFilePath.equals(PERCOLATION_CONFIGURATION)) {
            mySimulation = new PercolationSimulation(grid);
        }

        myVisualization.showSimulationScene(grid);
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
