package simulation;

import elements.Cell;
import elements.Grid;

import java.util.ArrayList;

public class PercolationSimulation extends Simulation {
    public static final int OPEN = 0;
    public static final int FULL = 1;
    public static final int BLOCKED = 2;

    public PercolationSimulation(Grid grid) { super(grid); }

    @Override
    public void analyzeCells(){
        for(Cell[] cellRow : super.getGrid().getCells()){
            for(Cell cell : cellRow){
                Cell[] neighborsToFill = openNeighbors(cell.getMyNeighbors());
                if(cell.getState() == FULL && neighborsToFill.length != 0){
                    for(Cell openNeighbor : neighborsToFill){
                        openNeighbor.setMyNextState(FULL);
                    }
                }
            }
        }
    }

    private Cell[] openNeighbors(Cell[] neighbors){
        ArrayList<Cell> openCells = new ArrayList<>();
        for(Cell neighbor : neighbors){
            if(neighbor.getState() == OPEN){
                openCells.add(neighbor);
            }
        }
        return openCells.toArray(new Cell[0]);
    }
}
