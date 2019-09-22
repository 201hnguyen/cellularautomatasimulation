package simulation;

import elements.Cell;
import elements.Grid;

import java.util.ArrayList;
import java.util.Random;

public class SegregationSimulation extends Simulation {
    public static final double SEGREGATION_TRESHOLD = 0.4; //TODO: Read from XML

    private ArrayList<Cell> myAvailableCells;

    public SegregationSimulation(Grid grid) {
        super(grid);
        myAvailableCells = new ArrayList<>();
    }

    @Override
    public void analyzeCells() {
        Random random = new Random();
        myAvailableCells = super.getGrid().getEmptyCells();
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                double similarNeighbors = countSimilarNeighbors(cell, cell.getMyNeighbors());
                if (cell.getState()!= 0 && (similarNeighbors / cell.getMyNeighbors().length < SEGREGATION_TRESHOLD)) {
                    Cell random_cell = myAvailableCells.get(random.nextInt(myAvailableCells.size()));
                    random_cell.setMyIsAvailable(false);
                    random_cell.setMyNextState(cell.getState());
                    cell.setMyNextState(0);
                    myAvailableCells.remove(random_cell);
                    myAvailableCells.add(cell);
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
