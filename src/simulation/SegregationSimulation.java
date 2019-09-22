package simulation;

import config.XMLParser;
import elements.Cell;
import elements.Grid;

import java.util.ArrayList;
import java.util.Random;

public class SegregationSimulation extends Simulation {
    private XMLParser xmlParser;
    private double segregationTreshold;

    private ArrayList<Cell> myAvailableCells;

    public SegregationSimulation(Grid grid) {
        super(grid);
        xmlParser = new XMLParser("Segregation", grid.getMyConfigFile());
        myAvailableCells = new ArrayList<>();
        segregationTreshold = xmlParser.getSimulationParameter1();
    }

    @Override
    public void analyzeCells() {
        Random random = new Random();
        myAvailableCells = super.getGrid().getEmptyCells();
        for (Cell[] cellRow : super.getGrid().getCells()) {
            for (Cell cell : cellRow) {
                double similarNeighbors = countSimilarNeighbors(cell, cell.getMyNeighbors());
                if (cell.getState()!= 0 && (similarNeighbors / cell.getMyNeighbors().length < segregationTreshold)) {
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
