package simulation;

import elements.Cell;
import elements.Grid;

public abstract class Simulation {
    private Grid myGrid;

    public Simulation(Grid grid) {
        myGrid = grid;
    }

    public abstract void analyzeCells();

    public void updateCells() {
        for (Cell[] cellRow : myGrid.getCells()) {
            for (Cell cell : cellRow) {
                cell.updateState();
            }
        }
    }

    public Grid getGrid() {
        return myGrid;
    }
}