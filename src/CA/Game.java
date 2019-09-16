package CA;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game extends Application {

    public static final String GAME_OF_LIFE_CONFIGURATION = "GameOfLife.txt";
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    Cell[][] myCells;
    Simulation mySimulation;
    Visualization myVisualization;

    public Game() {
        Grid grid = new Grid(GAME_OF_LIFE_CONFIGURATION);
        myCells = grid.configureCell(); // make grid and populate grid of cells
        mySimulation = new GameOfLifeSimulation(myCells); //TODO: Move once we start having scene transitions
        myVisualization = new Visualization();
    }

    @Override
    public void start(Stage stage) {
        myVisualization.setAndShowVisualizationStage(stage);
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
    }
}
