package simulation;

import elements.RectangularGrid;

public abstract class Simulation {
    private RectangularGrid myRectangularGrid;

    public Simulation(RectangularGrid rectangularGrid) {
        myRectangularGrid = rectangularGrid;
    }

    public abstract void analyzeCells();

    public void updateCells() {
        for(int id = 0; id < myRectangularGrid.getSize(); id++){
            myRectangularGrid.getCell(id).updateState();
        }
    }

    public RectangularGrid getGrid() {
        return myRectangularGrid;
    }
}