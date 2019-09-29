package config;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class XMLSimulationParser extends XMLParser {

    public static final String VALID_SIMULATION_TAG = "simulation";
    public static final String[] GRID_SHAPES_TAGS = new String[]{"initial_rectangular_grid", "initial_triangular_grid"};
    public static final String SIMULATION_TYPE_ATTRIBUTE = "simulationType";

    public XMLSimulationParser(File file) {
        super(file);
    }

    public String getSimulationType() {
        return super.getRoot().getAttribute(SIMULATION_TYPE_ATTRIBUTE);
    }

    public int getNumRows() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_rows").item(0).getTextContent());
    }

    public int getNumCols() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_columns").item(0).getTextContent());
    }

    public String getInitialGrid() {
        return super.getRoot().getElementsByTagName("initial_rectangular_grid").item(0).getTextContent();
    }

    public String[] getColors() {
         NodeList colorNodes = super.getRoot().getElementsByTagName("colors");
         String[] colors = colorNodes.item(0).getTextContent().trim().split("\\s+");
         return colors;
    }

    private Map<Integer, List<Integer>> getNeighborRulesHelper(String neighborRules) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        String s = super.getRoot().getElementsByTagName(neighborRules).item(0).getTextContent();
        if (s.length() == 0) {
            return map;
        }
        for (String sub : s.split("/")) {
            String[] subSplit = sub.split("c");
            Integer key = Integer.parseInt(subSplit[0].replaceAll(" ", ""));
            List<Integer> values = new ArrayList<>();
            String[] valuesAsStrings = subSplit[1].trim().split("\\s");
            for (String value : valuesAsStrings) {
                values.add(Integer.parseInt(value));
            }
            map.put(key, values);
        }
        return map;
    }

    public Map<String, Double> getParameters() {
        Map<String, Double> parameters = new HashMap<>();
        NodeList parameterNodes = super.getRoot().getElementsByTagName("parameters"); //TODO: Check for when tag doesn't exist (Perc)
        String parametersAsOneString = parameterNodes.item(0).getTextContent().trim();
        String[] parametersStringArray = parametersAsOneString.split("\\s+");
        for (String parameter : parametersStringArray) {
            String[] keyValueArray = parameter.split(":");
            parameters.put(keyValueArray[0], Double.parseDouble(keyValueArray[1]));
        }
        return parameters;
    }

    public String getNeighborConfiguration(){
        return super.getRoot().getElementsByTagName("neighbor_configuration").item(0).getTextContent();
    }

}
