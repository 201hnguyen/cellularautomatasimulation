package simulation;

import elements.Cell;
import elements.Grid;

import java.util.HashMap;
import java.util.Map;

public abstract class Simulation {
    private Grid myGrid;
    public Map<Integer, Integer> stateRecorder = new HashMap<>();
    int count = 0;

    public Simulation(Grid grid) {
        myGrid = grid;
    }

    public abstract void analyzeCells();

    public void updateCells() {
        for(int id = 0; id < myGrid.getSize(); id++){
            myGrid.getCell(id).updateState();
            countStates(myGrid.getCell(id));
        }
    }

    public void countStates(Cell cell){
       if(!stateRecorder.containsKey(cell.getState())){
           count = 1;
           stateRecorder.put(cell.getState(),count);
       } else {
           stateRecorder.put(cell.getState(), count++);
       }
    }

    public Grid getGrid() {
        return myGrid;
    }
}