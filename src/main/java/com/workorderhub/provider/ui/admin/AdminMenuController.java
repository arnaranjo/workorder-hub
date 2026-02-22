package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.edituser.EditUserInteractor;
import com.workorderhub.core.caseuse.newuser.NewUserInteractor;
import com.workorderhub.core.caseuse.plantelement.PlantElementInteractor;
import com.workorderhub.core.caseuse.procedures.LotoProcedureInteractor;
import com.workorderhub.core.caseuse.procedures.WorkProcedureInteractor;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.ViewLoader;
import com.workorderhub.provider.database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;

public class AdminMenuController {

    private URL newUserView = getClass().getResource("/ui/admin/new-user-view.fxml");
    private URL editUserView = getClass().getResource("/ui/admin/edit-user-view.fxml");
    private URL proceduresView = getClass().getResource("/ui/admin/procedures-view.fxml");
    private URL plantElementView = getClass().getResource("/ui/admin/plant-element-view.fxml");

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

    @FXML
    private void LoadEditUserView() {
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

    @FXML
    private void LoadProceduresView() {
        ProceduresPresenter presenter = new ProceduresPresenter();

        WorkProcedureInteractor workProcedureInteractor = new WorkProcedureInteractor(
                presenter,
                new DBWorkProcedure()
        );
        LotoProcedureInteractor lotoProcedureInteractor = new LotoProcedureInteractor(
                presenter,
                new DBLotoProcedure()
        );

        ViewLoader viewLoader = new ViewLoader();
        viewLoader.registerController(ProceduresController.class, ()-> {
            ProceduresController controller = new ProceduresController(
                    workProcedureInteractor,
                    lotoProcedureInteractor
            );

            presenter.setView(controller);
            return controller;
        });

        viewLoader.LoadView(
                proceduresView,
                PropertiesLoader.GetText("adminMenu.proceduresTittle")
        );
    }

    @FXML
    private void LoadPlantElementView() {
        PlantElementPresenter presenter = new PlantElementPresenter();

        PlantElementInteractor interactor = new PlantElementInteractor(
                new DBPlantElement(),
                presenter
        );

        ViewLoader viewLoader = new ViewLoader();
        viewLoader.registerController(PlantElementController.class, ()-> {

            PlantElementController controller = new PlantElementController(
                    interactor
            );

            presenter.setView(controller);
            return controller;
        });

        viewLoader.LoadView(
                plantElementView,
                PropertiesLoader.GetText("adminMenu.plantElementTittle"),
                PropertiesLoader.GetDouble("adminMenu.plantElementWidth"),
                PropertiesLoader.GetDouble("adminMenu.plantElementHeight")
        );
    }
}
