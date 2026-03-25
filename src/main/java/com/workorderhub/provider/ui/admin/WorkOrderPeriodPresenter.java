package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodView;

public class WorkOrderPeriodPresenter implements WorkOrderPeriodOutput {

    private WorkOrderPeriodView view;

    public WorkOrderPeriodPresenter() {
    }

    public void setView(WorkOrderPeriodView view) {
        this.view = view;
    }
}
