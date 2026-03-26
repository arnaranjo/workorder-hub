package com.workorderhub.core.caseuse.workorder;

public interface WorkOrderInput {
    /**
     * Toggle the "Valid period" selector and its content.
     * It is allowed to create a work order not scheduled.
     */
    void toggleValidPeriodDisable();

    /**
     * Toggle the "Work procedure" selector and its content.
     * It is allowed to create a work order without a specific work procedure.
     */
    void toggleProcedureSelectionDisable();

    /**
     * Toggles the "Work permit" selector and its content.
     * It is allowed to create a work order without a specific work permit.
     */
    void togglePermitSelectionDisable();

    /**
     * Retrieves the list of work order categories.
     */
    void retrieveWorkOrderCategories();

    /**
     * Retrieves User list that are Technicians.
     */
    void retrieveTechnicianList();

    /**
     * Retrieves the User list allowed to be Holders.
     */
    void retrieveHoldersList();
}
