package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodView;
import com.workorderhub.provider.models.WorkFrontModel;

import java.util.List;

public class WorkOrderPeriodPresenter implements WorkOrderPeriodOutput {

    private WorkOrderPeriodView viewController;

    public WorkOrderPeriodPresenter() {
    }

    public void setViewController(WorkOrderPeriodView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void displayWorkFrontList(List<WorkFrontRow> workFrontRowList) {
        List<WorkFrontModel> modelList = workFrontRowList.stream().map(workFrontRow -> new WorkFrontModel(
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
}
