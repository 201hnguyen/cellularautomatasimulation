package elements;

import java.util.ArrayList;
import java.util.List;

public class Cell{
    private int myState;
    private int myNextState;
    private List<Cell> myNeighbors;
    private int myRow;
    private int myCol;
    private boolean myIsAvailable;

    public Cell(int state, int x, int y){
        myState = state;
        myNextState = state;
        myRow = x;
        myCol = y;
        myIsAvailable = true;
        myNeighbors = new ArrayList<>();
    }

    public int getState(){
        return myState;
    }

    public void updateState(){
        myState = myNextState;
        myIsAvailable = true;
    }

    public int getNextState() {
        return myNextState;
    }

    public void setMyNextState(int state){
        myNextState = state;
    }

    public List<Cell> getMyNeighbors(){
        return myNeighbors;
    }

    public void addToNeighbor(Cell cell) {
        myNeighbors.add(cell);
    }

    public boolean getMyIsAvailable() {
        return myIsAvailable;
    }

    public void setMyIsAvailable(boolean value) {
        myIsAvailable = value;
    }

    public int getRow() {
        return myRow;
    }
     public int getCol() {
        return myCol;
     }

}