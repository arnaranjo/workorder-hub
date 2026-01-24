package com.workorderhub.provider.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader {

    private ViewLoader() {}

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
