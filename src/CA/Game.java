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

    public static final int FRAMES_PER_SECOND = 3;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Visualization myVisualization;
    private Timeline myTimeline;
    private HashMap<String, String> mySimulationsSupported; //TODO: Read from XML
    private ArrayList<String> mySimulationButtons;

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
        Simulation simulation = null;

        if (simulationFilePath.equals(GAME_OF_LIFE_CONFIGURATION)) {
            simulation = new GameOfLifeSimulation(grid);
        } else if (simulationFilePath.equals(SEGREGATION_CONFIGURATION)) {
            simulation = new SegregationSimulation(grid);
        } else if (simulationFilePath.equals(PREDATOR_PREY_CONFIGURATION)) {
            simulation = new PredatorPreySimulation(grid);
        } else if (simulationFilePath.equals(SPREADING_OF_FIRE_CONFIGURATION)) {
            simulation = new SpreadingOfFireSimulation(grid);
        } else if (simulationFilePath.equals(PERCOLATION_CONFIGURATION)) {
            simulation = new PercolationSimulation(grid);
        }

        myVisualization.showSimulationScene(grid, mySimulationButtons);
        setGameLoop(simulation);
    }

    public void loadIntro() {
        myTimeline.getKeyFrames().clear();
        myTimeline.stop();
        myVisualization.showIntroScene();
    }

    private void setGameLoop(Simulation simulation) {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> playGameLoop(simulation, SECOND_DELAY));
        myTimeline.setCycleCount(Timeline.INDEFINITE);
        myTimeline.getKeyFrames().add(frame);
        myTimeline.play();
    }

    private void playGameLoop(Simulation simulation, double elapsedTime) {
        simulation.analyzeCells();
        simulation.updateCells();
        myVisualization.displayGrid(simulation.getGrid());
    }
}
