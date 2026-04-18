package com.workorderhub.core.caseuse.workorder;

public interface WorkOrderMainOutput {
    /**
     * Toggle the "Valid period" selector and its content.
     * It is allowed to create a work order not scheduled.
     */
    void toggleValidPeriodSelection();

    /**
     * Toggle the "Work procedure" selector and its content.
     * It is allowed to create a work order without a specific work procedure.
     */
    void toggleProcedureSelection();

    /**
     * Toggle the "Work permit" selector and its content.
     * It is allowed to create a work order without a specific work permit.
     */
    void togglePermitSelection();


}
