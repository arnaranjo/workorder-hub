package com.workorderhub.provider.ui.admin;

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
}
