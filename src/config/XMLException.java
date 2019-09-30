package config;

import javafx.scene.control.Alert;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents what might go wrong when using XML files.
 *
 * @author Robert C. Duvall
 */
public class XMLException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;
    private static final List<String> XML_SIMULATION_VALIDATION_SCHEMAS = new ArrayList<>() {{
       add("Resources/simulation_config_schema/GameOfLifeConfig.xsd");
       add("Resources/simulation_config_schema/PercolationConfig.xsd");
       add("Resources/simulation_config_schema/PredatorPreyConfig.xsd");
       add("Resources/simulation_config_schema/SpreadingOfFireConfig.xsd");
       add("Resources/simulation_config_schema/SegregationConfig.xsd");
    }};
    private static final String XML_GAME_VALIDATION_SCHEMA = "Resources/GameConfig.xsd";

    /**
     * Create an exception based on an issue in our code.
     */
    public XMLException (String message, Object ... values) {
        super(String.format(message, values));
    }

    public static boolean isValidSimulationSchema(File file) {
        for (String xmlSchema : XML_SIMULATION_VALIDATION_SCHEMAS) {
            try {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new File(xmlSchema));
                Validator validator = schema.newValidator();
                validator.validate(new StreamSource(file));
                return true;
            } catch (SAXException | IOException e) {
                // do nothing
            }
        }
        return false;
    }

    public static boolean isValidGameSchema(File file) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(XML_GAME_VALIDATION_SCHEMA));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (SAXException | IOException e) {
            return false;
        }
        return true;
    }

    public static void showInvalidFileAlert() {
        Alert invalidFileAlert = new Alert(Alert.AlertType.ERROR);
        invalidFileAlert.setHeaderText("Input file not valid."); //TODO: This should be read in from config
        invalidFileAlert.setContentText("Please load a Simulation Config XML File");
        invalidFileAlert.showAndWait();
    }

    public static void showInvalidSimulationAlert() {
        Alert invalidSimulationAlert = new Alert(Alert.AlertType.ERROR);
        invalidSimulationAlert.setHeaderText("Simulation not supported"); //TODO: Read this in from property
        invalidSimulationAlert.setContentText("This program only supports \"Game of Life\", \"Percolation\", \"Predator and Prey\"," +
                "\"Segregation\", and \"Spreading of Fire\" Simulation.");
        invalidSimulationAlert.show();
    }

    public static void showGridInconsistencyAlert() {
        Alert gridSizeInvalidAlert = new Alert(Alert.AlertType.ERROR);
        gridSizeInvalidAlert.setHeaderText("Inconsistent grid size found"); //TODO: Read this in from property
        gridSizeInvalidAlert.setContentText("Grid size specified may not follow number of rows and columns specified in XML file.");
        gridSizeInvalidAlert.showAndWait();
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public XMLException (Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public XMLException (Throwable cause) {
        super(cause);
    }
}
