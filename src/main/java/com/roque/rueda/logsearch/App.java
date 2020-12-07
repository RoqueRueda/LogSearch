package com.roque.rueda.logsearch;

import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;

/**
 * Made with love :-D
 */
public class App 
{
    public static final String CONFIG_NAME = "config.properties";
    public static final String LOG_READER = "Log Reader";
    public static final String NO_FILE_SELECTED_ERROR = "No log file was selected";

    public static void main( String[] args )
    {
        System.out.println("Start");
        // Open properties file
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        String logFilePath = null;
        String regex = null;

        try(InputStream input = loader.getResourceAsStream(CONFIG_NAME)) {
            properties.load(input);
            System.out.println("Loading properties file!");
            logFilePath = properties.getProperty("LogFile");
            regex = properties.getProperty("Regex");
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
            System.exit(-1);
        }

        System.out.println("Provided file is:" + file.getAbsolutePath());
        LogReader reader = LogReader.fromFilePath(file.getAbsolutePath());
        System.out.println("Your regex is:" + regex);

        RegexInput regexInput = new RegexInput(regex);
        Matcher matcher = regexInput.getPattern().matcher(reader.getFileText());

        while (matcher.find()) {
            System.out.println("One Match found at start:" + matcher.start() + ", end:" + matcher.end());
            final Document document = LogXmlParser.parseStringToXml(matcher.group());

            if (document != null) {
                System.out.println("=== XML Found ===");
                System.out.println(document.getDocumentElement());
                System.out.println();

                //showXmlWindow(document);
            } else {
                System.out.println("Can not parse the xml! :'c");
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
