package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginOutput;
import com.workorderhub.core.caseuse.login.LoginResponse;
import com.workorderhub.core.entity.UserRoleEnum;
import com.workorderhub.provider.common.AppState;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.ViewLoader;
import com.workorderhub.provider.ui.admin.AdminMainController;
import com.workorderhub.provider.ui.admin.AdminMenuController;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class LoginPresenter implements LoginOutput {

    private LoginView view;
    private final URL adminView = getClass().getResource("/ui/admin/admin-view.fxml");
    private final URL technicianView = getClass().getResource("/ui/supervisor/supervisor-view.fxml");
    private final URL supervisorView = getClass().getResource("/ui/technician/technician-view.fxml");

    public LoginPresenter() {
    }

    public void setView(LoginView view) {
        this.view = view;
    }

    @Override
    public void displayUserNoFound() {
        view.setTopDisplay(
                PropertiesLoader.GetText("login.userNoFound"),
                PropertiesLoader.GetText("login.failStyle")
        );
    }

    @Override
    public void loadView(LoginResponse response, UserRoleEnum userRole) {

        FXMLLoader fxmlLoader;
        AppState appState = AppState.getInstance();
        appState.setLoggedUser(response.getUserName());

        switch (userRole) {
            case MANAGER:
                fxmlLoader = new FXMLLoader(adminView);

                fxmlLoader.setControllerFactory(type -> {
                    if (type == AdminMainController.class) {
                        return new AdminMainController();
                    } else if (type == AdminMenuController.class) {
                        return new AdminMenuController();
                    }

                    try {
                        return type.getConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot instantiate controller: " + type, e);
                    }
                });

                ViewLoader.LoadView(
                        fxmlLoader,
                        PropertiesLoader.GetText("login.managerScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;

            case SUPERVISOR:
                fxmlLoader = new FXMLLoader(supervisorView);
                ViewLoader.LoadView(
                        fxmlLoader,
                        PropertiesLoader.GetText("login.supervisorScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;

            case TECHNICIAN:
                fxmlLoader = new FXMLLoader(technicianView);
                ViewLoader.LoadView(
                        fxmlLoader,
                        PropertiesLoader.GetText("login.technicianScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;
        }

        view.closedScreen();
    }
}
