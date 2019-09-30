package elements;

import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * This class represents the cells in each of the simulation. The class is used by Grid to initialize the grid object itself,
 * used by Simulations to change and update the state of each cell according to its neighbors, and used by Visualization
 * to print out the colors that represent the state of the cells.
 * @author Sumer Vardhan
 */
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

    /**
     * Gets the integer that represents the state of the cell, used by classes such as Grid, Simulation, and Visualization
     * to configure the grid object to display to the user and update this grid.
     * @return the integer that represents the state of each cell.
     */
    public int getState(){
        return myState;
    }

    /**
     * Updates the cell's state to the next state set by the Simulation class. This is done so that all cells update
     * their states at once.
     */
    public void updateState(){
        myState = myNextState;
        myIsAvailable = true;
    }

    /**
     * Gets the next state of the cell; this is used primarily by Predator and Prey because all fish cells move first
     * and update their next state before shark cells move accordingly
     * @return the next5 state of the cell
     */
    public int getNextState() {
        return myNextState;
    }

    /**
     * Sets the next state of the cell for it to update to this state once all cells have checked their neighbors.
     * @param state the integer that represents the next state that the cell should be.
     */
    public void setMyNextState(int state){
        myNextState = state;
    }

    /**
     *
     * @return
     */
    public Cell[] getMyNeighbors(){
        return myNeighbors.toArray(new Cell[0]);
    }

    /**
     *
     * @param neighbors
     */
    public void setMyNeighbors(ArrayList<Cell> neighbors) {
        myNeighbors = neighbors;
    }

    /**
     *
     * @return
     */
    public boolean getMyIsAvailable() {
        return myIsAvailable;
    }

    public void setMyIsAvailable(boolean value) {
        myIsAvailable = value;
    }

    /**
     *
     * @return
     */
    public int getMyID(){
        return myID;
    }

    /**
     *
     * @return
     */
    public Shape getMyShape(){
        return myShape;
    }

    /**
     *
     * @param o
     * @return
     */
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
