package com.workorderhub.infrastructure.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private static final String COLOR_STYLESHEET = "/css/color.css";
    private static final String COMMON_STYLESHEET = "/css/common-ui.css";

    private final Map<Class<?>, Supplier<?>> controllersMap = new HashMap<>();

    /**
     * Registers a controller supplier for a specific controller class.
     * @param clazz the controller class to register
     * @param creator a supplier that creates instances of the controller class
     * @param <T> the type of the controller class
     */
    public <T> void registerController(Class<T> clazz, Supplier<T> creator) {
        controllersMap.put(clazz, creator);
    }

    public ViewLoader() {
    }

    private Scene buildStyledScene(Parent root) {
        Scene scene = new Scene(root);
        addStylesheetIfExists(scene, COLOR_STYLESHEET);
        addStylesheetIfExists(scene, COMMON_STYLESHEET);
        return scene;
    }

    private void addStylesheetIfExists(Scene scene, String cssPath) {
        URL cssResource = getClass().getResource(cssPath);
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        }
    }

    /**
     * Creates a controller factory for `FXMLLoader`.
     *  <p>
     *  The factory first checks whether a controller supplier was registered for the requested controller class.
     *  If found, it uses that supplier. Otherwise, it creates a new controller instance using the class * no-argument constructor.
     *  </p>
     *  @return a callback that provides controller instances for FXML loading
     *  @throws RuntimeException if a controller cannot be instantiated
     */
    javafx.util.Callback<Class<?>, Object> buildControllerFactory() {
        return type -> {
            if (controllersMap.containsKey(type)) {
                return controllersMap.get(type).get();
            }
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear controlador: " + type, e);
            }
        };
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

        loader.setControllerFactory(buildControllerFactory());

        try {
            Stage stage = new Stage();
            stage.setScene(buildStyledScene(loader.load()));
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

        loader.setControllerFactory(buildControllerFactory());

        try {
            Stage stage = new Stage();
            stage.setScene(buildStyledScene(loader.load()));
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
