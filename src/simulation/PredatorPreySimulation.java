package simulation;

import config.XMLParser;
import elements.Cell;
import elements.Grid;

import java.util.ArrayList;
import java.util.HashMap;

public class PredatorPreySimulation extends Simulation {
    public static final int EMPTY = 0;
    public static final int FISH = 1;
    public static final int SHARK = 2;

    private XMLParser myXMLParser;
    private int myStartingSharkEnergy;
    private int mySharkEnergyGain;
    private int mySharkEnergyLoss;
    private int mySharkReproducibilityThreshold;
    private int myFishReproducibilityThreshold;

    private HashMap<Cell, Integer> mySharkEnergyMap = new HashMap<>();
    private HashMap<Cell, Integer> mySharkMovesMap = new HashMap<>();
    private HashMap<Cell, Integer> myFishMovesMap = new HashMap<>();

    public PredatorPreySimulation(Grid grid) {
        super(grid);
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                if (cell.getState() == FISH) {
                    myFishMovesMap.put(cell, 0);
                } else if (cell.getState() == SHARK) {
                    mySharkMovesMap.put(cell, 0);
                    mySharkEnergyMap.put(cell, myStartingSharkEnergy);
                }
            }
            myXMLParser = new XMLParser("Predator and Prey", grid.getMyConfigFile());
            myStartingSharkEnergy = (int) myXMLParser.getSimulationParameter1();
            mySharkEnergyGain = (int) myXMLParser.getSimulationParameter2();
            mySharkEnergyLoss = (int) myXMLParser.getSimulationParameter3();
            mySharkReproducibilityThreshold = (int) myXMLParser.getSimulationParameter4();
            myFishReproducibilityThreshold = (int) myXMLParser.getSimulationParameter5();
        }
    }

    @Override
    public void analyzeCells() {
        clearCellAvailability();
        analyzeFishCells();

        clearCellAvailability();
        analyzeSharkCells();
    }

    private void analyzeFishCells() {
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                if (cell.getState() == FISH) {
                    Cell[] emptyNeighbors = checkNeighborsForCondition(FISH, EMPTY, cell.getMyNeighbors());
                    if (emptyNeighbors.length != 0) {
                        moveFishToNeighbor(cell, emptyNeighbors);
                    }
                }
            }
        }
    }

    private void analyzeSharkCells() {
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                if (cell.getState() == SHARK) {
                    Cell[] fishNeighbors = checkNeighborsForCondition(SHARK, FISH, cell.getMyNeighbors());
                    Cell[] emptyNeighbors = checkNeighborsForCondition(SHARK, EMPTY, cell.getMyNeighbors());

                    if (fishNeighbors.length != 0) {
                        moveSharkToNeighbor(cell, fishNeighbors);
                    } else if (emptyNeighbors.length != 0) {
                        moveSharkToNeighbor(cell, emptyNeighbors);
                    } else {
                        sharkStay(cell);
                    }
                }
            }
        }
    }

    private void sharkStay(Cell cell) {
        mySharkEnergyMap.put(cell, mySharkEnergyMap.get(cell) - 1);
        if (! (mySharkEnergyMap.get(cell) > 0)) {
            cell.setMyNextState(EMPTY);
        }
    }

    private void moveFishToNeighbor (Cell currentCell, Cell[] qualifyingNeighbors) {
        Cell targetCell = selectRandomNeighbor(qualifyingNeighbors);
        targetCell.setMyNextState(FISH);
        int movesToTransfer = myFishMovesMap.get(currentCell) + 1;
        if (movesToTransfer < myFishReproducibilityThreshold) {
            moveFishNoReproduce(currentCell, targetCell, movesToTransfer);
        } else {
            moveFishAndReproduce(currentCell, targetCell);
        }
        targetCell.setMyIsAvailable(false);
    }

    private void moveFishNoReproduce(Cell currentCell, Cell targetCell, int movesToTransfer) {
        transferMapValues(currentCell, targetCell,movesToTransfer, myFishMovesMap);
        currentCell.setMyNextState(EMPTY);
        targetCell.setMyNextState(FISH);
    }

    private void moveFishAndReproduce(Cell currentCell, Cell targetCell) {
        myFishMovesMap.put(currentCell, 0);
        myFishMovesMap.put(targetCell, 0);
        targetCell.setMyNextState(FISH);
    }

    private void moveSharkToNeighbor (Cell currentCell, Cell[] qualifyingNeighbors) {
        if (! canMove(currentCell)) {
            mySharkMovesMap.remove(currentCell);
            mySharkEnergyMap.remove(currentCell);
            currentCell.setMyNextState(EMPTY);
        } else {
            Cell targetCell = selectRandomNeighbor(qualifyingNeighbors);
            int movesToTransfer = mySharkMovesMap.get(currentCell) + 1;
            int energyToTransfer = mySharkEnergyMap.get(currentCell) - mySharkEnergyLoss;

            if (movesToTransfer < mySharkReproducibilityThreshold) {
                moveSharkNoReproduce(currentCell, targetCell, energyToTransfer, movesToTransfer);
            } else {
                moveSharkAndReproduce(currentCell, targetCell, energyToTransfer);
            }
            targetCell.setMyIsAvailable(false);
        }
    }

    private void moveSharkNoReproduce(Cell currentCell, Cell targetCell, int energyToTransfer, int movesToTransfer) {
        transferMapValues(currentCell, targetCell, energyToTransfer, mySharkEnergyMap);
        transferMapValues(currentCell, targetCell, movesToTransfer, mySharkMovesMap);

        if (targetCell.getNextState() == FISH) {
            mySharkEnergyMap.put(targetCell, mySharkEnergyMap.get(targetCell) + mySharkEnergyGain);
        }
        targetCell.setMyNextState(SHARK);
        currentCell.setMyNextState(EMPTY);
    }

    private void moveSharkAndReproduce(Cell currentCell, Cell targetCell, int energyToTransfer) {
        mySharkMovesMap.put(currentCell, 0);
        mySharkMovesMap.put(targetCell, 0);
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

    private void transferMapValues(Cell currentCell, Cell targetCell, int transferValue, HashMap<Cell, Integer> map) {
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
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                cell.setMyIsAvailable(true);
            }
        }
    }
}
