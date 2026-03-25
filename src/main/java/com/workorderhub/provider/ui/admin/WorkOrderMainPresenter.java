package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderMainOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderMainView;

public class WorkOrderMainPresenter implements WorkOrderMainOutput {

    private WorkOrderMainView view;

    public WorkOrderMainPresenter() {
    }

    public void setView(WorkOrderMainView view) {
        this.view = view;
    }
}
