package simulation;

import config.XMLSimulationParser;
import elements.Cell;
import elements.Grid;

/**
 * This class represents the Game of Life Simulation. It is used by Game to run the Game of Life Simulation if that is
 * the file that the user selected.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 */
public class GameOfLifeSimulation extends Simulation {
    public static final int LIVE = 1;
    public static final int DEAD = 2;

    private XMLSimulationParser myXMLParser;
    private double minPopulationThreshold;
    private double maxPopulationThreshold;


    public GameOfLifeSimulation(Grid grid) {
        super(grid);
        myXMLParser = new XMLSimulationParser(grid.getMyConfigFile());
        minPopulationThreshold = myXMLParser.getParameters().get("min_population_threshold");
        maxPopulationThreshold = myXMLParser.getParameters().get("max_population_threshold");
    }

    /**
     * Overrides analyzeCells in the Simulation superclass and analyzes the cells for the simulation based on the specified
     * rules of the Game of Life.
     */
    @Override
    public void analyzeCells() {
        for(int id = 0; id < getGrid().getSize(); id++){
            Cell cell = getGrid().getCell(id);
                int liveNeighborsCount = countLiveNeighbors(cell.getMyNeighbors());
                if (cell.getState() == LIVE) {
                    if (liveNeighborsCount < minPopulationThreshold || liveNeighborsCount > maxPopulationThreshold) {
                        cell.setMyNextState(DEAD);
                    }
                } else if (cell.getState() == DEAD) {
                    if (liveNeighborsCount == maxPopulationThreshold) {
                        cell.setMyNextState(LIVE);
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
