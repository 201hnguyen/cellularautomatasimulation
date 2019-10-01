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

####Assumptions or Simplifications:

####Known Bugs:

####Extra credit:


### Notes


### Impressions

