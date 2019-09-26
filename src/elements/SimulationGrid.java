package elements;

import config.XMLParser;

import java.io.File;
import java.util.Scanner;

public abstract class SimulationGrid {
    protected int myNumNeighbors;
    protected File myConfigFile;
    protected XMLParser myXMLParser;
    protected Scanner mySc;
    protected String[] myCellColors;

    protected SimulationGrid(File file){
        myConfigFile = file;
        myXMLParser =  new XMLParser("simulationType", myConfigFile);
        mySc = new Scanner(myXMLParser.getInitialGrid());
        myCellColors = myXMLParser.getCellColors();
    }
}
