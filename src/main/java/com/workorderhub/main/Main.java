package com.workorderhub.main;

import com.workorderhub.core.caseuse.login.LoginInteractor;
import com.workorderhub.provider.database.DBCredentials;
import com.workorderhub.provider.database.DBUser;
import com.workorderhub.provider.ui.login.LoginController;
import com.workorderhub.provider.ui.login.LoginPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/ui/login/login-view.fxml"));

        LoginController loginController = new LoginController();
        LoginInteractor interactor = new LoginInteractor(
                new DBUser(),
                new DBCredentials(),
                new LoginPresenter(loginController)
        );

        loginController.setLoginInteractor(interactor);
        fxmlLoader.setController(loginController);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
