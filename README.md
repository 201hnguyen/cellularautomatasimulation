simulation
====

This project implements a cellular automata simulator.

Names: Shreya Hurli, Sumer Vardhan, Ha Nguyen
sgh19, sv110, hn47

### Timeline

Start Date: 9/12/19

Finish Date: 9/24/19

Hours Spent: 40

### Primary Roles
Ha Nguyen: 

Sumer Vardhan:

Shreya Hurli: Worked on coming up with design for triangle cell neighbors, 
wrote 3 of the first simulations and did some front-end work for additional feature implementation
as well as some work on xml parsing and input files. Did some refactoring by adding comments to public methods and 
replacing hardcoded text with properties keys to make the code readable, dynamic, and flexible


### Resources Used
Course webpage: https://www2.cs.duke.edu/courses/compsci308/current/

### Running the Program

Main class: The main class is the Main class inside the game package. This is the class with the start 
method and the main method.

####Data files needed: 

GameProperties.properties -> Contains the text displayed on buttons, the text for selecting a simulation, shapes and 
colors of cells, and error-handling messages.

background.jpg -> From /Resources/images, provides the background for the splash screen
of the game, also is the background for the buttons

GameConfig.xml -> Defines what buttons exist in the CA

GameConfig.xsd -> Defines the tags used in GameConfig.xml, provides a framework for what elements can exist in
this xml file

GameOfLifeConfig.xml -> Tells the Game class that it is the simulation for Game of Life. Tells the Grid
class what color the cells are, the dimensions of the grid, how many neighbors each cell has, what the
neighbor configuration is, as well as the configuration of the initial grid. 

PercolationConfig.xml -> Same information as the GameOfLifeConfig file, except for Percolation.

PredatorPreyConfig -> Same information as the GameOfLifeConfig file, except for Predator and Prey/WaterWorld.

SegregationConfig -> Same information as the GameOfLifeConfig file, except for Segregation.

SpreadingOfFireConfig -> Same information as the GameOfLifeConfig file, except for Spreading of Fire.

####Interesting data files:

####Features implemented:

* 2D grid represents simulation, with smaller neighborhoods for edge cells
than inner cells
* Each cell has state updates based on the states of its neighbors
* Implemented simulations for Game of Life, Segregation, Predator-Prey, Fire, and
Percolation
* Initial simulation settings come from xml configuration files 
* xml files contain simulation type, configuration parameters for grid and cells, 
and the initial grid
* 2D array of cells are animated, and as simulation continues and cells change states,
they change colors 
* User can play, pause, step through, speed up, slow down, load a new simulation,
go back to the splash screen, and save the xml file of the grid of states the simulation is currently at
* Text displayed in the CA simulation is in the properties file of the project, within the src folder
* Error handling exists for if the user provides a non-xml file, if the simulation the user wants to run
is not supported by the logic of a Simulation subclass, and if the grid in the xml file the user provides is not a valid grid (if the scanner reading in 
the grid finds that the dimensions of the grid do not match the size of the initial grid provided)


####Assumptions or Simplifications:
The neighbor configuration in the xml file is the correct one for the current cell shape.

####Known Bugs:
Currently cannot change shape of cells from user interface, can only change from within the code. 
Cannot select different neighbor configs from the user interface


####Extra credit:

N/A

### Notes

Shreya: My IntelliJ interacted poorly with Git. I will be redownloading IntelliJ and potentially also redownloading JDK
to get everything together for the next project, so less time will be spent trying to get projects from Git to clone
properly, and more time will be spent actually getting to contribute to the project. 

### Impressions

Shreya: Very challenging project, required us to devise ways of interrelating classes we never thought of/have never 
really seen before. That said, I feel like I'm coming away understanding for the first time, what abstraction means, and
what interfaces are, as well as understanding data structures better (i.e. understanding the capabilities of a 2D array).

