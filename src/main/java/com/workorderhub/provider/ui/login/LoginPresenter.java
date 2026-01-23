package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginOutput;
import com.workorderhub.core.caseuse.login.LoginResponse;
import com.workorderhub.provider.common.Util;

import java.net.URL;

public class LoginPresenter implements LoginOutput {

    private LoginView view;
    private final URL adminView = getClass().getResource("/ui/admin/admin-view.fxml");
    private final URL technicianView = getClass().getResource("/ui/supervisor/supervisor-view.fxml");
    private final URL supervisorView = getClass().getResource("/ui/technician/technician-view.fxml");


    public LoginPresenter(LoginView view){
        this.view = view;
    }

    @Override
    public void DisplayUserNoFound() {
        view.setTopDisplay(
                Util.GetText("login.userNoFound"),
                Util.GetText("login.failStyle")
        );
    }

    @Override
    public void LoadView(LoginResponse response) {
        switch (response.getRolId()) {
            case 1:
                Util.LoadView(
                        adminView,
                        Util.GetText("login.managerScreen"),
                        Util.GetDouble("mainScreen.width"),
                        Util.GetDouble("mainScreen.height")
                );
                break;
            case 2:
                Util.LoadView(
                        supervisorView,
                        Util.GetText("login.supervisorScreen"),
                        Util.GetDouble("mainScreen.width"),
                        Util.GetDouble("mainScreen.height")
                );
                break;
            case 3:
                Util.LoadView(
                        technicianView,
                        Util.GetText("login.technicianScreen"),
                        Util.GetDouble("mainScreen.width"),
                        Util.GetDouble("mainScreen.height")
                );
                break;
        }
    }

    @Override
    public void ClosedCurrentScreen() {
    }
}
