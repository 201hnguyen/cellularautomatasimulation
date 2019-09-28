package elements;

import config.XMLSimulationParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Grid {
    private File myConfigFile;
    private String[] myCellColors;
    private List<List<Cell>> myCellsMatrix;
    private Map<Integer, Cell> myCellsMap;
    private Set<Cell> myEdgeCells;
    private int myNumRows;
    private int myNumCols;
    private Map<Integer, List<Integer>> myNeighborRules;
    private Map<Integer, List<Integer>> myEdgeNeighborRules;

    private XMLSimulationParser parser;
    private Scanner scanner;

    public Grid(File file){
        myConfigFile = file;
        parser =  new XMLSimulationParser(myConfigFile);
        scanner = new Scanner(parser.getInitialGrid());
        myCellColors = parser.getColors();
        myNumRows = parser.getNumRows();
        myNumCols = parser.getNumCols();
        myNeighborRules = parser.getMainNeighborRules();
        myEdgeNeighborRules = parser.getEdgeNeighborRules();
//        myCellsMatrix = Cell[myNumRows][myNumCols];
        myCellsMatrix = new ArrayList<>();
        myCellsMap = new HashMap<>();
        createGridOfCells();
        runBFSOnCells(myCellsMatrix.get(0).get(0), myNeighborRules);
        if (! myEdgeNeighborRules.equals(null)) {
            runBFSOnCells(myCellsMatrix.get(0).get(0), myEdgeNeighborRules);
        }
    }

    public Cell getCell(int id){
        return myCellsMap.get(id);
    }

    public List<Cell> getEmptyCells(){
        List<Cell> emptyCells = new ArrayList<>();
        for(List<Cell> cellRow : myCellsMatrix){
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
        while(scanner.hasNext()){
            for (int i = 0; i < myNumRows; i++){
                myCellsMatrix.add(new ArrayList<>());
                for (int j = 0; j < myNumCols; j++){
                    int state = scanner.nextInt();
                    myCellsMatrix.get(i).add(new Cell(state, i, j));
                    myCellsMap.put(id, myCellsMatrix.get(i).get(j));
                    id++;
                }
            }
        }
    }

    private void runBFSOnCells(Cell startingCell, Map<Integer, List<Integer>> bfsRules) {
        resetBFSChecked();
        Queue<Cell> cq = new LinkedList<>();
        cq.add(startingCell);
        startingCell.setBfsChecked(true);

        while (cq.size() > 0) {
            Cell currentCell = cq.remove();

            int neighborRow;
            int neighborCol;
            for (int row : bfsRules.keySet()) {
                neighborRow = currentCell.getRow() + row;
                for (int col : bfsRules.get(row)) {
                    neighborCol = currentCell.getCol() + col;
                    if (inRange(neighborRow, neighborCol)) {
                        if (!myCellsMatrix.get(neighborRow).get(neighborCol).bfsChecked()) {
                            cq.add(myCellsMatrix.get(neighborRow).get(neighborCol));
                        }
                        currentCell.addToNeighbor(myCellsMatrix.get(neighborRow).get(neighborCol));
                        myCellsMatrix.get(neighborRow).get(neighborCol).setBfsChecked(true);
                    }
                }
            }
        }
    }

    private void resetBFSChecked() {
        for (Integer cellId : myCellsMap.keySet()) {
            myCellsMap.get(cellId).setBfsChecked(false);
        }
    }

    private boolean inRange(int row, int col) {
        if (row < 0 || row > myNumRows - 1 || col < 0 || col > myNumCols - 1) {
            return false;
        }
        return true;
    }
}
