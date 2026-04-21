package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.adminpanel.AdminMainView;
import com.workorderhub.core.caseuse.adminpanel.AdminMainOutput;
import com.workorderhub.core.caseuse.adminpanel.WorkLogRow;
import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.provider.models.WorkFrontModel;
import com.workorderhub.provider.models.WorkLogModel;

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

        //TODO: Display placeholder when list is empty
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

        //TODO: Display placeholder when list is empty
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

        //TODO: Display placeholder when list is empty
    }
}
