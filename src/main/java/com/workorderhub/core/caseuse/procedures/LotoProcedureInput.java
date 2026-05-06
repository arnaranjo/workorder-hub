package com.workorderhub.core.caseuse.procedures;

public interface LotoProcedureInput {
    /**
     * Gets the complete LOTO procedure list to populates the table, when the window is loaded.
     */
    void retrieveLotoProcedureList();

    /**
     * Creates a new LOTO procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of a new work procedure.
     * @return ID of the new LOTO procedure.
     */
    int CreateLotoProcedure(RequestNewLotoProcedure request);

    /**
     * Updates a LOTO procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of the LOTO procedure to update.
     * @return true if the update is successful or false if not.
     */
    boolean UpdateLotoProcedureCode(RequestUpdateLotoProcedureCode request);

    /**
     * Updates a LOTO procedure name in the database.
     * @param request Record of the LOTO procedure to update.
     * @return true if the update is successful or false if not.
     */
    boolean UpdateLotoProcedureName(RequestUpdateLotoProcedureName request);

    /**
     * Deletes a LOTO procedure in the database.
     *
     * @param request Record of the LOTO procedure to delete.
     * @return true if the deletion is successful or false if not.
     */
    boolean DeleteLotoProcedure(RequestDeleteLotoProcedure request);
}
