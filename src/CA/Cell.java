package CA;

public class Cell{
    private String[] myStates;
    int myState;
    int myNextState;
    Cell[] myNeighbours;
    int myXPosition;
    int myYPosition;

    public Cell(int state, int x, int y){
        myState = state;
        myXPosition = x;
        myYPosition = y;
    }

    public void updateCell(){
        myState = myNextState;
    }

    public void setMyNextState(int state){
        myNextState = state;
    }
}