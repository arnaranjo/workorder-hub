package com.workorderhub.provider.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

    private static FileInputStream propertiesInputStream;
    private static FileInputStream sizeInputStream;
    private static final Properties texts = new Properties();
    private static final Properties size = new Properties();

    static {
        try {
            propertiesInputStream = new FileInputStream(
                    "src/main/java/com/workorderhub/provider/ui/properties/config.properties"
            );
            sizeInputStream = new FileInputStream(
                    "src/main/java/com/workorderhub/provider/ui/properties/windows-size.properties"
            );
            texts.load(propertiesInputStream);
            size.load(sizeInputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Util() {
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

    /**
     * Loads the window into a resizable view.
     *
     * @param title     Title of the screen.
     * @param minWidth  minimum window width.
     * @param minHeight minimum window height.
     */
    public static void LoadView(
            FXMLLoader fxmlLoader,
            String title,
            double minWidth,
            double minHeight
    ) {
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Loads the window into a fixed view.
     *
     * @param title Title of the screen.
     */
    public static void LoadView(
            FXMLLoader fxmlLoader,
            String title
    ) {
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
