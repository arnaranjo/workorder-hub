package com.workorderhub.provider.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;


/**
 * Static class to provide methods to display messages.
 */
public class Util {

    private Util() {
    }

    /**
     * Requests a confirmation to the user.
     *
     * @param title   title of the window.
     * @param message message showed in the window.
     * @return true or false
     */
    public static boolean RequestConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(PropertiesLoader.GetText(title));
        alert.setHeaderText(null);
        alert.setContentText(PropertiesLoader.GetText(message));
        alert.setResizable(false);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Displays a message of information.
     *
     * @param title   title of the window.
     * @param message message showed in the window.
     */
    public static void ShowMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(PropertiesLoader.GetText(title));
        alert.setHeaderText(null);
        alert.setContentText(PropertiesLoader.GetText(message));
        alert.setResizable(false);

        alert.showAndWait();
    }

    /**
     * Displays a message with additional information.
     *
     * @param title             title of the window.
     * @param message           message showed in the window.
     * @param additionalMessage message added to provide additional information.
     */
    public static void ShowMessage(String title, String message, String additionalMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(PropertiesLoader.GetText(title));
        alert.setHeaderText(null);
        alert.setContentText(PropertiesLoader.GetText(message) + " " + additionalMessage);
        alert.setResizable(false);

        alert.showAndWait();
    }
}
