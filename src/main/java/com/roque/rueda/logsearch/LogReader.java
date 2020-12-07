package com.roque.rueda.logsearch;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class LogReader {

    private String filePath;

    /**
     * Builds a new instance using the file path to get a reference to the log file
     * @param filePath the path of the log file
     */
    private LogReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieve the contents of the file
     * @return a single string with all the content of the file
     */
    public String getFileText() {
        // TODO: Check for a better or more efficient way
        try {
            return IOUtils.toString(new FileInputStream(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Build a Log Reader using a file path
     * @param filePath path to the log file
     * @return A new log reader
     */
    public static LogReader fromFilePath(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("The path of the log file is null");
        }

        return new LogReader(filePath);
    }
}
