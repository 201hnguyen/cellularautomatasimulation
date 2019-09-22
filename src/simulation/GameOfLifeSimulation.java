package simulation;

import elements.Cell;
import elements.Grid;

public class GameOfLifeSimulation extends Simulation {
    public static final int LIVE = 1;
    public static final int DEAD = 2;
    public static final int MIN_POPULATION_THRESHOLD = 2; //TODO: Read from XML
    public static final int MAX_POPULATION_THRESHOLD = 3; //TODO: Read from XML

    public GameOfLifeSimulation(Grid grid) {
        super(grid);
    }

    @Override
    public void analyzeCells() {
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                int liveNeighborsCount = countLiveNeighbors(cell.getMyNeighbors());
                if (cell.getState() == LIVE) {
                    if (liveNeighborsCount < MIN_POPULATION_THRESHOLD || liveNeighborsCount > MAX_POPULATION_THRESHOLD) {
                        cell.setMyNextState(DEAD);
                    }
                } else if (cell.getState() == DEAD) {
                    if (liveNeighborsCount == MAX_POPULATION_THRESHOLD) {
                        cell.setMyNextState(LIVE);
                    }
                }
            }
        }
    }

    private int countLiveNeighbors(Cell[] neighbors) {
        int liveNeighborsCount = 0;
        for (Cell neighbor : neighbors) {
            if (neighbor.getState() == LIVE) {
                liveNeighborsCount++;
            }
        }
        return liveNeighborsCount;
    }
}
