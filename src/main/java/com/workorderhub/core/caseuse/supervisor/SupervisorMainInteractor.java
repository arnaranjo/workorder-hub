package com.workorderhub.core.caseuse.supervisor;

public class SupervisorMainInteractor implements SupervisorMainInput {

    private SupervisorMainOutput output;

    public SupervisorMainInteractor(SupervisorMainOutput output) {
        this.output = output;
    }
}
