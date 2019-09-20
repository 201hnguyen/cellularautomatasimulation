package CA;

import java.util.Random;

public class SpreadingOfFireSimulation extends Simulation{
    public static int EMPTY = 0;
    public static int TREE = 1;
    public static int BURNING = 2;

    public static double BURN_PROBABILITY = 0.15;
    public static double TREE_PROBABILITY = 0.15;

    public static int emptyTurns;


    public SpreadingOfFireSimulation(Grid grid) {
        super(grid);
    }

    @Override
    public void analyzeCells(){
        for(Cell[] cellRow : myGrid.getCells()){
            for(Cell cell : cellRow){
                if(cell.getState() == EMPTY){
                    emptyTurns = 1;
                    stayEmpty(cell);
                }
                if(cell.getState() == TREE){

                }
                if(cell.getState() == BURNING){
                    cell.setMyNextState(EMPTY);
                }


            }
        }
    }

    private void willBurn(Cell curr, Cell[] neighbors){
        for(Cell neighbor : neighbors){
            if(curr.getState() == TREE && neighbor.getState() == BURNING && probability(BURN_PROBABILITY)){
                curr.setMyNextState(BURNING);
            }
        }
    }

    private void willTreeGrow(Cell curr, Cell[] neighbors){
        for(Cell neighbor : neighbors){
            if(curr.getState() == EMPTY && emptyTurns == 1){
                curr.setMyNextState(EMPTY);
                emptyTurns++;
            }
            if(curr.getState() == EMPTY && neighbor.getState() == TREE &&
                    probability(TREE_PROBABILITY) && emptyTurns == 2){
                curr.setMyNextState(TREE);
            }
        }
    }

    private void stayEmpty(Cell curr){
        emptyTurns = 1;
        if(curr.getState() == EMPTY){
            curr.setMyNextState(EMPTY);
            emptyTurns++;
        }
    }

    private boolean probability(double probEvent){
        Random random = new Random();
        int randNum = random.nextInt(100);

        return (randNum < (probEvent * 100) - 1);
    }

}
