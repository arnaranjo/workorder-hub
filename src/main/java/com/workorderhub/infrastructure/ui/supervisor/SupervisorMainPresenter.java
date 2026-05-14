package com.workorderhub.infrastructure.ui.supervisor;

import com.workorderhub.core.caseuse.supervisor.SupervisorMainView;
import com.workorderhub.core.caseuse.supervisor.SupervisorMainOutput;
import com.workorderhub.core.caseuse.supervisor.ResponseReviewWorkOrder;
import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataListener;
import com.workorderhub.core.caseuse.workorder.WorkOrderInteractor;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.ViewLoader;
import com.workorderhub.infrastructure.database.*;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import com.workorderhub.infrastructure.ui.admin.*;

import java.net.URL;
import java.util.List;

public class SupervisorMainPresenter implements SupervisorMainOutput {

    private SupervisorMainView viewController;
    private final URL workOrderView = SupervisorMainPresenter.class.getResource("/ui/admin/workorder-main-view.fxml");

    public SupervisorMainPresenter() {}

    public void setViewController(SupervisorMainView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void displayWorkFrontList(List<WorkFrontRow> workFrontRowList) {
        List<WorkFrontModel> modelList = workFrontRowList.stream()
                .map(this::mapWorkFrontModel)
                .toList();
        viewController.setWorkFrontList(modelList);
    }

    @Override
    public void displayClosedWorkList(List<WorkFrontRow> closedWorkList) {
        List<WorkFrontModel> modelList = closedWorkList.stream()
                .map(this::mapWorkFrontModel)
                .toList();
        viewController.setClosedWorkList(modelList);
    }

    @Override
    public void loadWorkOrderView(ResponseReviewWorkOrder response) {
        AppState.getInstance().setWorkOrderId(response.workOrderId());
        AppState.getInstance().setSupervisorMode(true);

        WorkOrderMainPresenter mainPresenter = new WorkOrderMainPresenter();
        WorkOrderDataPresenter dataPresenter = new WorkOrderDataPresenter();
        dataPresenter.setDataListener(new WorkOrderDataListener() {
            @Override
            public void onNewWorkOrder() {}

            @Override
            public void onEditWorkOrder() {}
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
                PropertiesLoader.GetText("supervisorView.workOrderReviewTitle")
        );
    }

    private WorkFrontModel mapWorkFrontModel(WorkFrontRow workFrontRow) {
        return new WorkFrontModel(
                workFrontRow.workOrderId(),
                workFrontRow.description(),
                workFrontRow.startDate(),
                workFrontRow.endDate(),
                workFrontRow.plantElementTag(),
                workFrontRow.workProcedureCode(),
                workFrontRow.lockDeviceId(),
                workFrontRow.lotoProcedureCode(),
                workFrontRow.holderName(),
                workFrontRow.status()
        );
    }
}
