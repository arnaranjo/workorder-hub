package com.workorderhub.provider.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Class to provide methods to load new windows.
 */
public class ViewLoader {

    private final Map<Class<?>, Supplier<?>> controllerCreators = new HashMap<>();

    public <T> void registerController(Class<T> clazz, Supplier<T> creator) {
        controllerCreators.put(clazz, creator);
    }

    public ViewLoader() {
    }

    /**
     * Loads the window into a fixed view.
     *
     * @param viewPath View URL.
     * @param title    Title of the screen.
     */
    public void LoadView(
            URL viewPath,
            String title
    ) {
        FXMLLoader loader = new FXMLLoader(viewPath);

        loader.setControllerFactory(type -> {
            if (controllerCreators.containsKey(type)) {
                return controllerCreators.get(type).get();
            }

            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear controlador: " + type, e);
            }
        });

        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Loads the window into a resizable view.
     *
     * @param viewPath  View URL.
     * @param title     Title of the screen.
     * @param minWidth  minimum window width.
     * @param minHeight minimum window height.
     */
    public void LoadView(
            URL viewPath,
            String title,
            double minWidth,
            double minHeight
    ) {
        FXMLLoader loader = new FXMLLoader(viewPath);

        loader.setControllerFactory(type -> {
            if (controllerCreators.containsKey(type)) {
                return controllerCreators.get(type).get();
            }

            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear controlador: " + type, e);
            }
        });

        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
