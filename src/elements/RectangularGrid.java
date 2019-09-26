package elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RectangularGrid extends SimulationGrid {
    private HashSet<Cell> myCells;
    private int myNumRows;
    private int myNumCols;

    public RectangularGrid(File file){
        super(file);
        myNumRows = myXMLParser.getNumRows();
        myNumCols = myXMLParser.getNumCols();
        myCells = new HashSet<>();
    }

    public HashSet<Cell> configureCells(){
        createGridOfCells();
        setCellNeighbors();
        return myCells;
    }

    public Cell getCell(int i){
        for(Cell cell: myCells){
            if(cell.getMyID() == i){
                return cell;
            }
        }
        return null;
    }

    public List<Cell> getEmptyCells(){
        List<Cell> emptyCells = new ArrayList<>();
        int i = 0;
        for(Cell cell : myCells){
            if(cell.getState() == 0)
            {
                emptyCells.add(cell);
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
        int id = 0;
        myNumNeighbors = myXMLParser.getNumNeighbors();
        while(mySc.hasNext()){
            for (int i = 0; i < myNumRows; i++){
                for (int j = 0; j < myNumCols; j++){
                    int state = mySc.nextInt();
                    myCells.add(new Cell(state, id));
                    id++;
                }
            }
        }
    }

    //This method assumes that all simulations will either consider 4 neighbors (NSWE) or 8 (NSWE + the 4 directions in between)
    private void setCellNeighbors(){
        Cell[][] cells = new Cell[myNumRows][myNumCols];
        int id = 0;
        for(int i = 0; i < myNumRows; i++){
            for(int j = 0; j < myNumCols; j++){
                cells[i][j] = getCell(id);
                id++;
            }
        }
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

    public int getSize() {
        return myNumRows*myNumCols;
    }
}
