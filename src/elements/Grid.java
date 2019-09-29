package elements;

import config.XMLException;
import config.XMLSimulationParser;

import java.io.File;
import java.util.*;

public class Grid implements Iterable<Cell> {
    private File myConfigFile;
    private XMLSimulationParser myXMLParser;
    private Scanner mySc;
    private String[] myCellColors;
    private TreeSet<Cell> myCells;
    private int myNumRows;
    private int myNumCols;
    private String myNeighborConfiguration;

    public Grid(File file){
        myConfigFile = file;
        myXMLParser =  new XMLSimulationParser(myConfigFile);
        mySc = new Scanner(myXMLParser.getInitialGrid());
        myCellColors = myXMLParser.getColors();
        myNumRows = myXMLParser.getNumRows();
        myNumCols = myXMLParser.getNumCols();
        myCells = new TreeSet<>();
    }

    public TreeSet<Cell> configureCells() throws NoSuchElementException{
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

    private void createGridOfCells() throws NoSuchElementException {
        int id = 0;
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

    private void setCellNeighbors() {
        for(Cell cell: this){
            ArrayList<Cell> neighbors = null;
            int cell_row = cell.getMyID()/(myNumCols);
            int cell_column = cell.getMyID()%(myNumRows);
            neighbors = checkNeighborsForCell(cell_row, cell_column);
            cell.setMyNeighbors(neighbors);
            if (cell.getMyID() == 37) {
                for(Cell neighbor: cell.getMyNeighbors()) {
                    System.out.print(neighbor.getMyID() + ", ");
                }
            }
        }
    }

    //This class takes pattern as input from xml
    private ArrayList<Cell> checkNeighborsForCell(int row, int column){
        //The "pattern" around the cell in question used to determine its neighborhood comes from config
        int original_column = column;
        int original_row = row;
        ArrayList<Cell> neighbors = new ArrayList<>();
        myNeighborConfiguration = myXMLParser.getNeighborConfiguration();
        Scanner sc = new Scanner(myNeighborConfiguration);
        while(sc.hasNextInt()){
            int row_modifier = sc.nextInt();
            row = original_row + row_modifier;
            int number_of_neighbors_in_row = sc.nextInt();
            for(int i = 0; i < number_of_neighbors_in_row; i++){
                column = original_column - 1 + i;
                if(column > -1 && column < myNumCols && row > -1 && row < myNumRows) {
                    if(column != original_column || row != original_row) {
                        neighbors.add(getCell(toCellID(row, column)));
                    }
                }
            }
        }
        return neighbors;
    }

    private int toCellID(int row, int column){
        return row*myNumCols + column;
    }
    public File getMyConfigFile(){
        return myConfigFile;
    }

    public int getSize() {
        return myNumRows*myNumCols;
    }

    @Override
    public Iterator<Cell> iterator() {
        return this.myCells.iterator();
    }
}