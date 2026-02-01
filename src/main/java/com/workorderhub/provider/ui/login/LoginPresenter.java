package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginOutput;
import com.workorderhub.core.caseuse.login.ResponseLogin;
import com.workorderhub.core.caseuse.login.LoginView;
import com.workorderhub.core.entity.UserRoleEnum;
import com.workorderhub.provider.common.AppState;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.ViewLoader;
import com.workorderhub.provider.ui.admin.AdminMainController;
import com.workorderhub.provider.ui.admin.AdminMenuController;

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
    public void loadView(ResponseLogin response, UserRoleEnum userRole) {

        ViewLoader viewLoader = new ViewLoader();
        AppState appState = AppState.getInstance();
        appState.setLoggedUser(response.getUserName());

        switch (userRole) {
            case MANAGER:
                viewLoader.registerController(AdminMainController.class, ()-> new AdminMainController());
                viewLoader.registerController(AdminMenuController.class, ()-> new AdminMenuController());
                viewLoader.LoadView(
                        adminView,
                        PropertiesLoader.GetText("login.managerScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;

            case SUPERVISOR:
                //Log the controllers here

                viewLoader.LoadView(
                        supervisorView,
                        PropertiesLoader.GetText("login.supervisorScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;

            case TECHNICIAN:
                //Log the controllers here

                viewLoader.LoadView(
                        technicianView,
                        PropertiesLoader.GetText("login.technicianScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;
        }

        view.closedScreen();
    }
}
