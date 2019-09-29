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
    private static final List<String> xmlValidationSchemas = new ArrayList<>() {{
       add("Resources/simulation_config_schema/GameOfLifeConfig.xsd");
       add("Resources/simulation_config_schema/PercolationConfig.xsd");
       add("Resources/simulation_config_schema/PredatorPreyComfig.xsd");
       add("Resources/simulation_config_schema/SpreadingOfFireConfig.xsd");
       add("Resources/simulaton_config_schema/Segregation.xsd");
    }};

    /**
     * Create an exception based on an issue in our code.
     */
    public XMLException (String message, Object ... values) {
        super(String.format(message, values));
    }

    public static boolean isValidSimulationSchema(File file) {
        for (String xmlSchema : xmlValidationSchemas) {
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

    public static void showInvalidFileAlert() {
        Alert invalidFileAlert = new Alert(Alert.AlertType.ERROR);
        invalidFileAlert.setHeaderText("Input file not valid."); //TODO: This should be read in from config
        invalidFileAlert.setContentText("Please load a Simulation Config XML File");
        invalidFileAlert.showAndWait();
    }

    public static void showInvalidSimulationAlert() {
        Alert invalidSimulationAlert = new Alert(Alert.AlertType.ERROR);
        invalidSimulationAlert.setHeaderText("Simulation not supported");
        invalidSimulationAlert.setContentText("This program only supports \"Game of Life\", \"Percolation\", \"Predator and Prey\"," +
                "\"Segregation\", and \"Spreading of Fire\" Simulation.");
        invalidSimulationAlert.show();
    }

    public static void showGridWarning() {
        Alert gridSizeInvalidAlert = new Alert(Alert.AlertType.WARNING);
        gridSizeInvalidAlert.setHeaderText("Inconsistent grid size found");
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
