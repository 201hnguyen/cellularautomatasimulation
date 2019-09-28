package elements;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class Cell implements Comparable<Cell>{
    private int myState;
    private int myNextState;
    private ArrayList<Cell> myNeighbors;
    private int myID;
    private boolean myIsAvailable;
    private Shape myShape;

    public Cell(int state, int ID){
        myState = state;
        myNextState = state;
        myID = ID;
        myIsAvailable = true;
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

    public Cell[] getMyNeighbors(){
        return myNeighbors.toArray(new Cell[0]);
    }

    public void setMyNeighbors(ArrayList<Cell> neighbors) {
        myNeighbors = neighbors;
    }

    public boolean getMyIsAvailable() {
        return myIsAvailable;
    }

    public void setMyIsAvailable(boolean value) {
        myIsAvailable = value;
    }

    public int getMyID(){
        return myID;
    }

    public Shape getMyShape(){
        return myShape;
    }

    @Override
    public int compareTo(Cell o) {
        if(this.getMyID() == o.getMyID()) {
            return 0;
        }
        else if (this.getMyID() < o.getMyID()){
            return -1;
        }
        else{
            return 1;
        }
    }
}
