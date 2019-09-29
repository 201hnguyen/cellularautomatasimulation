package config;

import javafx.scene.control.Alert;

/**
 * This class represents what might go wrong when using XML files.
 *
 * @author Robert C. Duvall
 */
public class XMLException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;


    /**
     * Create an exception based on an issue in our code.
     */
    public XMLException (String message, Object ... values) {
        super(String.format(message, values));
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
