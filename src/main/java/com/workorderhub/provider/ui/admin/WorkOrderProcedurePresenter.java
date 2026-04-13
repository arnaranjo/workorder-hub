package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderProcedureOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderProcedureView;
import com.workorderhub.core.caseuse.workorder.WorkProcedureRow;
import com.workorderhub.provider.models.WorkProcedureModel;

import java.util.List;

public class WorkOrderProcedurePresenter implements WorkOrderProcedureOutput {

    private WorkOrderProcedureView viewController;

    public WorkOrderProcedurePresenter() {
    }

    public void setView(WorkOrderProcedureView view) {
        this.viewController = view;
    }

    @Override
    public void retrieveWorkProcedureList(List<WorkProcedureRow> workProcedureList) {
        List<WorkProcedureModel> modelList = workProcedureList.stream()
                .map(workProcedureRow -> new WorkProcedureModel(
                        workProcedureRow.procedureId(),
                        workProcedureRow.documentCode(),
                        workProcedureRow.documentName()
        )).toList();
        viewController.setWorkProcedureList(modelList);
    }
}
