package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderProcedureOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderProcedureView;

public class WorkOrderProcedurePresenter implements WorkOrderProcedureOutput {

    private WorkOrderProcedureView view;

    public WorkOrderProcedurePresenter() {
    }

    public void setView(WorkOrderProcedureView view) {
        this.view = view;
    }
}
