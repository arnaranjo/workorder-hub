package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderDataOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;

public class WorkOrderDataPresenter implements WorkOrderDataOutput {

    private WorkOrderDataView view;

    public WorkOrderDataPresenter() {
    }

    public void setView(WorkOrderDataView view) {
        this.view = view;
    }
}
