package simulation;

import config.XMLSimulationParser;
import elements.Cell;
import elements.Grid;

import java.util.Random;
import java.util.Set;

public class SpreadingOfFireSimulation extends Simulation {
    private final static int EMPTY = 0;
    private final static int TREE = 1;
    private final static int BURNING = 2;

    private XMLSimulationParser myXMLParser;
    private double myBurnProbability;
    private double myTreeProbability;

    private int myEmptyTurns;

    public SpreadingOfFireSimulation(Grid grid) {
        super(grid);
        myXMLParser = new XMLSimulationParser(grid.getMyConfigFile());
        myBurnProbability = myXMLParser.getParameters().get("burn_probability");
        myTreeProbability = myXMLParser.getParameters().get("tree_probability");
    }

    @Override
    public void analyzeCells(){
        for(int id = 0; id < getGrid().getSize(); id++){
            Cell cell = getGrid().getCell(id);
                if(cell.getState() == EMPTY){
                    myEmptyTurns = 1;
                    willTreeGrow(cell, cell.getMyNeighbors());
                }
                if(cell.getState() == TREE){
                    willBurn(cell, cell.getMyNeighbors());
                }
                if(cell.getState() == BURNING){
                    cell.setMyNextState(EMPTY);
                }
            }
        }

    private void willBurn(Cell curr, Set<Cell> neighbors){
        for(Cell neighbor : neighbors){
            if(curr.getState() == TREE && neighbor.getState() == BURNING){
//                if(curr.getState() == TREE && neighbor.getState() == BURNING && probability(myBurnProbability)){
                curr.setMyNextState(BURNING);
            }
        }
    }

    private void willTreeGrow(Cell curr, Set<Cell> neighbors){
        if(curr.getState() == EMPTY && myEmptyTurns == 1){
            curr.setMyNextState(EMPTY);
            myEmptyTurns++;
        }
        for(Cell neighbor : neighbors){
            if(curr.getState() == EMPTY && neighbor.getState() == TREE &&
                    probability(myTreeProbability) && myEmptyTurns == 2){
                curr.setMyNextState(TREE);
            }
        }
    }

    private boolean probability(double probEvent){
        Random random = new Random();
        int randNum = random.nextInt(100);

        return (randNum < (probEvent * 100) - 1);
    }
}

