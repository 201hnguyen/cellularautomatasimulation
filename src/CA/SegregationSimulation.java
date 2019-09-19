package CA;

import javafx.application.Application;

import java.util.HashSet;
import java.util.Random;

public class SegregationSimulation extends Simulation {
    private final double SEGREGATION_TRESHOLD = 0.2;
    private final int AGENT1 = 1;
    private final int AGENT2 = 2;

    public SegregationSimulation(Grid grid) {
        super(grid);
    }

    @Override
    public void analyzeCells() {
        for (Cell[] cellRow : myGrid.getCells()) {
            for (Cell cell : cellRow) {
                int similarNeighbors = countSimilarNeighbors(cell, cell.getMyNeighbours());
                if (similarNeighbors / cell.getMyNeighbours().length < SEGREGATION_TRESHOLD) {
                    Random random = new Random();
                    Cell[] emptyCellsArray = myGrid.getEmptyCells();
                    int random_index = 0;
                    for(int i = 0; i < emptyCellsArray.length; i++) {
                        random_index = random.nextInt(emptyCellsArray.length);
                        if(emptyCellsArray[random_index].getMyIsAvailable()){
                            emptyCellsArray[random_index].setMyNextState(cell.getState());
                            emptyCellsArray[random_index].setMyIsAvailable(false);
                            cell.setMyNextState(0);
                            break;
                        }
                    }
                }

            }
        }
    }

    private int countSimilarNeighbors(Cell cell, Cell[] neighbors){
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




