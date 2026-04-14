package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.LotoProcedureRow;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;
import com.workorderhub.provider.models.LotoProcedureModel;

import java.util.List;

public class WorkOrderPermitPresenter implements WorkOrderPermitOutput {

    private WorkOrderPermitView viewController;

    public WorkOrderPermitPresenter() {
    }

    public void setViewController(WorkOrderPermitView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void displayLotoProcedureList(List<LotoProcedureRow> lotoProcedureList) {
        List<LotoProcedureModel> modelList = lotoProcedureList.stream()
                .map(lotoProcedureRow -> new LotoProcedureModel(
                        lotoProcedureRow.procedureId(),
                        lotoProcedureRow.documentCode(),
                        lotoProcedureRow.documentName()
                )).toList();
        this.viewController.setLotoProcedureList(modelList);
    }
}
