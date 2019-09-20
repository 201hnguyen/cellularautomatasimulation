package CA;

import java.util.ArrayList;

public class PercolationSimulation extends Simulation{
    public static int OPEN = 0;
    public static int FULL = 1;
    public static int BLOCKED = 2;

    public PercolationSimulation(Grid grid) { super(grid); }

    @Override
    public void analyzeCells(){
        for(Cell[] cellRow : myGrid.getCells()){
            for(Cell cell : cellRow){
                Cell[] neighborsToFill = openNeighbors(cell.getMyNeighbours());
                if(cell.getState() == FULL && neighborsToFill.length != 0){
                    for(Cell openNeighbor : neighborsToFill){
                        openNeighbor.setMyNextState(FULL);
                    }
                }

            }
        }
    }

    public Cell[] openNeighbors(Cell[] neighbors){
        ArrayList<Cell> openCells = new ArrayList<>();
        for(Cell neighbor : neighbors){
            if(neighbor.getState() == OPEN){
                openCells.add(neighbor);
            }
        }
        return openCells.toArray(new Cell[0]);
    }



}
