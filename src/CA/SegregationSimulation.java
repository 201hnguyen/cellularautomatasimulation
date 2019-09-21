package CA;

import javafx.application.Application;

import java.util.HashSet;
import java.util.Random;

public class SegregationSimulation extends Simulation {
    private final double SEGREGATION_TRESHOLD = 0.5;
    private final int AGENT1 = 1;
    private final int AGENT2 = 2;

    public SegregationSimulation(Grid grid) {
        super(grid);
    }

    @Override
    public void analyzeCells() {
        for (Cell[] cellRow : myGrid.getCells()) {
            for (Cell cell : cellRow) {
                double similarNeighbors = countSimilarNeighbors(cell, cell.getMyNeighbours());
                if (cell.getState()!= 0 && (similarNeighbors / cell.getMyNeighbours().length < SEGREGATION_TRESHOLD)) {
                    System.out.println("My threshold: " + similarNeighbors / cell.getMyNeighbours().length);
                    Cell[] emptyCellsArray = myGrid.getEmptyCells();
                    for(int i = 0; i < emptyCellsArray.length; i++) {
                        if(emptyCellsArray[i].getMyIsAvailable()){
                            emptyCellsArray[i].setMyNextState(cell.getState());
                            emptyCellsArray[i].setMyIsAvailable(false);
                            cell.setMyNextState(0);
                            break;
                        }
                    }
                }
            }
        }
    }

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




