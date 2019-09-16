package CA;

public class Cell{
    private String[] myStates;
    private int myState;
    private int myNextState;
    private Cell[] myNeighbors;
    int myXPosition;
    int myYPosition;

    public Cell(int state, int x, int y){
        myState = state;
        myXPosition = x;
        myYPosition = y;
    }

    public void updateState(){
        myState = myNextState;
    }

    public void setMyNextState(int state){
        myNextState = state;
    }

    public int getMyState(){
        return myState;
    }

    public Cell[] getMyNeighbours(){
        return myNeighbors;
    }

    public void setMyNeighbours(Cell[] neighbors) {
        myNeighbors = neighbors;
    }
}