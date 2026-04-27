package com.workorderhub.infrastructure.ui.technician;

import com.workorderhub.core.caseuse.technician.TechnicianMainOutput;
import com.workorderhub.core.caseuse.technician.TechnicianMainView;
import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.infrastructure.models.WorkFrontModel;

import java.util.List;

public class TechnicianMainPresenter implements TechnicianMainOutput {

    private TechnicianMainView viewController;

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
}
