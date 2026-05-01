package com.workorderhub.infrastructure.ui.supervisor;

import com.workorderhub.core.caseuse.supervisor.SupervisorMainOutput;

public class SupervisorMainPresenter implements SupervisorMainOutput {

    private SupervisorMainController viewController;

    public SupervisorMainPresenter() {}

    public void setViewController(SupervisorMainController viewController) {
        this.viewController = viewController;
    }
}
