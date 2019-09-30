package config;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XMLGenerator {
    private Document myXMLDocument;
    private Element myRoot;

    public XMLGenerator() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
            myXMLDocument = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace(); //TODO: Fix this
        }
    }

    public void generateSimulationXMLDocument() {
        myRoot = myXMLDocument.createElement("simulation");
        myXMLDocument.appendChild(myRoot);
        addAttribute(myRoot,"simulationType", "Game of Life");
        addColorsNode();
        Map<String, String> rowsColumnsNeighbors = new HashMap<>() {{
            put("num_rows", "20");
            put("num_cols", "20");
            put("num_neighbors", "8");
            put("neighbor_configuration", "-1 3 0 3 1 3");
        }};
        for (Map.Entry<String, String> entry : rowsColumnsNeighbors.entrySet()) {
            addChildNodeHelper(myRoot, entry.getKey(), entry.getValue());
        }
        addParametersNode();
        addGridNode();
        writeContentToXML();
    }

    private void addAttribute(Element parent, String attributeName, String attributeValue) {
        Attr attribute = myXMLDocument.createAttribute(attributeName);
        attribute.setValue(attributeValue);
        parent.setAttributeNode(attribute);
    }

    private void addParametersNode() {
        Element parametersNode = myXMLDocument.createElement("parameters");
        Map<String, String> parametersMap = new HashMap<>() {{
            put("min_population_threshold", "min_population_threshold:2");
            put("max_population_threshold", "max_population_threshold:3");
        }};
        for (Map.Entry<String, String> entry : parametersMap.entrySet()) {
            addChildNodeHelper(parametersNode, entry.getKey(), entry.getValue());
        }
        myRoot.appendChild(parametersNode);
    }

    private void addGridNode() {
        Element gridNode = myXMLDocument.createElement("initial_rectangular_grid");
        gridNode.setNodeValue("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 1 2 2 2 2 1 2 1 2 2 2 2 1 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 1 1 1 2 2 2 1 1 1 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0\n" +
                "        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        myRoot.appendChild(gridNode);
    }

    private void addColorsNode() {
        Element colorsNode = myXMLDocument.createElement("colors");
        Map<String, String> statesColorsMap = new HashMap<>() {{
            put("empty", "White");
            put("live", "Blue");
            put("dead", "Yellow");
        }};
        for (String key : statesColorsMap.keySet()) {
            addChildNodeHelper(colorsNode, key, statesColorsMap.get(key));
        }
        myRoot.appendChild(colorsNode);
    }

    private void addChildNodeHelper(Element parent, String childTagName, String textContent) {
        Element child = myXMLDocument.createElement(childTagName);
        child.setTextContent(textContent);
        parent.appendChild(child);
    }

    private void writeContentToXML() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(myXMLDocument);
            StreamResult result = new StreamResult(new File("Resources/testfile.xml"));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            //TODO: Figure out how to do this shit.
        }
    }
}
