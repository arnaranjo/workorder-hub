package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.workorder.LotoProcedureRow;
import com.workorderhub.core.caseuse.workorder.ResponseWOrkPermitInfo;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;
import com.workorderhub.infrastructure.models.LotoProcedureModel;

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

    @Override
    public void displayWorkPermitInfo(ResponseWOrkPermitInfo response) {
        if (response.lotoProcedureId()!= null) {
            LotoProcedureModel model = new LotoProcedureModel(
                    response.lotoProcedureId(),
                    response.lotoProcedureCode(),
                    response.lotoProcedureName()
            );
            this.viewController.setWorkPermitInfo(
                    response.permitDescription(),
                    response.lockDevices(),
                    model
            );
        } else{
            this.viewController.setWorkPermitInfo(
                    response.permitDescription(),
                    response.lockDevices(),
                    null
            );
        }
    }
}
