package com.workorderhub.core.caseuse.workorder;

public interface WorkOrderMainView {

    /**
     * Toggles the "Valid period" selector and its content.
     * It is allowed to create a work order not scheduled.
     */
    void toggleValidPeriodContent();

    /**
     * Toggles the "Work procedure" selector and its content.
     * It is allowed to create a work order without a specific work procedure.
     */
    void toggleWorkProcedureContent();

    /**
     * Toggles the "Work permit" selector and its content.
     * It is allowed to create a work order without a specific work permit.
     */
    void toggleWorkPermitContent();

}
