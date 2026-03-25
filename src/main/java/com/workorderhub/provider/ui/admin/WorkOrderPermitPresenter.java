package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderPermitOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;

public class WorkOrderPermitPresenter implements WorkOrderPermitOutput {

    private WorkOrderPermitView view;

    public WorkOrderPermitPresenter() {
    }

    public void setView(WorkOrderPermitView view) {
        this.view = view;
    }
}
