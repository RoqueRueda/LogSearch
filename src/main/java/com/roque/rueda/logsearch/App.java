package com.roque.rueda.logsearch;

import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.regex.Matcher;

/**
 * Made with love :-D
 */
public class App 
{
    public static final String LOG_READER = "Log Reader";
    public static final String NO_FILE_SELECTED_ERROR = "No log file was selected";

    public static void main( String[] args )
    {
        // Select a file
        File file = openFile();
        //File file = fixedFile();

        if (fileIsNull(file)) {
            // Can't continue if there is no file
            System.exit(-1);
            return;
        }

        assert file != null;
        System.out.println("File you select is:" + file.getAbsolutePath());

        LogReader reader = LogReader.fromFilePath(file.getAbsolutePath());

        String regex = inputRegexFromUser();
        System.out.println("Regex you provide is:" + regex);

        RegexInput regexInput = new RegexInput(regex);
        Matcher matcher = regexInput.getPattern().matcher(reader.getFileText());

        //String element = JOptionPane.showInputDialog("Insert Element:");
        //String attribute = JOptionPane.showInputDialog("Insert Attribute:");
        //String value = JOptionPane.showInputDialog("Insert Attribute value:");

        while (matcher.find()) {
            System.out.println("One Match found at start:" + matcher.start() + ", end:" + matcher.end());
            final Document document = LogXmlParser.parseStringToXml(matcher.group());

            if (document != null) {
                System.out.println("=== XML Found ===");
                System.out.println(document.getDocumentElement());
                System.out.println();

                showXmlWindow(document);
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

    public static File openFile() {
        JFileChooser chooser = new JFileChooser();
        // Allow only text files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(
                    null,
                    NO_FILE_SELECTED_ERROR,
                    LOG_READER,
                    JOptionPane.ERROR_MESSAGE);
            return null;
        } else {
            // TODO: Maybe I should handle this other way
            return null;
        }
    }

    public static File fixedFile() {
        return new File("/Users/roque/Documents/LogSearch/src/main/resources/ExampleLog.txt");
    }

    public static Boolean fileIsNull(File f) {
        return f == null;
    }

    private static String inputRegexFromUser() {
        // Example regex
        // (?<XML><[ns3:]{0,}:AddContainerRequest [\s\S]*?<\/[ns3:]{0,}AddContainerRequest>)
        //return JOptionPane.showInputDialog("Enter regex");
        return JOptionPane.showInputDialog("Enter regex",
                "(?<XML><[ns3:]{0,}:AddContainerRequest [\\s\\S]*?<\\/[ns3:]{0,}AddContainerRequest>)");
    }
}
