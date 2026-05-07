package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.adminpanel.AdminMainEnum;
import com.workorderhub.core.caseuse.adminpanel.AdminMainView;
import com.workorderhub.core.caseuse.adminpanel.AdminMainOutput;
import com.workorderhub.core.caseuse.adminpanel.WorkLogRow;
import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import com.workorderhub.infrastructure.models.WorkLogModel;

import java.util.List;

public class AdminMainPresenter implements AdminMainOutput {

    private AdminMainView viewController;


    public AdminMainPresenter() {
    }

    public void setViewController(AdminMainView viewController) {
        this.viewController = viewController;
    }


    @Override
    public void displayWorkFrontList(List<WorkFrontRow> workFrontRowList) {
        List<WorkFrontModel> modelList = workFrontRowList.stream()
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
    public void displayClosedOrdersList(List<WorkFrontRow> closedOrdersList) {
        List<WorkFrontModel> modelList = closedOrdersList.stream()
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
        viewController.setClosedOrdersList(modelList);
    }

    @Override
    public void displayWorkLogList(List<WorkLogRow> workLogRowList) {
        List<WorkLogModel> modelList = workLogRowList.stream()
                .map(workLogRow -> new WorkLogModel(
                        workLogRow.logId(),
                        workLogRow.logDate(),
                        workLogRow.logComment(),
                        workLogRow.workOrderId(),
                        workLogRow.userName(),
                        workLogRow.workOrderStartDate(),
                        workLogRow.workOrderEndDate(),
                        workLogRow.holderName(),
                        workLogRow.workPermitId()
                )).toList();
        viewController.setWorkLogList(modelList);
    }

    @Override
    public void displayError(AdminMainEnum adminMainEnum) {
        switch (adminMainEnum){
            case DELETE_WORK_ORDER_ERROR:
                String errorTitle = "adminView.deleteError.title";
                String errorMessage = "adminView.deleteError.message";

                Util.showMessage(errorTitle, errorMessage);
                break;

        case DELETE_WORK_ORDER_ID_ERROR:
                String idErrorTitle = "adminView.idError.title";
                String idErrorMessage = "adminView.idError.message";

                Util.showMessage(idErrorTitle, idErrorMessage);
                break;
        }
    }

    @Override
    public boolean requestConfirmation(AdminMainEnum adminMainEnum) {
        if (adminMainEnum == AdminMainEnum.CONFIRM_DELETE_WORK_ORDER) {
            String confirmTitle = "adminView.deleteRequest.title";
            String confirmMessage = "adminView.deleteRequest.message";

            return Util.requestConfirmation(confirmTitle, confirmMessage);
        }
        return false;
    }

    @Override
    public void displaySuccess(AdminMainEnum adminMainEnum) {
        if (adminMainEnum == AdminMainEnum.DELETE_WORK_ORDER_SUCCESS) {
            String successTitle = "adminView.deleteConfirmation.title";
            String successMessage = "adminView.deleteConfirmation.message";

            Util.showMessage(successTitle, successMessage);
        }
    }
}
