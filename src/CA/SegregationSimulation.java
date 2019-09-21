package CA;

import javafx.application.Application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SegregationSimulation extends Simulation {
    private final double SEGREGATION_TRESHOLD = 0.5;
<<<<<<< HEAD
    private ArrayList<Cell> availableCells;
=======
    private final int AGENT1 = 1;
    private final int AGENT2 = 2;
>>>>>>> (Untested) working version of segregation; buttons for simulation in progress

    public SegregationSimulation(Grid grid) {
        super(grid);
        availableCells = new ArrayList<Cell>();
    }

    @Override
    public void analyzeCells() {
        Random random = new Random();
        availableCells = myGrid.getEmptyCells();
        for (Cell[] cellRow : myGrid.getCells()) {
            for (Cell cell : cellRow) {
                double similarNeighbors = countSimilarNeighbors(cell, cell.getMyNeighbours());
                if (cell.getState()!= 0 && (similarNeighbors / cell.getMyNeighbours().length < SEGREGATION_TRESHOLD)) {
                    System.out.println("My threshold: " + similarNeighbors / cell.getMyNeighbours().length);
<<<<<<< HEAD
                    Cell random_cell = availableCells.get(random.nextInt(availableCells.size()));
                    random_cell.setMyIsAvailable(false);
                    random_cell.setMyNextState(cell.getState());
                    cell.setMyNextState(0);
                    availableCells.remove(random_cell);
                    availableCells.add(cell);
=======
                    Cell[] emptyCellsArray = myGrid.getEmptyCells();
                    for(int i = 0; i < emptyCellsArray.length; i++) {
                        if(emptyCellsArray[i].getMyIsAvailable()){
                            emptyCellsArray[i].setMyNextState(cell.getState());
                            emptyCellsArray[i].setMyIsAvailable(false);
                            cell.setMyNextState(0);
                            break;
                        }
                    }
>>>>>>> (Untested) working version of segregation; buttons for simulation in progress
                }
            }

        }
    }

<<<<<<< HEAD



=======
>>>>>>> (Untested) working version of segregation; buttons for simulation in progress
    private double countSimilarNeighbors(Cell cell, Cell[] neighbors){
        int similarNeighborsCount = 0;
        int state = cell.getState();
        for(Cell neighbor: neighbors){
            if(neighbor.getState() == state){
                similarNeighborsCount++;
            }
        }
        return similarNeighborsCount;
    }
}




