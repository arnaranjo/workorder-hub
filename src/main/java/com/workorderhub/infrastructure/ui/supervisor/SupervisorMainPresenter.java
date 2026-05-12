package com.workorderhub.infrastructure.ui.supervisor;

import com.workorderhub.core.caseuse.supervisor.SupervisorMainView;
import com.workorderhub.core.caseuse.supervisor.SupervisorMainOutput;
import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.infrastructure.models.WorkFrontModel;

import java.util.List;

public class SupervisorMainPresenter implements SupervisorMainOutput {

    private SupervisorMainView viewController;

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
