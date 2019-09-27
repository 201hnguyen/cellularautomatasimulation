package elements;

import config.XMLSimulationParser;

import java.io.File;
import java.util.*;

public class Grid {
    private int myNumNeighbors;
    private File myConfigFile;
    private String[] myCellColors;
    private Cell[][] myCellsMatrix;
    private Map<Integer, Cell> myCellsMap;
    private int myNumRows;
    private int myNumCols;
    private Map<Integer,List<Integer>> myNeighborRules;

    private XMLSimulationParser parser;
    private Scanner scanner;

    public Grid(File file){
        myConfigFile = file;
        parser =  new XMLSimulationParser(myConfigFile);
        scanner = new Scanner(parser.getInitialGrid());
        myCellColors = parser.getColors();
        myNumRows = parser.getNumRows();
        myNumCols = parser.getNumCols();
        myNeighborRules = parser.getNeighborRules();
        myCellsMatrix = new Cell[myNumRows][myNumCols];
        myCellsMap = new HashMap<>();
        createGridOfCells();
        setCellNeighbors();
    }

    public Cell getCell(int id){
        return myCellsMap.get(id);
    }

    public List<Cell> getEmptyCells(){
        List<Cell> emptyCells = new ArrayList<>();
        for(Cell[] cellRow : myCellsMatrix){
            for (Cell cell : cellRow)
            if(cell.getState() == 0) {
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

    public File getMyConfigFile(){
        return myConfigFile;
    }

    public int getSize() {
        return myNumRows*myNumCols;
    }

    private void createGridOfCells() {
        int id = 0;
        System.out.println(parser.getInitialGrid());
        System.out.println(parser.getInitialGrid().length());
        while(scanner.hasNext()){
            for (int i = 0; i < myNumRows; i++){
                for (int j = 0; j < myNumCols; j++){
                    int state = scanner.nextInt();
                    myCellsMatrix[i][j] = new Cell(state, i, j);
                    myCellsMap.put(id, myCellsMatrix[i][j]);
                    id++;
                }
            }
        }
    }

    private void setCellNeighbors() {
        Queue<Cell> cellQueue = new LinkedList<>();
        cellQueue.add(myCellsMatrix[0][0]);

        while (cellQueue.size() != 0) {
            Cell currentCell = cellQueue.remove();
            int neighborRow;
            int neighborCol;
            for (int row : myNeighborRules.keySet()) {
                neighborRow = currentCell.getRow() + row;
                for (int col : myNeighborRules.get(row)) {
                    neighborCol = currentCell.getCol() + col;
                    if (inRange(neighborRow, neighborCol)) {
                        cellQueue.add(myCellsMatrix[neighborRow][neighborCol]);
                        currentCell.addToNeighbor(myCellsMatrix[neighborRow][neighborCol]);
                    }
                }
            }
        }
    }

    private boolean inRange(int row, int col) {
        if (row < 0 || row > myNumRows - 1 || col < 0 || col > myNumCols - 1) {
            return false;
        }
        return true;
    }

//    private void setCellNeighbors(){
//        Cell[][] cells = new Cell[myNumRows][myNumCols];
//        int id = 0;
//        for(int i = 0; i < myNumRows; i++){
//            for(int j = 0; j < myNumCols; j++){
//                cells[i][j] = getCell(id);
//                id++;
//            }
//        }
//        Cell[] neighbors = null;
//        for (int i = 0; i < myNumRows; i++){
//            for (int j = 0; j < myNumCols; j++) {
//                if(myNumNeighbors == 4){
//                    if(i == 0)
//                    {
//                        if(j == 0){
//                            neighbors = new Cell[2];
//                            neighbors[0] = cells[i+1][j];
//                            neighbors[1] = cells[i][j+1];
//                        }
//                        else if (j == myNumRows -1){
//                            neighbors = new Cell[2];
//                            neighbors[0] = cells[i+1][j];
//                            neighbors[1] = cells[i][j-1];
//                        }
//                        else{
//                            neighbors = new Cell[3];
//                            neighbors[0] = cells[i][j-1];
//                            neighbors[1] = cells[i][j+1];
//                            neighbors[2] = cells[i+1][j];
//                        }
//                    }
//                    else if (i == myNumCols - 1){
//                        if (j == 0){
//                            neighbors = new Cell[2];
//                            neighbors[0] = cells[i-1][j];
//                            neighbors[1] = cells[i][j+1];
//                        }
//                        else if (j == myNumRows - 1){
//                            neighbors = new Cell[2];
//                            neighbors[0] = cells[i-1][j];
//                            neighbors[1] = cells[i][j-1];
//                        }
//                        else{
//                            neighbors = new Cell[3];
//                            neighbors[0] = cells[i][j-1];
//                            neighbors[1] = cells[i][j+1];
//                            neighbors[2] = cells[i-1][j];
//                        }
//                    }
//                    else if (j == 0){
//                        neighbors = new Cell[3];
//                        neighbors[0] = cells[i-1][j];
//                        neighbors[1] = cells[i+1][j];
//                        neighbors[2] = cells[i][j+1];
//                    }
//                    else if (j == myNumRows - 1){
//                        neighbors = new Cell[3];
//                        neighbors[0] = cells[i-1][j];
//                        neighbors[1] = cells[i+1][j];
//                        neighbors[2] = cells[i][j-1];
//                    }
//                    else {
//                        neighbors = new Cell[4];
//                        neighbors[0] = cells[i][j + 1];
//                        neighbors[1] = cells[i][j - 1];
//                        neighbors[2] = cells[i + 1][j];
//                        neighbors[3] = cells[i - 1][j];
//                    }
//                }
//                else if(myNumNeighbors == 8){
//                    if(i == 0)
//                    {
//                        if(j == 0){
//                            neighbors = new Cell[3];
//                            neighbors[0] = cells[i+1][j];
//                            neighbors[1] = cells[i][j+1];
//                            neighbors[2] = cells[i+1][j+1];
//                        }
//                        else if (j == myNumRows -1){
//                            neighbors = new Cell[3];
//                            neighbors[0] = cells[i+1][j];
//                            neighbors[1] = cells[i][j-1];
//                            neighbors[2] = cells[i+1][j-1];
//                        }
//                        else{
//                            neighbors = new Cell[5];
//                            neighbors[0] = cells[i][j-1];
//                            neighbors[1] = cells[i][j+1];
//                            neighbors[2] = cells[i+1][j];
//                            neighbors[3] = cells[i+1][j+1];
//                            neighbors[4] = cells[i+1][j-1];
//                        }
//                    }
//                    else if (i == myNumCols - 1){
//                        if (j == 0){
//                            neighbors = new Cell[3];
//                            neighbors[0] = cells[i-1][j];
//                            neighbors[1] = cells[i][j+1];
//                            neighbors[2] = cells[i-1][j+1];
//                        }
//                        else if (j == myNumRows - 1){
//                            neighbors = new Cell[3];
//                            neighbors[0] = cells[i-1][j];
//                            neighbors[1] = cells[i][j-1];
//                            neighbors[2] = cells[i-1][j-1];
//                        }
//                        else{
//                            neighbors = new Cell[5];
//                            neighbors[0] = cells[i][j-1];
//                            neighbors[1] = cells[i][j+1];
//                            neighbors[2] = cells[i-1][j];
//                            neighbors[3] = cells[i-1][j-1];
//                            neighbors[4] = cells[i-1][j+1];
//                        }
//                    }
//                    else if (j == 0){
//                        neighbors = new Cell[5];
//                        neighbors[0] = cells[i-1][j];
//                        neighbors[1] = cells[i+1][j];
//                        neighbors[2] = cells[i][j+1];
//                        neighbors[3] = cells[i-1][j+1];
//                        neighbors[4] = cells[i+1][j+1];
//                    }
//                    else if (j == myNumRows - 1){
//                        neighbors = new Cell[5];
//                        neighbors[0] = cells[i-1][j];
//                        neighbors[1] = cells[i+1][j];
//                        neighbors[2] = cells[i][j-1];
//                        neighbors[3] = cells[i-1][j-1];
//                        neighbors[4] = cells[i+1][j-1];
//                    }
//                    else {
//                        neighbors = new Cell[8];
//                        neighbors[0] = cells[i][j + 1];
//                        neighbors[1] = cells[i][j - 1];
//                        neighbors[2] = cells[i + 1][j];
//                        neighbors[3] = cells[i - 1][j];
//                        neighbors[4] = cells[i + 1][j + 1];
//                        neighbors[5] = cells[i + 1][j - 1];
//                        neighbors[6] = cells[i - 1][j - 1];
//                        neighbors[7] = cells[i - 1][j + 1];
//                    }
//                }
//                cells[i][j].setneighbors);
//            }
//        }
//    }
}

