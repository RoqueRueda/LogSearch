package com.roque.rueda.logsearch.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtils {

    private static final String CONFIG_NAME = "config.properties";

    private FileUtils() {
        // Nothing here...
    }

    public static String obtainPropertyFromPropertiesFile(String propertyKey) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream input = loader.getResourceAsStream(CONFIG_NAME)) {
            properties.load(input);
            return properties.getProperty(propertyKey);
        } catch (IOException e) {
            return null;
        }
    }
}
