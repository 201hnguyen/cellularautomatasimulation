package CA;

public class Cell{
    private String[] myStates;
    private int myState;
    private int myNextState;
    private Cell[] myNeighbors;
    int myXPosition;
    int myYPosition;
    private boolean myIsAvailable;

    public Cell(int state, int x, int y){
        myState = state;
        myNextState = state;
        myXPosition = x;
        myYPosition = y;
        myIsAvailable = true;
    }

    public void updateState(){
        myState = myNextState;
        myIsAvailable = true;
    }

    public void setMyNextState(int state){
        myNextState = state;
    }

    public int getState(){
        return myState;
    }

    public Cell[] getMyNeighbours(){
        return myNeighbors;
    }

    public void setMyNeighbours(Cell[] neighbors) {
        myNeighbors = neighbors;
    }

    public boolean getMyIsAvailable(){
        return myIsAvailable;
    }

    public void setMyIsAvailable(boolean value){
        myIsAvailable = value;
    }
}