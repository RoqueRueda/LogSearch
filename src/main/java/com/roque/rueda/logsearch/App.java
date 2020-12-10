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
