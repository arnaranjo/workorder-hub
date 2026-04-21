package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderMainOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderMainView;

public class WorkOrderMainPresenter implements WorkOrderMainOutput {

    private WorkOrderMainView viewController;

    public WorkOrderMainPresenter() {
    }

    public void setViewController(WorkOrderMainView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void toggleValidPeriodSelection() {
        if (viewController != null) {
            viewController.toggleValidPeriodContent();
        }
    }

    @Override
    public void toggleProcedureSelection() {
        if (viewController != null) {
            viewController.toggleWorkProcedureContent();
        }
    }

    @Override
    public void togglePermitSelection() {
        if (viewController != null) {
            viewController.toggleWorkPermitContent();
        }
    }
}
