package com.roque.rueda.logsearch;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public final class LogXmlParser {

    private LogXmlParser() {
        // Prevent instantiation of utility class
    }

    /**
     * Take an xml string and tries to convert it to a w3c DOM Document
     * @param inputXml String with the xml content
     * @return Document instance result of parse or null if there is an exception
     */
    public static Document parseStringToXml(String inputXml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(inputXml)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            // TODO: How we should handle this?
            e.printStackTrace();
        }

        // Fail to parse the xml
        return null;
    }
}
