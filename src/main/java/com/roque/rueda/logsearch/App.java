package com.roque.rueda.logsearch;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * Made with love :-D
 */
public class App 
{
    public static final String CONFIG_NAME = "config.properties";
    public static final String LOG_FILE_KEY = "LogFile";
    public static final String REGEX_BASE_KEY = "Regex_%d";
    public static final String XPATH_BASE_KEY = "XPath_%d_%d";

    public static void main( String[] args )
    {
        System.out.println("Start");
        // Open properties file
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        String logFilePath = null;

        try(InputStream input = loader.getResourceAsStream(CONFIG_NAME)) {
            properties.load(input);
            System.out.println("Loading properties file!");
            logFilePath = properties.getProperty(LOG_FILE_KEY);
        } catch (FileNotFoundException e) {
            System.err.println("Properties file was not found :(!");
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("IOException :(!");
            System.exit(-1);
        }

        File file = new File(logFilePath);

        if (fileIsNull(file)) {
            // Can't continue if there is no file
            System.err.println("Can't create the file:" +  file.getAbsolutePath());
            System.exit(-1);
        }

        System.out.println("Provided file is:" + file.getAbsolutePath());
        LogReader reader = LogReader.fromFilePath(file.getAbsolutePath());

        int regexCounter = 1;
        boolean regexPendingForRead = true;

        // Iterate until there is no more regex
        while (regexPendingForRead) {
            try(InputStream input = loader.getResourceAsStream(CONFIG_NAME)) {
                properties.load(input);
                String regexKey = String.format(REGEX_BASE_KEY, regexCounter);
                System.out.println("Retrieve regex key:" + regexKey);
                String regexFromFile = properties.getProperty(regexKey);
                System.out.println("Your regex is:" + regexFromFile);

                if (regexFromFile == null) {
                    // There is no more regex
                    break;
                }

                // Find the matches of the regex from file
                RegexInput regexInput = new RegexInput(regexFromFile);
                Matcher matcher = regexInput.getPattern().matcher(reader.getFileText());

                while (matcher.find()) {
                    System.out.println("One Match found at start:" + matcher.start() + ", end:" + matcher.end());
                    final Document xmlDocument = LogXmlParser.parseStringToXml(matcher.group());

                    if (xmlDocument != null) {
                        System.out.println("=== XML Found ===");
                        //System.out.println(xmlDocument.getDocumentElement());

                        int xPathCounter = 1;
                        String currentXpath;
                        String xPathKey = String.format(XPATH_BASE_KEY, regexCounter, xPathCounter);
                        while ((currentXpath = properties.getProperty(xPathKey)) != null) {
                            System.out.println("Current XPath:" + currentXpath);

                            XPathFactory xpathfactory = XPathFactory.newInstance();
                            XPath xPath = xpathfactory.newXPath();
                            XPathExpression expression = xPath.compile(currentXpath);
                            Object result = expression.evaluate(xmlDocument, XPathConstants.NODESET);
                            NodeList nodes = (NodeList) result;
                            if (nodes.getLength() > 0) {
                                System.out.println("===Match for XPath===");
                                for (int i = 0; i < nodes.getLength(); i++) {
                                    System.out.println("Result of XPath:" + nodes.item(i).getNodeValue());
                                    System.out.println(nodes.item(i));
                                }
                            } else {
                                System.out.println("===No match found for Xpath: " + currentXpath + "===");
                                System.out.println("Matches: " + nodes.getLength());
                            }

                            xPathCounter ++;
                            xPathKey = String.format(XPATH_BASE_KEY, regexCounter, xPathCounter);
                            System.out.println();
                            //nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
                            //showXmlWindow(document);
                        }
                    } else {
                        System.out.println("Can not parse the xml! :'c");
                    }
                }
                regexCounter ++;
            } catch (IOException | XPathExpressionException e) {
                regexPendingForRead = false;
            }
        }
    }

    private static void showXmlWindow(Document document) {
        JFrame frame = new XmlTreeFrame(document.getDocumentElement().getTagName(), document);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static Boolean fileIsNull(File f) {
        return f == null;
    }
}
