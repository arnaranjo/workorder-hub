package com.workorderhub.main;

import com.workorderhub.core.caseuse.login.LoginInteractor;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.ViewLoader;
import com.workorderhub.provider.database.DBCredentials;
import com.workorderhub.provider.database.DBUser;
import com.workorderhub.provider.ui.login.LoginController;
import com.workorderhub.provider.ui.login.LoginPresenter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    private final URL logInView = getClass().getResource("/ui/login/login-view.fxml");

    @Override
    public void start(Stage stage){

        LoginPresenter presenter = new LoginPresenter();
        LoginInteractor interactor = new LoginInteractor(
                new DBUser(),
                new DBCredentials(),
                presenter
        );

        ViewLoader viewLoader = new ViewLoader();
        viewLoader.registerController(LoginController.class, () -> {
            LoginController controller = new LoginController(interactor);
            presenter.setView(controller);
            return controller;
        });

        viewLoader.LoadView(
                logInView,
                PropertiesLoader.GetText("login.title")
        );
    }

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
