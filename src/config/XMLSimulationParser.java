package config;

import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLSimulationParser extends XMLParser {

    private static final String VALID_SIMULATION_TAG = "simulation";

    public XMLSimulationParser(File file) {
        super(file);
        if (!isValidFile(VALID_SIMULATION_TAG)) {
            // TODO: Notify user that this is invalid file
        }
    }

    public String getSimulationType() {
        return super.getRoot().getAttribute("simulationType");
    }

    public int getNumRows() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_rows").item(0).getTextContent());
    }

    public int getNumCols() {
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_columns").item(0).getTextContent());
    }

    public int getNumNeighbors(){
        return Integer.parseInt(super.getRoot().getElementsByTagName("num_neighbors").item(0).getTextContent());
    }

    public String getInitialGrid() {
        return super.getRoot().getElementsByTagName("initial_rectangular_grid").item(0).getTextContent();
    }

    public String[] getColors() {
         NodeList colorNodes = super.getRoot().getElementsByTagName("colors");
         String[] colors = colorNodes.item(0).getTextContent().trim().split("\\s+");
         return colors;
    }

    public Map<Integer, List<Integer>> getNeighborRules() {
        Map<Integer, List<Integer>> rulesMap = new HashMap<>();
        String s = super.getRoot().getElementsByTagName("neighbor_rules").item(0).getTextContent();
        for (String sub : s.split("/")) {
            String[] subSplit = sub.split("c");
            Integer key = Integer.parseInt(subSplit[0].replaceAll(" ", ""));
            List<Integer> values = new ArrayList<>();
            String[] valuesAsStrings = subSplit[1].trim().split("\\s");
            for (String value : valuesAsStrings) {
                values.add(Integer.parseInt(value));
            }
            rulesMap.put(key, values);
        }
        return rulesMap;
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
}
