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

    private int myFramesPerSecond = 1;
    private int myMillisecondDelay = 1000 / myFramesPerSecond;
    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private HashMap<String, String> myIntroButtons;
    private ArrayList<String> mySimulationButtons;

    public Game(Stage stage) {
        myTimeline = new Timeline();
        XMLParser parser = new XMLParser("Game", new File("Resources/GameConfig.xml"));
        myIntroButtons = parser.getIntroButtons();
        mySimulationButtons = parser.getSimulationButtons();
        String windowTitle = parser.getTitle();
        int sceneWidthWithBar = parser.getSceneWidthWithBar();
        int sceneWidthJustCells = parser.getSceneWidth();
        int sceneHeight = parser.getSceHeight();

        myVisualization = new Visualization(this, stage, myIntroButtons, mySimulationButtons,
                windowTitle, sceneWidthWithBar, sceneWidthJustCells, sceneHeight);
        myVisualization.showIntroScene();
    }

    protected void loadSimulation(String simulationFilePath) {
        File simulationFile = new File(simulationFilePath);
        Grid grid = new Grid(simulationFile);
        grid.configureCells();
        mySimulation = null;

        if (simulationFilePath.equals(myIntroButtons.get("Game of Life"))) {
            mySimulation = new GameOfLifeSimulation(grid);
        } else if (simulationFilePath.equals(myIntroButtons.get("Segregation"))) {
            mySimulation = new SegregationSimulation(grid);
        } else if (simulationFilePath.equals(myIntroButtons.get("Predator and Prey"))) {
            mySimulation = new PredatorPreySimulation(grid);
        } else if (simulationFilePath.equals(myIntroButtons.get("Spread of Fire"))) {
            mySimulation = new SpreadingOfFireSimulation(grid);
        } else if (simulationFilePath.equals(myIntroButtons.get("Percolation"))) {
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
