package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.edituser.EditUserInteractor;
import com.workorderhub.core.caseuse.newuser.NewUserInteractor;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.ViewLoader;
import com.workorderhub.provider.database.DBCredentials;
import com.workorderhub.provider.database.DBUser;
import com.workorderhub.provider.database.DBUserRole;
import javafx.fxml.FXML;

import java.net.URL;

public class AdminMenuController {

    private URL newUserView = getClass().getResource("/ui/admin/new-user-view.fxml");
    private URL editUserView = getClass().getResource("/ui/admin/edit-user-view.fxml");

    @FXML
    private void LoadNewUserView(){
        NewUserPresenter presenter = new NewUserPresenter();
        NewUserInteractor interactor = new NewUserInteractor(
                presenter,
                new DBUser(),
                new DBCredentials(),
                new DBUserRole()
        );

        ViewLoader viewLoader = new ViewLoader();

        viewLoader.registerController(NewUserController.class, ()-> {
            NewUserController controller = new NewUserController(interactor);
            presenter.setView(controller);
            return controller;
        });

        viewLoader.LoadView(
                newUserView,
                PropertiesLoader.GetText("adminMenu.newUserTittle")
        );

    }

    public void LoadEditUserView() {
        EditUserPresenter presenter = new EditUserPresenter();
        EditUserInteractor interactor = new EditUserInteractor(
                presenter,
                new DBUser(),
                new DBCredentials(),
                new DBUserRole()
        );

        ViewLoader viewLoader = new ViewLoader();

        viewLoader.registerController(EditUserController.class, ()-> {
            EditUserController controller = new EditUserController(interactor);
            presenter.setView(controller);
            return controller;
        });

        viewLoader.LoadView(
                editUserView,
                PropertiesLoader.GetText("adminMenu.editUserTittle")
        );
    }
}
