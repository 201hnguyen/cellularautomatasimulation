package config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

// Adapted from code given in class -- Spike Simulation

/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 */
public class XMLParser {
    // Readable error message that can be displayed by the GUI
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    // name of root attribute that notes the type of file expecting to parse
    private final String TYPE_ATTRIBUTE;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;
    private File myFile;
    private Element root;


    /**
     * Create parser for XML files of given type.
     */
    public XMLParser (String type, File file) {
        myFile = file;
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
        root = getRootElement(myFile);
    }

    public int getNumRows(){
        return Integer.parseInt(root.getElementsByTagName("num_rows").item(0).getTextContent());
    }

    public int getNumCols(){
        return Integer.parseInt(root.getElementsByTagName("num_columns").item(0).getTextContent());
    }

    public int getNumNeighbors(){
        return Integer.parseInt(root.getElementsByTagName("num_neighbors").item(0).getTextContent());
    }

    public String getTitle() {
        return root.getAttribute("title");
    }

    public int getSceneWidthWithBar() {
        return Integer.parseInt(root.getAttribute("sceneWidthWithBar"));
    }

    public int getSceneWidth() {
        return Integer.parseInt(root.getAttribute("sceneWidth"));
    }

    public int getSceHeight() {
        return Integer.parseInt(root.getAttribute("sceneHeight"));
    }

    public ArrayList<String> getSimulationButtons() {
        ArrayList<String> simulationButtons = new ArrayList<>();
        simulationButtons.add(root.getElementsByTagName("playButton").item(0).getTextContent());
        simulationButtons.add(root.getElementsByTagName("pauseButton").item(0).getTextContent());
        simulationButtons.add(root.getElementsByTagName("stepButton").item(0).getTextContent());
        simulationButtons.add(root.getElementsByTagName("speedButton").item(0).getTextContent());
        simulationButtons.add(root.getElementsByTagName("slowButton").item(0).getTextContent());
        simulationButtons.add(root.getElementsByTagName("loadButton").item(0).getTextContent());
        simulationButtons.add(root.getElementsByTagName("homeButton").item(0).getTextContent());
        return simulationButtons;
    }

    public String getInitialGrid(){
        return root.getElementsByTagName("initial-states-grid").item(0).getTextContent();
    }

    public double getSimulationParameter1(){
        return Double.parseDouble(root.getElementsByTagName("parameter1").item(0).getTextContent());
    }

    public double getSimulationParameter2(){
        return Double.parseDouble(root.getElementsByTagName("parameter2").item(0).getTextContent());
    }

    public double getSimulationParameter3(){
        return Double.parseDouble(root.getElementsByTagName("parameter3").item(0).getTextContent());
    }

    public double getSimulationParameter4(){
        return Double.parseDouble(root.getElementsByTagName("parameter4").item(0).getTextContent());
    }

    public double getSimulationParameter5(){
        return Double.parseDouble(root.getElementsByTagName("parameter5").item(0).getTextContent());
    }

    public String getSimulationType() {
        return root.getAttribute("simulationType");
    }

    public String[] getCellColors(){
        String[] cellColors = new String[3];
        cellColors[0] = root.getAttribute("Color0").toString();
        cellColors[1] = root.getAttribute("Color1").toString();
        cellColors[2] = root.getAttribute("Color2").toString();
        return cellColors;
    }

    public String getIntroButton() {
        return root.getElementsByTagName("introLoadFileButton").item(0).getTextContent();
    }

    /**
     * Get data contained in this XML file as an object
     */

    // get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

    // get value of Element's text
    private String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or exception? In some cases it may be an error to not find any text
            return "";
        }
    }

    // boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}

