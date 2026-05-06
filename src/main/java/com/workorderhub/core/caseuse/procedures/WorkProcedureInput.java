package com.workorderhub.core.caseuse.procedures;

public interface WorkProcedureInput {

    /**
     * Gets the complete work procedure list to populates the table, when the window is loaded.
     */
    void retrieveWorkProcedureList();

    /**
     * Creates a new work procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of a new work procedure.
     * @return ID of the new work procedure.
     */
    int CreateWorkProcedure(RequestNewWorkProcedure request);


    /**
     * Updates a work procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of the work procedure to update.
     * @return true if the update is successful or false if not.
     */
    boolean UpdateWorkProcedureCode(RequestUpdateWorkProcedureCode request);

    /**
     * Updates a work procedure name in the database.
     * @param request Record of the work procedure to update.
     * @return true if the update is successful or false if not.
     */
    boolean UpdateWorkProcedureName(RequestUpdateWorkProcedureName request);

    /**
     * Deletes a work procedure in the database.
     *
     * @param request Record of the work procedure to delete.
     * @return true if the deletion is successful or false if not.
     */
    boolean DeleteWorkProcedure(RequestDeleteWorkProcedure request);
}
