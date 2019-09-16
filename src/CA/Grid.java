package CA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Grid{
    private Cell[][] cells;
    private int num_rows;
    private int num_columns;
    private int num_neighbors;
    private File myConfigFile;

    public Grid(File file){
        myConfigFile = file;
        Scanner sc = null;
        try {
            sc = new Scanner(myConfigFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        num_rows = sc.nextInt();
        num_columns = sc.nextInt();
        cells = new Cell[num_rows][num_columns];
        sc.close();
    }

    public void configureCells(){
        createGridOfCells();
        setCellNeighbours();
    }

    private void createGridOfCells() {
        Scanner sc = null;
        try {
            sc = new Scanner(myConfigFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        num_neighbors = sc.nextInt();
        while(sc.hasNext()){
            for (int i = 0; i < num_rows; i++){
                for (int j = 0; j < num_columns; j++){
                    int state = sc.nextInt();
                    cells[i][j] = new Cell(state, i, j);
                }
            }
        }
    }

    //This method assumes that all simulations will either consider 4 neighbors (NSWE) or 8 (NSWE + the 4 directions in between)
    private void setCellNeighbours(){
        Cell[] neighbours = null;
        for (int i = 0; i < num_rows; i++){
            for (int j = 0; j < num_columns; j++) {
                    if(num_neighbors == 4){
                        if(i == 0)
                        {
                            if(j == 0){
                                neighbours = new Cell[2];
                                neighbours[0] = cells[i+1][j];
                                neighbours[1] = cells[i][j+1];
                            }
                            else if (j == num_rows -1){
                                neighbours = new Cell[2];
                                neighbours[0] = cells[i+1][j];
                                neighbours[1] = cells[i][j-1];
                            }
                            else{
                                neighbours = new Cell[3];
                                neighbours[0] = cells[i][j-1];
                                neighbours[1] = cells[i][j+1];
                                neighbours[2] = cells[i+1][j];
                            }
                        }
                        else if (i == num_columns - 1){
                            if (j == 0){
                                neighbours = new Cell[2];
                                neighbours[0] = cells[i-1][j];
                                neighbours[1] = cells[i][j+1];
                            }
                            else if (j == num_rows - 1){
                                neighbours = new Cell[2];
                                neighbours[0] = cells[i-1][j];
                                neighbours[1] = cells[i][j-1];
                            }
                            else{
                                neighbours = new Cell[3];
                                neighbours[0] = cells[i][j-1];
                                neighbours[1] = cells[i][j+1];
                                neighbours[2] = cells[i-1][j];
                            }
                        }
                        else if (j == 0){
                            neighbours = new Cell[3];
                            neighbours[0] = cells[i-1][j];
                            neighbours[1] = cells[i+1][j];
                            neighbours[2] = cells[i][j+1];
                        }
                        else if (j == num_rows - 1){
                            neighbours = new Cell[3];
                            neighbours[0] = cells[i-1][j];
                            neighbours[1] = cells[i+1][j];
                            neighbours[2] = cells[i][j-1];
                        }
                    }
                    else if(num_neighbors == 8){
                        if(i == 0)
                        {
                            if(j == 0){
                                neighbours = new Cell[3];
                                neighbours[0] = cells[i+1][j];
                                neighbours[1] = cells[i][j+1];
                                neighbours[2] = cells[i+1][j+1];
                            }
                            else if (j == num_rows -1){
                                neighbours = new Cell[3];
                                neighbours[0] = cells[i+1][j];
                                neighbours[1] = cells[i][j-1];
                                neighbours[2] = cells[i+1][j-1];
                            }
                            else{
                                neighbours = new Cell[5];
                                neighbours[0] = cells[i][j-1];
                                neighbours[1] = cells[i][j+1];
                                neighbours[2] = cells[i+1][j];
                                neighbours[3] = cells[i+1][j+1];
                                neighbours[4] = cells[i+1][j-1];
                            }
                        }
                        else if (i == num_columns - 1){
                            if (j == 0){
                                neighbours = new Cell[3];
                                neighbours[0] = cells[i-1][j];
                                neighbours[1] = cells[i][j+1];
                                neighbours[2] = cells[i-1][j-1];
                            }
                            else if (j == num_rows - 1){
                                neighbours = new Cell[2];
                                neighbours[0] = cells[i-1][j];
                                neighbours[1] = cells[i][j-1];
                                neighbours[2] = cells[i-1][j-1];
                            }
                            else{
                                neighbours = new Cell[5];
                                neighbours[0] = cells[i][j-1];
                                neighbours[1] = cells[i][j+1];
                                neighbours[2] = cells[i-1][j];
                                neighbours[3] = cells[i-1][j-1];
                                neighbours[4] = cells[i-1][j+1];
                            }
                        }
                        else if (j == 0){
                            neighbours = new Cell[5];
                            neighbours[0] = cells[i-1][j];
                            neighbours[1] = cells[i+1][j];
                            neighbours[2] = cells[i][j+1];
                            neighbours[3] = cells[i-1][j+1];
                            neighbours[4] = cells[i+1][j+1];
                        }
                        else if (j == num_rows - 1){
                            neighbours = new Cell[5];
                            neighbours[0] = cells[i-1][j];
                            neighbours[1] = cells[i+1][j];
                            neighbours[2] = cells[i][j-1];
                            neighbours[3] = cells[i-1][j-1];
                            neighbours[4] = cells[i+1][j-1];
                        }

                    }
            }
        }
    }
}