package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.adminpanel.AdminMainInput;
import com.workorderhub.core.caseuse.adminpanel.AdminMainInteractor;
import com.workorderhub.core.caseuse.adminpanel.RequestDeleteWorkOrder;
import com.workorderhub.core.caseuse.edituser.EditUserInteractor;
import com.workorderhub.core.caseuse.newuser.NewUserInteractor;
import com.workorderhub.core.caseuse.plantelement.PlantElementInteractor;
import com.workorderhub.core.caseuse.procedures.LotoProcedureInteractor;
import com.workorderhub.core.caseuse.procedures.WorkProcedureInteractor;
import com.workorderhub.core.caseuse.spareparts.SparePartInteractor;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataListener;
import com.workorderhub.core.caseuse.workorder.WorkOrderInteractor;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.common.ViewLoader;
import com.workorderhub.infrastructure.database.*;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import javafx.fxml.FXML;

import java.net.URL;

public class AdminMenuController {

    private final URL newUserView = getClass().getResource("/ui/admin/new-user-view.fxml");
    private final URL editUserView = getClass().getResource("/ui/admin/edit-user-view.fxml");
    private final URL proceduresView = getClass().getResource("/ui/admin/procedures-view.fxml");
    private final URL plantElementView = getClass().getResource("/ui/admin/plant-element-view.fxml");
    private final URL sparePartsView = getClass().getResource("/ui/admin/spare-parts-view.fxml");
    private final URL workOrderView = getClass().getResource("/ui/admin/workorder-main-view.fxml");

    private AdminMainController adminMainController;
    private AdminMainInput interactor;

    public AdminMenuController(
            AdminMainInput interactor
    ){
        this.interactor = interactor;
    }

    /**
     * Sets the controller of the main window.
     * @param adminMainController main controller.
     */
    public void setAdminController(AdminMainController adminMainController){
        this.adminMainController = adminMainController;
    }

    // Event methods

    @FXML
    private void loadNewUserView() {
        NewUserPresenter presenter = new NewUserPresenter();
        NewUserInteractor interactor = new NewUserInteractor(
                presenter,
                new DBUser(),
                new DBCredentials(),
                new DBUserRole()
        );

        ViewLoader viewLoader = new ViewLoader();

        viewLoader.registerController(NewUserController.class, () -> {
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
    private void loadEditUserView() {
        EditUserPresenter presenter = new EditUserPresenter();
        EditUserInteractor interactor = new EditUserInteractor(
                presenter,
                new DBUser(),
                new DBCredentials(),
                new DBUserRole()
        );

        ViewLoader viewLoader = new ViewLoader();

        viewLoader.registerController(EditUserController.class, () -> {
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
    private void loadProceduresView() {
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
        viewLoader.registerController(ProceduresController.class, () -> {
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
    private void loadPlantElementView() {
        PlantElementPresenter presenter = new PlantElementPresenter();
        PlantElementInteractor interactor = new PlantElementInteractor(
                new DBPlantElement(),
                presenter
        );

        ViewLoader viewLoader = new ViewLoader();
        viewLoader.registerController(PlantElementController.class, () -> {
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

    public void loadSparePartsView() {
        SparePartPresenter presenter = new SparePartPresenter();

        SparePartInteractor interactor = new SparePartInteractor(
                presenter,
                new DBSparePart(),
                new DBSpareCategory()
        );

        ViewLoader viewLoader = new ViewLoader();
        viewLoader.registerController(SparePartController.class, () -> {
            SparePartController controller = new SparePartController(
                    interactor
            );

            presenter.setView(controller);
            return controller;
        });

        viewLoader.LoadView(
                sparePartsView,
                PropertiesLoader.GetText("adminMenu.sparePartsTittle"),
                PropertiesLoader.GetDouble("adminMenu.sparePartsWidth"),
                PropertiesLoader.GetDouble("adminMenu.sparePartsHeight")
        );
    }

    @FXML
    private void LoadNewWorkOrderView() {

        // Reset the AppState to ensure there is no data from previous work order creation processes.
        AppState.getInstance().resetWorkOrder();
        // Set the admin mode to true in the AppState to ensure that the work order form loads with the correct functionalities.
        AppState.getInstance().setAdminMode(true);

        WorkOrderMainPresenter mainPresenter = new WorkOrderMainPresenter();
        WorkOrderDataPresenter dataPresenter = new WorkOrderDataPresenter();
        dataPresenter.setDataListener(new WorkOrderDataListener() {
            @Override
            public void onNewWorkOrder() {
                AppState.getInstance().resetWorkOrder();
                adminMainController.retrieveWorkFront();

            }

            @Override
            public void onEditWorkOrder() {
                return;
            }
        });

        WorkOrderPeriodPresenter periodPresenter = new WorkOrderPeriodPresenter();
        WorkOrderProcedurePresenter procedurePresenter = new WorkOrderProcedurePresenter();
        WorkOrderPermitPresenter permitPresenter = new WorkOrderPermitPresenter();

        WorkOrderInteractor interactor = new WorkOrderInteractor.Builder()
                .withMainOutput(mainPresenter)
                .withDataOutput(dataPresenter)
                .withPeriodOutput(periodPresenter)
                .withProcedureOutput(procedurePresenter)
                .withPermitOutput(permitPresenter)
                .withWorkOrderGateway(new DBWorkOrder())
                .withCategoryGateway(new DBCategory())
                .withUserGateway(new DBUser())
                .withPlantElementGateway(new DBPlantElement())
                .withSparePartGateway(new DBSparePart())
                .withWorkProcedureGateway(new DBWorkProcedure())
                .withLotoProcedureGateway(new DBLotoProcedure())
                .withWorkPermitGateway(new DBWorkPermit())
                .withStatusGateway(new DBStatus())
                .withAssignedCategoryGateway(new DBAssignedCategory())
                .withParticipantGateway(new DBParticipant())
                .withUsedSparePartGateway(new DBUsedSparePart())
                .build();

        ViewLoader viewLoader = new ViewLoader();

        viewLoader.registerController(WorkOrderMainController.class, () -> {
            WorkOrderMainController mainController = new WorkOrderMainController(interactor);
            mainPresenter.setViewController(mainController);
            return mainController;
        });

        viewLoader.registerController(WorkOrderDataController.class, () -> {
            WorkOrderDataController dataController = new WorkOrderDataController(interactor);
            dataPresenter.setViewController(dataController);
            return dataController;
        });

        viewLoader.registerController(WorkOrderPeriodController.class, () -> {
            WorkOrderPeriodController periodController = new WorkOrderPeriodController(interactor);
            periodPresenter.setViewController(periodController);
            return periodController;
        });

        viewLoader.registerController(WorkOrderProcedureController.class, () -> {
            WorkOrderProcedureController procedureController = new WorkOrderProcedureController(interactor);
            procedurePresenter.setView(procedureController);
            return procedureController;
        });

        viewLoader.registerController(WorkOrderPermitController.class, () -> {
            WorkOrderPermitController permitController = new WorkOrderPermitController(interactor);
            permitPresenter.setViewController(permitController);
            return permitController;
        });

        viewLoader.LoadView(
                workOrderView,
                PropertiesLoader.GetText("adminMenu.newWorkOrderTitle")
        );
    }

    @FXML
    private void loadEditWorkOrderView() {

        WorkFrontModel selectedWorkOrder = adminMainController.getSelectedWorkOrder();
        if (selectedWorkOrder == null) {
            String titleError = "adminMenu.noSelectedTitle";
            String messageError = "adminMenu.noSelectedMessage";
            Util.showMessage(titleError, messageError);

        }
        else {

            // Set the selected work order ID in the AppState to ensure that the edit view loads the correct work order data.
            AppState.getInstance().setWorkOrderId(selectedWorkOrder.getWorkOrderId());
            // Set the admin mode to true in the AppState to ensure that the work order form loads with the correct functionalities.
            AppState.getInstance().setAdminMode(true);

            WorkOrderMainPresenter mainPresenter = new WorkOrderMainPresenter();
            WorkOrderDataPresenter dataPresenter = new WorkOrderDataPresenter();
            dataPresenter.setDataListener(new WorkOrderDataListener() {
                @Override
                public void onNewWorkOrder() {
                    return;
                }

                @Override
                public void onEditWorkOrder() {
                    adminMainController.retrieveWorkFront();
                }
            });
            WorkOrderPeriodPresenter periodPresenter = new WorkOrderPeriodPresenter();
            WorkOrderProcedurePresenter procedurePresenter = new WorkOrderProcedurePresenter();
            WorkOrderPermitPresenter permitPresenter = new WorkOrderPermitPresenter();

            WorkOrderInteractor interactor = new WorkOrderInteractor.Builder()
                    .withMainOutput(mainPresenter)
                    .withDataOutput(dataPresenter)
                    .withPeriodOutput(periodPresenter)
                    .withProcedureOutput(procedurePresenter)
                    .withPermitOutput(permitPresenter)
                    .withWorkOrderGateway(new DBWorkOrder())
                    .withCategoryGateway(new DBCategory())
                    .withUserGateway(new DBUser())
                    .withPlantElementGateway(new DBPlantElement())
                    .withSparePartGateway(new DBSparePart())
                    .withWorkProcedureGateway(new DBWorkProcedure())
                    .withLotoProcedureGateway(new DBLotoProcedure())
                    .withWorkPermitGateway(new DBWorkPermit())
                    .withStatusGateway(new DBStatus())
                    .withAssignedCategoryGateway(new DBAssignedCategory())
                    .withParticipantGateway(new DBParticipant())
                    .withUsedSparePartGateway(new DBUsedSparePart())
                    .build();

            ViewLoader viewLoader = new ViewLoader();

            viewLoader.registerController(WorkOrderMainController.class, () -> {
                WorkOrderMainController mainController = new WorkOrderMainController(interactor);
                mainPresenter.setViewController(mainController);
                return mainController;
            });

            viewLoader.registerController(WorkOrderDataController.class, () -> {
                WorkOrderDataController dataController = new WorkOrderDataController(interactor);
                dataPresenter.setViewController(dataController);
                return dataController;
            });

            viewLoader.registerController(WorkOrderPeriodController.class, () -> {
                WorkOrderPeriodController periodController = new WorkOrderPeriodController(interactor);
                periodPresenter.setViewController(periodController);
                return periodController;
            });

            viewLoader.registerController(WorkOrderProcedureController.class, () -> {
                WorkOrderProcedureController procedureController = new WorkOrderProcedureController(interactor);
                procedurePresenter.setView(procedureController);
                return procedureController;
            });

            viewLoader.registerController(WorkOrderPermitController.class, () -> {
                WorkOrderPermitController permitController = new WorkOrderPermitController(interactor);
                permitPresenter.setViewController(permitController);
                return permitController;
            });

            viewLoader.LoadView(
                    workOrderView,
                    PropertiesLoader.GetText("adminMenu.editWorkOrderTitle")
            );

        }
    }

    @FXML
    private void deleteWorkOrder() {
        if (adminMainController.getSelectedWorkOrder() == null) {

            String titleError = "adminMenu.noSelectedTitle";
            String messageError = "adminMenu.noSelectedMessage";
            Util.showMessage(titleError, messageError);

        } else {
            RequestDeleteWorkOrder requestDeleteWorkOrder = new RequestDeleteWorkOrder(
                    adminMainController.getSelectedWorkOrder().getWorkOrderId()
            );

            interactor.deleteWorkOrder(requestDeleteWorkOrder);
        }
    }
}
