package CA;

public abstract class Simulation {
    protected Cell[][] myCells;

    public Simulation(Cell[][] cells) {
        myCells = cells;
    }

    public abstract void analyzeCells();

    public void updateCells() {
        for (Cell[] cellRow : myCells) {
            for (Cell cell : cellRow) {
                cell.updateState();
            }
        }
    }

}