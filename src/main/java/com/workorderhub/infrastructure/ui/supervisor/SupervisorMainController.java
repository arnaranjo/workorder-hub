package com.workorderhub.infrastructure.ui.supervisor;

import com.workorderhub.core.caseuse.supervisor.SupervisorMainInput;
import com.workorderhub.core.caseuse.supervisor.SupervisorMainView;

public class SupervisorMainController implements SupervisorMainView {

    private SupervisorMainInput interactor;

    public SupervisorMainController(SupervisorMainInput interactor) {
        this.interactor = interactor;
    }
}
