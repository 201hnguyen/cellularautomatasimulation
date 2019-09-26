package elements;

public class Cell{
    private int myState;
    private int myNextState;
    private Cell[] myNeighbors;
    private int myID;
    private boolean myIsAvailable;

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
        return myNeighbors;
    }

    public void setMyNeighbors(Cell[] neighbors) {
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

}