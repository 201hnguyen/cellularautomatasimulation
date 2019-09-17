package CA;

public abstract class Simulation {
    protected Grid myGrid;

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


}