package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {
    private final String ROOT_NAME;
    private final DocumentBuilder DOCUMENT_BUILDER;
    private File myFile;
    private Element myRoot;

    public XMLParser(File file) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        myFile = file;
        try {
            myRoot = getRootElement(myFile);
        } catch (XMLException e) {
            e.showInvalidFileAlert();
        }
        ROOT_NAME = myRoot.getTagName();
    }

    protected Element getRoot() {
        return myRoot;
    }

    protected boolean isValidFile(String validTag) {
        return ROOT_NAME.equals(validTag);
    }

    private Element getRootElement(File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    private DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }

}
