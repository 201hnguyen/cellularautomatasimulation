package CA;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public static final String GAME_OF_LIFE_CONFIGURATION = "Resources/GameOfLifeConfig.txt";
    public static final String SEGREGATION_CONFIGURATION = "Resources/SegregationConfig.txt";
    public static final String PREDATOR_PREY_CONFIGURATION = "Resources/PredatorPreyConfig.txt";
    public static final String PERCOLATION_CONFIGURATION = "Resources/PercolationConfig.txt";
    public static final String SPREADING_OF_FIRE_CONFIGURATION = "Resources/SpreadingOfFireConfig.txt";

    private Visualization myVisualization;
    private Simulation mySimulation;
    private Timeline myTimeline;
    private HashMap<String, String> mySimulationsSupported; //TODO: Read from XML
    private ArrayList<String> mySimulationButtons;

    public int frames_per_second = 1;
    public int millisecond_delay = 1000 / frames_per_second;
    public double second_delay = 1.0 / frames_per_second;

    public Game(Stage stage) {
        myTimeline = new Timeline();
        mySimulationsSupported = new HashMap<>();
        mySimulationsSupported.put("Game of Life", GAME_OF_LIFE_CONFIGURATION);
        mySimulationsSupported.put("Segregation", SEGREGATION_CONFIGURATION);
        mySimulationsSupported.put("Predator and Prey", PREDATOR_PREY_CONFIGURATION);
        mySimulationsSupported.put("Spreading of Fire", SPREADING_OF_FIRE_CONFIGURATION);
        mySimulationsSupported.put("Percolation", PERCOLATION_CONFIGURATION);

        mySimulationButtons = new ArrayList<>();
        mySimulationButtons.add("Back");
        mySimulationButtons.add("Play");
        mySimulationButtons.add("Pause");
        mySimulationButtons.add("Step forward");
        mySimulationButtons.add("Speed up");
        mySimulationButtons.add("Slow down");

        myVisualization = new Visualization(this, stage, mySimulationsSupported, mySimulationButtons);
        myVisualization.showIntroScene();

    }

    public void loadSimulation(String simulationFilePath) {

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
        setGameLoop(mySimulation);
    }

    public void loadIntro() {
        myTimeline.getKeyFrames().clear();
        myTimeline.stop();
        myVisualization.showIntroScene();
    }

    private void setGameLoop(Simulation simulation) {
        var frame = new KeyFrame(Duration.millis(millisecond_delay), e -> playGameLoop(second_delay));
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
        pauseSimulation();
    }

    private void playGameLoop(double elapsedTime) {
        mySimulation.analyzeCells();
        mySimulation.updateCells();
        myVisualization.displayGrid(mySimulation.getGrid());
    }

    public void fastForwardSimulation(){
        myTimeline.stop();
        myTimeline.getKeyFrames().clear();
        frames_per_second++;
        millisecond_delay = 1000 / frames_per_second;
        second_delay = 1.0 / frames_per_second;
        var frame = new KeyFrame(Duration.millis(millisecond_delay), e -> playGameLoop(second_delay));
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
    }
    public void slowDownSimulation(){
        myTimeline.stop();
        myTimeline.getKeyFrames().clear();
        frames_per_second--;
        millisecond_delay = 1000 / frames_per_second;
        second_delay = 1.0 / frames_per_second;
        var frame = new KeyFrame(Duration.millis(millisecond_delay), e -> playGameLoop(second_delay));
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
    }

    public void pauseSimulation(){
        myTimeline.stop();
    }

    public void playSimulation(){
        myTimeline.play();
    }

    public void skipStep(){
            playGameLoop(second_delay);
    }

}
