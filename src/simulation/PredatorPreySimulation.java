package simulation;

import config.XMLSimulationParser;
import elements.Cell;
import elements.Grid;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the Predator and Prey Simulation. It is used by Game to run the Predator and Prey Simulation if that is
 * the file that the user selected. This class executes the principles of encapsulation and abstraction in a very careful
 * and exact manner. For example, notice that the class works with the super class Simulation, and there is only one public method,
 * which is the analyzeCells method that is called in the Game class. Everything else is private helper methods that helps
 * the analyzeCells method executes its duty. As such, the encapsulation can be seen very clearly in the implementation
 * of this class and its working with the Simulation super class. In addition, the principle of abstraction is embodied
 * in every method implementation in this class. Although the implementation of the Predator and Prey simulation is quite
 * complex, looking at each method at a high level, it is always easy to understand what each method is doing because any
 * additional level of logic is refactored into a private helper method. For example, at the top level, looking at the
 * analyzeCells method, one knows that first, analyzeCells analyzes the fish cells; then, it analyzes the shark cells
 * once all fish cells have been analyzed. Then, on the next level of logic, take analyzeFishCells, for example, it is
 * quite clear to see that first, the method goes through the grid, checks neighbors for empty neighbors that it can moves
 * to, and if the there are empty neighbors, then the fish will move to the empty neighbor. One very thoughtful design
 * decision made in this method is the decision to access cells by Id. This means that, no matter what the implementation
 * of the grid passed to it from the super class is, no matter if internally, the grid is a list or hash map or 2D-array,
 * as long as the cells maintain their ids, then it is possible to iterate over them. Thus, even if we have crazy cell
 * configurations in future simulations, as long as cells have ids and defined neighbors, then this entire simulation
 * will work without needing extensive change. In addition, notice that magic numbers are very much minimized throughout
 * the class, allowing for more understandable code. The cell states, as integers, allow for a very clear understanding
 * of which cells are what. This was a design implementation that I implemented at the beginning in the Game of Life
 * Simulation  that is then adopted across all other simulations for understandability.
 * @author Ha Nguyen
 */
public class PredatorPreySimulation extends Simulation {
    public static final int EMPTY = 0;
    public static final int FISH = 1;
    public static final int SHARK = 2;
    public static final double ZERO_MOVES = 0.0;

    private XMLSimulationParser myXMLParser;
    private double myStartingSharkEnergy;
    private double mySharkEnergyGain;
    private double mySharkEnergyLoss;
    private double mySharkReproducibilityThreshold;
    private double myFishReproducibilityThreshold;
    private HashMap<Cell, Double> mySharkEnergyMap = new HashMap<>();
    private HashMap<Cell, Double> mySharkMovesMap = new HashMap<>();
    private HashMap<Cell, Double> myFishMovesMap = new HashMap<>();

    public PredatorPreySimulation(Grid grid) {
        super(grid);
        myXMLParser = new XMLSimulationParser(grid.getMyConfigFile());
        myStartingSharkEnergy = myXMLParser.getParameters().get("starting_shark_energy");
        mySharkEnergyGain = myXMLParser.getParameters().get("shark_energy_gain");
        mySharkEnergyLoss = myXMLParser.getParameters().get("shark_energy_loss");
        mySharkReproducibilityThreshold = myXMLParser.getParameters().get("shark_reproducibility_threshold");
        myFishReproducibilityThreshold = myXMLParser.getParameters().get("fish_reproducibility_threshold");

        for(int id = 0; id < getGrid().getSize(); id++){
            Cell cell = getGrid().getCell(id);
            if (cell.getState() == FISH) {
                myFishMovesMap.put(cell, ZERO_MOVES);
            } else if (cell.getState() == SHARK) {
                mySharkMovesMap.put(cell, ZERO_MOVES);
                mySharkEnergyMap.put(cell, myStartingSharkEnergy);
            }
        }
    }

    /**
     * Overrides analyzeCells in the Simulation superclass and analyzes the cells for the simulation based on the specified
     * rules of the Predator and Prey.
     */
    @Override
    public void analyzeCells() {
        clearCellAvailability();
        analyzeFishCells();

        clearCellAvailability();
        analyzeSharkCells();
    }

    private void analyzeFishCells() {
        for(int id = 0; id < getGrid().getSize(); id++){
            Cell cell = getGrid().getCell(id);
            if (cell.getState() == FISH) {
                Cell[] emptyNeighborsToMoveTo = checkNeighborsForCondition(FISH, EMPTY, cell.getMyNeighbors());
                if (emptyNeighborsToMoveTo.length != 0) {
                    moveFishToNeighbor(cell, emptyNeighborsToMoveTo);
                }
            }
        }
    }

    private void moveFishToNeighbor (Cell currentCell, Cell[] qualifyingNeighbors) {
        Cell targetCell = selectRandomNeighbor(qualifyingNeighbors);
        targetCell.setMyNextState(FISH);
        double movesToTransfer = myFishMovesMap.get(currentCell) + 1;
        if (movesToTransfer < myFishReproducibilityThreshold) {
            moveFishNoReproduce(currentCell, targetCell, movesToTransfer);
        } else {
            moveFishAndReproduce(currentCell, targetCell);
        }
        targetCell.setMyIsAvailable(false);
    }

    private void moveFishNoReproduce(Cell currentCell, Cell targetCell, double movesToTransfer) {
        transferMapValues(currentCell, targetCell,movesToTransfer, myFishMovesMap);
        currentCell.setMyNextState(EMPTY);
        targetCell.setMyNextState(FISH);
    }

    private void moveFishAndReproduce(Cell currentCell, Cell targetCell) {
        myFishMovesMap.put(currentCell, ZERO_MOVES);
        myFishMovesMap.put(targetCell, ZERO_MOVES);
        targetCell.setMyNextState(FISH);
    }

    private void analyzeSharkCells() {
        for(int id = 0; id < getGrid().getSize(); id++){
            Cell cell = getGrid().getCell(id);
            if (cell.getState() == SHARK) {
                Cell[] fishNeighborsToMoveTo = checkNeighborsForCondition(SHARK, FISH, cell.getMyNeighbors());
                Cell[] emptyNeighborsToMoveTo = checkNeighborsForCondition(SHARK, EMPTY, cell.getMyNeighbors());

                if (fishNeighborsToMoveTo.length != 0) {
                    moveSharkToNeighbor(cell, fishNeighborsToMoveTo);
                } else if (emptyNeighborsToMoveTo.length != 0) {
                    moveSharkToNeighbor(cell, emptyNeighborsToMoveTo);
                } else {
                    sharkStay(cell);
                }
            }
        }
    }

    private void sharkStay(Cell cell) {
        mySharkEnergyMap.put(cell, mySharkEnergyMap.get(cell) - mySharkEnergyLoss);
        if (! (mySharkEnergyMap.get(cell) > 0)) {
            cell.setMyNextState(EMPTY);
        }
    }

    private void moveSharkToNeighbor (Cell currentCell, Cell[] qualifyingNeighbors) {
        if (! canMove(currentCell)) {
            mySharkMovesMap.remove(currentCell);
            mySharkEnergyMap.remove(currentCell);
            currentCell.setMyNextState(EMPTY);
        } else {
            Cell targetCell = selectRandomNeighbor(qualifyingNeighbors);
            double movesToTransfer = mySharkMovesMap.get(currentCell) + 1;
            double energyToTransfer = mySharkEnergyMap.get(currentCell) - mySharkEnergyLoss;

            if (movesToTransfer < mySharkReproducibilityThreshold) {
                moveSharkNoReproduce(currentCell, targetCell, energyToTransfer, movesToTransfer);
            } else {
                moveSharkAndReproduce(currentCell, targetCell, energyToTransfer);
            }
            targetCell.setMyIsAvailable(false);
        }
    }

    private void moveSharkNoReproduce(Cell currentCell, Cell targetCell, double energyToTransfer, double movesToTransfer) {
        transferMapValues(currentCell, targetCell, energyToTransfer, mySharkEnergyMap);
        transferMapValues(currentCell, targetCell, movesToTransfer, mySharkMovesMap);

        if (targetCell.getNextState() == FISH) {
            mySharkEnergyMap.put(targetCell, mySharkEnergyMap.get(targetCell) + mySharkEnergyGain);
        }
        targetCell.setMyNextState(SHARK);
        currentCell.setMyNextState(EMPTY);
    }

    private void moveSharkAndReproduce(Cell currentCell, Cell targetCell, double energyToTransfer) {
        mySharkMovesMap.put(currentCell, ZERO_MOVES);
        mySharkMovesMap.put(targetCell, ZERO_MOVES);
        mySharkEnergyMap.put(targetCell, energyToTransfer);
        mySharkEnergyMap.put(currentCell, myStartingSharkEnergy);

        if (targetCell.getNextState() == FISH) {
            mySharkEnergyMap.put(targetCell, mySharkEnergyMap.get(targetCell) + mySharkEnergyGain);
        }
        targetCell.setMyNextState(SHARK);
    }

    private boolean canMove(Cell cell) {
        if (mySharkEnergyMap.get(cell) > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void transferMapValues(Cell currentCell, Cell targetCell, double transferValue, HashMap<Cell, Double> map) {
        map.remove(currentCell);
        map.put(targetCell, transferValue);
    }

    private Cell[] checkNeighborsForCondition(int currentState, int condition, Cell[] neighbors) {
        ArrayList<Cell> cellsWithCondition = new ArrayList<>();
        for (Cell neighbor : neighbors) {
            if (currentState == FISH && neighbor.getMyIsAvailable() && neighbor.getState() == condition) {
                cellsWithCondition.add(neighbor);
            } else if (currentState == SHARK && neighbor.getMyIsAvailable() && neighbor.getNextState() == condition) {
                cellsWithCondition.add(neighbor);
            }
        }
        return cellsWithCondition.toArray(new Cell[0]);
    }

    private Cell selectRandomNeighbor(Cell[] qualifyingNeighbors) {
        int randomCellIndex = (int) (Math.random() * (qualifyingNeighbors.length));
        return qualifyingNeighbors[randomCellIndex];
    }

    private void clearCellAvailability() {
        for(int id = 0; id < getGrid().getSize(); id++){
            Cell cell = getGrid().getCell(id);
            cell.setMyIsAvailable(true);
        }
    }
}
