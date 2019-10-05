/**
 * This class serves as a superclass for all simulations; it holds the grid object itself and provides
 * two methods: analyzeCells and and updateCells. analyzeCells is abstract, allowing each Simulation to
 * implement their own rules in analyzing cells. I believe this code is well designed because everything
 * in it makes good use of the inheritance hierarchy while at the same time following the principles of
 * encapsulation, and demonstrates good use of an abstract class. Leveraging the power of inheritance and
 * knowing that each simulation needs a Grid object, the Grid and its initialization is kept in the superclass,
 * and a getter method is provided for subclasses and the game class to access the grid object. The Grid
 * is a key element of the Simulation class, and thus, one might question the choice to make getGrid public
 * (it is currently used by the subclasses and the Game class). There was a lot of thought put into the public
 * getter -- mainly that, though we allow public access to the Grid, we made a grid that, in itself, is very
 * private; classes can know of the object, they can change the states of individual cells, but they cannot
 * alter the actual state of the Grid at a large scale (e.g., changing the numbers of rows and columns,
 * deleting a cell, etc.); thus, it is okay for the public getter method here to return the entire Grid itself,
 * rather than a copy of the Grid. The analyzeCells and updateCells method breaks up the actual process of
 * changing the state of the cells. We realize that the analysis of neighbors is the only thing that differs
 * from Simulation Simulation. Once a cell is told what its next state should be, the updating process should
 * remain the same. Thus, the updateCells method is factored out of the subclasses and into the superclass
 * to avoid redundancy by having each superclass implement the same method. Then, the analyzeCells method
 * is left as abstract because it is the only thing that varies from Simulation to Simulation; thus, the
 * subclasses have the power to implement them. This class, therefore, leverages principles of the commonality
 * analysis in a way that keeps Simulations as broad ad possible because it recognizes recurring implementations
 * and reuse them; this broadness allows the design to hold up against a variety of different simulations/situations.
 */

package simulation;

import elements.Grid;

/**
 * This abstract class represents a Simulation. It is a superclass that is subclassed by each of the Simulation in order
 * to implement their own rules on the Simulation.
 * @author Ha Nguyen
 * @author Sumer Vardhan
 * @author Shreya Hurli
 */
public abstract class Simulation {
    private Grid myGrid;

    /**
     * Initializes the simulation with the Grid object given by game.
     * @param grid the grid object to initialize the simulation with.
     */
    public Simulation(Grid grid) {
        myGrid = grid;
    }

    /**
     * Each simulation class implements their own version of this method to analyze cells and determine the next state
     * of each cell depending on its neighbors.
     */
    public abstract void analyzeCells();

    /**
     * This method in Game is called to update the state of each cell once all the neighbors analysis has finished.
     */
    public void updateCells() {
        for(int id = 0; id < myGrid.getSize(); id++){
            myGrid.getCell(id).updateState();
        }
    }

    /**
     * This method is used to get the Grid of the Simulation; this is useful for the Simulation class, which must display
     * this grid to the GUI
     * @return the current grid of the Simulation.
     */
    public Grid getGrid() {
        return myGrid;
    }
}