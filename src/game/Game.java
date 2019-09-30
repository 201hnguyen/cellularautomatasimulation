package game;

import config.XMLException;
import config.XMLGameParser;
import config.XMLGenerator;
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
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class Game {
    private static final String GAME_PROPERTIES = "GameProperties";

    private int myFramesPerSecond = 1;
    private int myMillisecondDelay = 1000 / myFramesPerSecond;
    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private String[] mySimulationButtons;
    private Stage myStage;
    private XMLSimulationParser mySimulationParser;
    private ResourceBundle myResources;

    public Game(Stage stage) {
        myResources = ResourceBundle.getBundle(GAME_PROPERTIES);

        myStage = stage;
        myTimeline = new Timeline();
        File gameConfig = new File("Resources/GameConfig.xml");
        if (! XMLException.isValidGameSchema(gameConfig)) {
            System.exit(0);
        }

        XMLGameParser parser = new XMLGameParser(gameConfig);
        mySimulationButtons = parser.getSimulationButtons();
        String windowTitle = parser.getTitle();
        int sceneWidthWithBar = parser.getSceneWidthFull();
        int sceneWidthJustCells = parser.getSceneWidth();
        int sceneHeight = parser.getSceneHeight();
        myVisualization = new Visualization(this, stage, mySimulationButtons, windowTitle, sceneWidthWithBar, sceneWidthJustCells, sceneHeight);
        myVisualization.showIntroScene();
    }

    protected void loadSimulation(File file) {
        File simulationFile=null;
        if (XMLException.isValidSimulationSchema(file)) {
            simulationFile = file;
        } else {
            XMLException.showInvalidSimulationAlert(myResources);
        }

        Grid grid = new Grid(simulationFile);
        try {
            grid.configureCells();
        } catch (NoSuchElementException e) {
            XMLException.showGridInconsistencyAlert(myResources);
            return;
        }
        mySimulation = null;
        mySimulationParser = new XMLSimulationParser(simulationFile); //TODO: Change simulation strings to resource file
        if (mySimulationParser.getSimulationType().equals(myResources.getString("GameOfLife"))) {
            mySimulation = new GameOfLifeSimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("Segregation"))) {
            mySimulation = new SegregationSimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("PredatorAndPrey"))) {
            mySimulation = new PredatorPreySimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("SpreadingOfFire"))) {
            mySimulation = new SpreadingOfFireSimulation(grid);
        } else if (mySimulationParser.getSimulationType().equals(myResources.getString("Percolation"))) {
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
        try {
            loadSimulation(selectedFile);
        } catch (IllegalArgumentException | NullPointerException e) {
           // do nothing
        }
    }

    protected void saveSimulationXML() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(myStage);
        XMLGenerator xmlGenerator = new XMLGenerator(file);
        xmlGenerator.generateSimulationXMLDocument();
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
