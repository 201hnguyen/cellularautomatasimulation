/*

package CA;

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
                    if (cell.getState() == AGENT1) {
                        cell.setMyNextState(AGENT1);
                    } else if (cell.getState() == AGENT2) {
                        cell.setMyNextState(AGENT2);
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
    }
}

*/


