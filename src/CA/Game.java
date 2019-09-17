package CA;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Game extends Application {

    public static final String GAME_OF_LIFE_CONFIGURATION = "Resources/GameOfLifeConfig.txt";
    public static final int FRAMES_PER_SECOND = 1;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    Grid myGrid;
    Simulation mySimulation;
    Visualization myVisualization;

    public Game() {
        File sim_file = new File(GAME_OF_LIFE_CONFIGURATION);
        myGrid = new Grid(sim_file);
        myGrid.configureCells(); // make grid and populate grid of cells
        myGrid.printCells();
        System.out.println("\n\n");
        mySimulation = new GameOfLifeSimulation(myGrid); //TODO: Move once we start having scene transitions
        myVisualization = new Visualization(myGrid);
    }

    @Override
    public void start(Stage stage) {
        myVisualization.setAndShowVisualizationStage(stage);
        myVisualization.displayGrid();
        setGameLoop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void setGameLoop() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> playGameLoop(SECOND_DELAY));
        var timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void playGameLoop(double elapsedTime) {
        mySimulation.analyzeCells();
        mySimulation.updateCells();
        System.out.println("Update complete, current Simulation cells:");
        mySimulation.printCells();
        myVisualization.displayGrid();
        System.out.println("Visualization cells");
        myVisualization.printCells();
    }
}



