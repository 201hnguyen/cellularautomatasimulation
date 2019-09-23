package elements;

import config.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid{
    private Cell[][] cells;
    private int myNumRows;
    private int myNumCols;
    private int myNumNeighbors;
    private File myConfigFile;
    private XMLParser myXMLParser;
    private Scanner mySc;
    private String[] myCellColors;

    public Grid(File file){
        myConfigFile = file;
        myXMLParser =  new XMLParser("simulationType", myConfigFile);
        mySc = new Scanner(myXMLParser.getInitialGrid());
        myNumRows = myXMLParser.getNumRows();
        myNumCols = myXMLParser.getNumCols();
        myCellColors = myXMLParser.getCellColors();
        cells = new Cell[myNumRows][myNumCols];
    }

    public Cell[][] configureCells(){
        createGridOfCells();
        setCellNeighbors();
        return cells;
    }

    public Cell[][] getCells(){
        return cells;
    }

    public ArrayList<Cell> getEmptyCells(){
        ArrayList<Cell> emptyCells = new ArrayList<>();
        int i = 0;
        for(Cell[] cellrow: cells){
            for(Cell cell : cellrow){
                if(cell.getState() == 0)
                {
                    emptyCells.add(cell);
                }
            }
        }
        return emptyCells;
    }

    public int getNumRows(){
        return myNumRows;
    }

    public int getNumCols(){
        return myNumCols;
    }

    public String[] getCellColors(){
        return myCellColors;
    }

    private void createGridOfCells() {
        myNumNeighbors = myXMLParser.getNumNeighbors();
        while(mySc.hasNext()){
            for (int i = 0; i < myNumRows; i++){
                for (int j = 0; j < myNumCols; j++){
                    int state = mySc.nextInt();
                    cells[i][j] = new Cell(state, i, j);
                }
            }
        }
    }

    //This method assumes that all simulations will either consider 4 neighbors (NSWE) or 8 (NSWE + the 4 directions in between)
    private void setCellNeighbors(){
        Cell[] neighbors = null;
        for (int i = 0; i < myNumRows; i++){
            for (int j = 0; j < myNumCols; j++) {
                if(myNumNeighbors == 4){
                    if(i == 0)
                    {
                        if(j == 0){
                            neighbors = new Cell[2];
                            neighbors[0] = cells[i+1][j];
                            neighbors[1] = cells[i][j+1];
                        }
                        else if (j == myNumRows -1){
                            neighbors = new Cell[2];
                            neighbors[0] = cells[i+1][j];
                            neighbors[1] = cells[i][j-1];
                        }
                        else{
                            neighbors = new Cell[3];
                            neighbors[0] = cells[i][j-1];
                            neighbors[1] = cells[i][j+1];
                            neighbors[2] = cells[i+1][j];
                        }
                    }
                    else if (i == myNumCols - 1){
                        if (j == 0){
                            neighbors = new Cell[2];
                            neighbors[0] = cells[i-1][j];
                            neighbors[1] = cells[i][j+1];
                        }
                        else if (j == myNumRows - 1){
                            neighbors = new Cell[2];
                            neighbors[0] = cells[i-1][j];
                            neighbors[1] = cells[i][j-1];
                        }
                        else{
                            neighbors = new Cell[3];
                            neighbors[0] = cells[i][j-1];
                            neighbors[1] = cells[i][j+1];
                            neighbors[2] = cells[i-1][j];
                        }
                    }
                    else if (j == 0){
                        neighbors = new Cell[3];
                        neighbors[0] = cells[i-1][j];
                        neighbors[1] = cells[i+1][j];
                        neighbors[2] = cells[i][j+1];
                    }
                    else if (j == myNumRows - 1){
                        neighbors = new Cell[3];
                        neighbors[0] = cells[i-1][j];
                        neighbors[1] = cells[i+1][j];
                        neighbors[2] = cells[i][j-1];
                    }
                    else {
                        neighbors = new Cell[4];
                        neighbors[0] = cells[i][j + 1];
                        neighbors[1] = cells[i][j - 1];
                        neighbors[2] = cells[i + 1][j];
                        neighbors[3] = cells[i - 1][j];
                    }
                }
                else if(myNumNeighbors == 8){
                    if(i == 0)
                    {
                        if(j == 0){
                            neighbors = new Cell[3];
                            neighbors[0] = cells[i+1][j];
                            neighbors[1] = cells[i][j+1];
                            neighbors[2] = cells[i+1][j+1];
                        }
                        else if (j == myNumRows -1){
                            neighbors = new Cell[3];
                            neighbors[0] = cells[i+1][j];
                            neighbors[1] = cells[i][j-1];
                            neighbors[2] = cells[i+1][j-1];
                        }
                        else{
                            neighbors = new Cell[5];
                            neighbors[0] = cells[i][j-1];
                            neighbors[1] = cells[i][j+1];
                            neighbors[2] = cells[i+1][j];
                            neighbors[3] = cells[i+1][j+1];
                            neighbors[4] = cells[i+1][j-1];
                        }
                    }
                    else if (i == myNumCols - 1){
                        if (j == 0){
                            neighbors = new Cell[3];
                            neighbors[0] = cells[i-1][j];
                            neighbors[1] = cells[i][j+1];
                            neighbors[2] = cells[i-1][j+1];
                        }
                        else if (j == myNumRows - 1){
                            neighbors = new Cell[3];
                            neighbors[0] = cells[i-1][j];
                            neighbors[1] = cells[i][j-1];
                            neighbors[2] = cells[i-1][j-1];
                        }
                        else{
                            neighbors = new Cell[5];
                            neighbors[0] = cells[i][j-1];
                            neighbors[1] = cells[i][j+1];
                            neighbors[2] = cells[i-1][j];
                            neighbors[3] = cells[i-1][j-1];
                            neighbors[4] = cells[i-1][j+1];
                        }
                    }
                    else if (j == 0){
                        neighbors = new Cell[5];
                        neighbors[0] = cells[i-1][j];
                        neighbors[1] = cells[i+1][j];
                        neighbors[2] = cells[i][j+1];
                        neighbors[3] = cells[i-1][j+1];
                        neighbors[4] = cells[i+1][j+1];
                    }
                    else if (j == myNumRows - 1){
                        neighbors = new Cell[5];
                        neighbors[0] = cells[i-1][j];
                        neighbors[1] = cells[i+1][j];
                        neighbors[2] = cells[i][j-1];
                        neighbors[3] = cells[i-1][j-1];
                        neighbors[4] = cells[i+1][j-1];
                    }
                    else {
                        neighbors = new Cell[8];
                        neighbors[0] = cells[i][j + 1];
                        neighbors[1] = cells[i][j - 1];
                        neighbors[2] = cells[i + 1][j];
                        neighbors[3] = cells[i - 1][j];
                        neighbors[4] = cells[i + 1][j + 1];
                        neighbors[5] = cells[i + 1][j - 1];
                        neighbors[6] = cells[i - 1][j - 1];
                        neighbors[7] = cells[i - 1][j + 1];
                    }
                }
                cells[i][j].setMyNeighbors(neighbors);
            }
        }
    }
    public File getMyConfigFile(){
        return myConfigFile;
    }
}
