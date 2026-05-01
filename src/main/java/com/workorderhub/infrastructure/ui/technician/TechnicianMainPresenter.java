package com.workorderhub.infrastructure.ui.technician;

import com.workorderhub.core.caseuse.technician.*;
import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.common.ViewLoader;
import com.workorderhub.infrastructure.database.*;
import com.workorderhub.infrastructure.models.WorkFrontModel;

import java.net.URL;
import java.util.List;

public class TechnicianMainPresenter implements TechnicianMainOutput {

    private TechnicianMainView viewController;
    private final URL workOrderView = TechnicianMainPresenter.class.getResource("/ui/technician/workorder-check-view.fxml");

    public TechnicianMainPresenter() {}

    public void setViewController(TechnicianMainView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void displayWorkFrontList(List<WorkFrontRow> workOrderList) {
        List<WorkFrontModel> modelList = workOrderList.stream()
                .map(workFrontRow -> new WorkFrontModel(
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
                )).toList();
        viewController.setWorkFrontList(modelList);
    }

    @Override
    public void displayError(TechnicianEnum technicianEnum) {
        switch (technicianEnum) {
            case WORK_ORDER_ID_ERROR:
                String idErrorTitle = "technicianView.errorTitle";
                String idErrorMessage = "technicianView.idError";

                Util.ShowMessage(idErrorTitle, idErrorMessage);
                break;

            case WORK_ORDER_NOT_FOUND:
                String titleNotFound = "technicianView.errorTitle";
                String messageNotFound = "technicianView.worOrderNoFound";

                Util.ShowMessage(titleNotFound, messageNotFound);
                break;

            case WORK_ORDER_START_ERROR:

                String titleStartError = "technicianView.errorTitle";
                String messageStartError = "technicianView.openErrorMessage";

                Util.ShowMessage(titleStartError, messageStartError);
                break;

            case WORK_ORDER_CLOSE_ERROR:
                String titleCloseError = "technicianView.errorTitle";
                String messageCloseError = "technicianView.closeErrorMessage";

                Util.ShowMessage(titleCloseError, messageCloseError);
                break;
        }
    }

    @Override
    public void displaySuccess(TechnicianEnum technicianEnum) {
        //TODO: Implement success messages
    }

    @Override
    public boolean requestConfirmation(TechnicianEnum technicianEnum) {
        switch (technicianEnum) {
            case REQUEST_CONFIRMATION_START_WORK_ORDER:
                String requestStartTitle = "technicianView.startTitle";
                String requestStartMessage = "technicianView.startMessage";

                return Util.RequestConfirmation(requestStartTitle, requestStartMessage);

            case REQUEST_CONFIRMATION_CLOSE_WORK_ORDER:
                String requestCloseTitle = "technicianView.closeTitle";
                String requestCloseMessage = "technicianView.closeMessage";

                return Util.RequestConfirmation(requestCloseTitle, requestCloseMessage);
        }
        return false;
    }

    @Override
    public void loadWorkOrderCheckView() {
        WorkOrderCheckPresenter presenter = new WorkOrderCheckPresenter();
        WorkOrderCheckInteractor interactor = new WorkOrderCheckInteractor(
                presenter,
                new DBWorkOrder(),
                new DBParticipant(),
                new DBUsedSparePart(),
                new DBAssignedCategory(),
                new DBSparePart()
        );
        ViewLoader viewLoader = new ViewLoader();

        viewLoader.registerController(WorkOrderCheckController.class, () -> {
            WorkOrderCheckController controller = new WorkOrderCheckController(interactor);
            presenter.setViewController(controller);
            return controller;
        });

        viewLoader.LoadView(
                workOrderView,
                PropertiesLoader.GetText("technicianView.workOrderReviewTitle")
        );
    }
}
