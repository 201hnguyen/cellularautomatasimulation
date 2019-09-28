package game;

import config.XMLGameParser;
import config.XMLSimulationParser;
import simulation.GameOfLifeSimulation;
import simulation.PercolationSimulation;
import simulation.PredatorPreySimulation;
import simulation.SegregationSimulation;
import simulation.SpreadingOfFireSimulation;
import simulation.Simulation;

import elements.Grid;
import javafx.stage.FileChooser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Game {

    private int myFramesPerSecond = 1;
    private int myMillisecondDelay = 1000 / myFramesPerSecond;
    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private String[] mySimulationButtons;
    private Stage myStage;

    public Game(Stage stage) {
        myStage = stage;
        myTimeline = new Timeline();
        XMLGameParser parser = new XMLGameParser(new File("Resources/GameConfig.xml"));
        mySimulationButtons = parser.getSimulationButtons();
        String windowTitle = parser.getTitle();
        int sceneWidthWithBar = parser.getSceneWidthFull();
        int sceneWidthJustCells = parser.getSceneWidth();
        int sceneHeight = parser.getSceneHeight();

        myVisualization = new Visualization(this, stage, mySimulationButtons, windowTitle, sceneWidthWithBar, sceneWidthJustCells, sceneHeight);
        myVisualization.showIntroScene();
    }

    protected void loadSimulation(File simulationFile) {
        Grid grid = new Grid(simulationFile);
        grid.configureCells();
        mySimulation = null;

        XMLSimulationParser parser = new XMLSimulationParser(simulationFile); //TODO: Change simulation strings to resource file
        if (parser.getSimulationType().equals("Game of Life")) {
            mySimulation = new GameOfLifeSimulation(grid);
        } else if (parser.getSimulationType().equals("Segregation")) {
            mySimulation = new SegregationSimulation(grid);
        } else if (parser.getSimulationType().equals("Predator and Prey")) {
            mySimulation = new PredatorPreySimulation(grid);
        } else if (parser.getSimulationType().equals("Spreading of Fire")) {
            mySimulation = new SpreadingOfFireSimulation(grid);
        } else if (parser.getSimulationType().equals("Percolation")) {
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
        if(myFramesPerSecond < 1){
            myFramesPerSecond = 1;
        }
        myMillisecondDelay = 1000 / myFramesPerSecond; //TODO: Fix error if slow it down too much there's divide by 0 error
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
