package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginOutput;
import com.workorderhub.core.caseuse.login.LoginResponse;
import com.workorderhub.core.entity.UserRoleEnum;
import com.workorderhub.provider.common.AppState;
import com.workorderhub.provider.common.Util;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class LoginPresenter implements LoginOutput {

    private final LoginView view;
    private final URL adminView = getClass().getResource("/ui/admin/admin-view.fxml");
    private final URL technicianView = getClass().getResource("/ui/supervisor/supervisor-view.fxml");
    private final URL supervisorView = getClass().getResource("/ui/technician/technician-view.fxml");


    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    @Override
    public void displayUserNoFound() {
        view.setTopDisplay(
                Util.GetText("login.userNoFound"),
                Util.GetText("login.failStyle")
        );
    }

    @Override
    public void loadView(LoginResponse response, UserRoleEnum userRole) {

        FXMLLoader fxmlLoader;
        AppState appState = AppState.GetInstance();
        appState.setLoggedUser(response.getUserName());

        switch (userRole) {
            case MANAGER:
                fxmlLoader = new FXMLLoader(adminView);
                Util.LoadView(
                        fxmlLoader,
                        Util.GetText("login.managerScreen"),
                        Util.GetDouble("mainScreen.width"),
                        Util.GetDouble("mainScreen.height")
                );
                break;
            case SUPERVISOR:
                fxmlLoader = new FXMLLoader(supervisorView);
                Util.LoadView(
                        fxmlLoader,
                        Util.GetText("login.supervisorScreen"),
                        Util.GetDouble("mainScreen.width"),
                        Util.GetDouble("mainScreen.height")
                );
                break;
            case TECHNICIAN:
                fxmlLoader = new FXMLLoader(technicianView);
                Util.LoadView(
                        fxmlLoader,
                        Util.GetText("login.technicianScreen"),
                        Util.GetDouble("mainScreen.width"),
                        Util.GetDouble("mainScreen.height")
                );
                break;
        }

        view.closedScreen();
    }
}
