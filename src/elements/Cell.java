package elements;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Cell{
    private int myState;
    private int myNextState;
    private Set<Cell> myNeighbors;
    private int myRow;
    private int myCol;
    private boolean myIsAvailable;
    private boolean bfs_checked;

    public Cell(int state, int x, int y){
        myState = state;
        myNextState = state;
        myRow = x;
        myCol = y;
        myIsAvailable = true;
        bfs_checked = false;
        myNeighbors = new HashSet<>();
    }

    public boolean bfsChecked() {
        return bfs_checked;
    }

    public void setBfsChecked(boolean value) {
        bfs_checked = value;
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

    public Set<Cell> getMyNeighbors(){
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