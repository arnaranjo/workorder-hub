package com.workorderhub.infrastructure.ui.login;

import com.workorderhub.core.caseuse.adminpanel.AdminMainInteractor;
import com.workorderhub.core.caseuse.login.LoginOutput;
import com.workorderhub.core.caseuse.login.ResponseLogin;
import com.workorderhub.core.caseuse.login.LoginView;
import com.workorderhub.core.caseuse.supervisor.SupervisorMainInteractor;
import com.workorderhub.core.caseuse.technician.TechnicianMainInteractor;
import com.workorderhub.core.entity.UserRoleEnum;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.ViewLoader;
import com.workorderhub.infrastructure.database.DBAssignedCategory;
import com.workorderhub.infrastructure.database.DBParticipant;
import com.workorderhub.infrastructure.database.DBUsedSparePart;
import com.workorderhub.infrastructure.database.DBWorkLog;
import com.workorderhub.infrastructure.database.DBWorkOrder;
import com.workorderhub.infrastructure.ui.admin.AdminMainController;
import com.workorderhub.infrastructure.ui.admin.AdminMainPresenter;
import com.workorderhub.infrastructure.ui.admin.AdminMenuController;
import com.workorderhub.infrastructure.ui.supervisor.SupervisorMainController;
import com.workorderhub.infrastructure.ui.supervisor.SupervisorMainPresenter;
import com.workorderhub.infrastructure.ui.technician.TechnicianMainController;
import com.workorderhub.infrastructure.ui.technician.TechnicianMainPresenter;

import java.net.URL;

public class LoginPresenter implements LoginOutput {

    private LoginView loginViewController;
    private final URL adminView = getClass().getResource("/ui/admin/admin-view.fxml");
    private final URL supervisorView = getClass().getResource("/ui/supervisor/supervisor-main-view.fxml");
    private final URL technicianView = getClass().getResource("/ui/technician/technician-main-view.fxml");

    public LoginPresenter() {
    }

    public void setLoginViewController(LoginView loginViewController) {
        this.loginViewController = loginViewController;
    }

    @Override
    public void displayUserNoFound() {
        loginViewController.setTopDisplay(
                PropertiesLoader.GetText("login.userNoFound"),
                PropertiesLoader.GetText("login.failStyle")
        );
    }

    @Override
    public void loadView(ResponseLogin response, UserRoleEnum userRole) {

        ViewLoader viewLoader = new ViewLoader();
        AppState appState = AppState.getInstance();
        appState.setLoggedUser(response.userName(), response.userId());

        switch (userRole) {
            case MANAGER:

                AdminMainPresenter managerPresenter = new AdminMainPresenter();
                AdminMainInteractor managerInteractor = new AdminMainInteractor(
                        managerPresenter,
                        new DBWorkOrder(),
                        new DBWorkLog(),
                        new DBAssignedCategory(),
                        new DBParticipant(),
                        new DBUsedSparePart()
                );

                viewLoader.registerController(AdminMainController.class, ()-> {
                    AdminMainController controller = new AdminMainController(managerInteractor);
                    managerPresenter.setViewController(controller);
                    return controller;
                });
                viewLoader.registerController(AdminMenuController.class, ()-> new AdminMenuController(managerInteractor));
                viewLoader.LoadView(
                        adminView,
                        PropertiesLoader.GetText("login.managerScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;

            case SUPERVISOR:

                SupervisorMainPresenter supervisorPresenter = new SupervisorMainPresenter();
                SupervisorMainInteractor supervisorInteractor = new SupervisorMainInteractor(
                        supervisorPresenter,
                        new DBWorkOrder()
                );

                viewLoader.registerController(SupervisorMainController.class, ()-> {
                    SupervisorMainController controller = new SupervisorMainController(supervisorInteractor);
                    supervisorPresenter.setViewController(controller);
                    return controller;
                });

                viewLoader.LoadView(
                        supervisorView,
                        PropertiesLoader.GetText("login.supervisorScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;

            case TECHNICIAN:

                TechnicianMainPresenter technicianMainPresenter = new TechnicianMainPresenter();
                TechnicianMainInteractor technicianMainInteractor = new TechnicianMainInteractor(
                        technicianMainPresenter,
                        new DBWorkOrder()
                );

                viewLoader.registerController(TechnicianMainController.class, ()-> {
                    TechnicianMainController controller = new TechnicianMainController(technicianMainInteractor);
                    technicianMainPresenter.setViewController(controller);
                    return controller;
                });

                viewLoader.LoadView(
                        technicianView,
                        PropertiesLoader.GetText("login.technicianScreen"),
                        PropertiesLoader.GetDouble("mainScreen.width"),
                        PropertiesLoader.GetDouble("mainScreen.height")
                );
                break;
        }

        loginViewController.closedScreen();
    }
}
