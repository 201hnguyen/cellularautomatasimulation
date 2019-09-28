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
        for(int id = 0; id < myGrid.getSize(); id++){
            myGrid.getCell(id).updateState();
        }
    }

    public Grid getGrid() {
        return myGrid;
    }
}