package com.roque.rueda.logsearch;

import com.roque.rueda.logsearch.file.FileUtils;
import com.roque.rueda.logsearch.messages.xml.AddContainerRequest;
import com.roque.rueda.logsearch.messages.xml.ContainerEvent;
import org.w3c.dom.Document;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

/**
 * Made with love :-D
 */
public class App 
{
    public static final String CONFIG_NAME = "config.properties";
    public static final String LOG_FILE_KEY = "LogFile";
    public static final String CONTAINER_NAME_KEY = "ContainerName";

    public static void main( String[] args )
    {
        System.out.println("Start");
        // Open properties file
        File file = obtainLogFileFromProperties();
        String containerNameValue = FileUtils.obtainPropertyFromPropertiesFile(CONTAINER_NAME_KEY);

        AddContainerRequest addContainerRequest = AddContainerRequest.readFromXml(
            Objects.requireNonNull(file).getAbsolutePath(),
            AddContainerRequest.obtainRegexFromPropertiesFile(),
            AddContainerRequest.obtainXpathForContainerName(),
            containerNameValue
        );

        if (addContainerRequest != null) {
            System.out.println("Add Container Request");
            System.out.println(addContainerRequest.getContainerName());
        }

        ContainerEvent containerEvent = ContainerEvent.readFromXml(
            Objects.requireNonNull(file).getAbsolutePath(),
            ContainerEvent.obtainRegexFromPropertiesFile(),
            ContainerEvent.obtainXpathForContainerName(),
            containerNameValue
        );

        if (containerEvent != null) {
            System.out.println("Container Event");
            System.out.println(containerEvent.getContainerName());
        }

        assert addContainerRequest != null;
        assert containerEvent != null;

        if(
            addContainerRequest.getContainerName().equals(containerEvent.getContainerName()) &&
            addContainerRequest.getLaneId().equals(containerEvent.getLaneId()) &&
            addContainerRequest.getOffset().equals(containerEvent.getOffset()) &&
            addContainerRequest.getTier().equals(containerEvent.getTier()) &&
            containerEvent.getAction().equals("Added")
        ) {
            System.out.println("Success");
        } else {
            System.out.println("Fail");
        }


        /*

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
                String containerName = properties.getProperty(CONTAINER_NAME_KEY);
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
                                    if (nodes.item(i).getNodeValue().equalsIgnoreCase(containerName)) {
                                        System.out.println("Result of XPath:" + nodes.item(i).getNodeValue());
                                        System.out.println(nodes.item(i));
                                    }
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
         */
    }

    private static File obtainLogFileFromProperties() {
        String logFilePath = FileUtils.obtainPropertyFromPropertiesFile(LOG_FILE_KEY);
        if (logFilePath != null) {
            return new File(logFilePath);
        } else {
            return null;
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
