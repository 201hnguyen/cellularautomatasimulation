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

    public void printCells() {
        for (Cell[] cellRow : myCells) {
            for (Cell cell : cellRow) {
                System.out.print(cell.getState());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

}