package com.workorderhub.infrastructure.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Static class to provide methods load texts or numbers from the properties files.
 */
public class PropertiesLoader {

    private static FileInputStream propertiesInputStream;
    private static FileInputStream sizeInputStream;
    private static final Properties texts = new Properties();
    private static final Properties size = new Properties();

    static {
        try {
            propertiesInputStream = new FileInputStream(
                    "src/main/java/com/workorderhub/infrastructure/ui/properties/config.properties"
            );
            sizeInputStream = new FileInputStream(
                    "src/main/java/com/workorderhub/infrastructure/ui/properties/windows-size.properties"
            );
            texts.load(propertiesInputStream);
            size.load(sizeInputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PropertiesLoader() {
    }

    /**
     * Get the text store in the properties file.
     *
     * @param propertyName name of the property.
     * @return text of the property.
     */
    public static String GetText(String propertyName) {
        return texts.getProperty(propertyName);
    }

    /**
     * Get an Array of string from the property, it is delimited by ",".
     *
     * @param propertyName name of the property.
     * @return Array of string.
     */
    public static String[] GetStringArray(String propertyName) {
        return texts.getProperty(propertyName).split(",");
    }

    /**
     * Get a Double parsed from the property.
     *
     * @param propertyName name of the property.
     * @return Double value from the property.
     */
    public static double GetDouble(String propertyName) {
        return Double.parseDouble(size.getProperty(propertyName));
    }
}
