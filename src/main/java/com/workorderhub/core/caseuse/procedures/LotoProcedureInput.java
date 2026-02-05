package com.workorderhub.core.caseuse.procedures;

import com.workorderhub.core.entity.LotoProcedure;

import java.util.List;

public interface LotoProcedureInput {


    /**
     * Gets the complete LOTO procedure list to populates the table, when the window is loaded.
     *
     * @return List of LOTO Procedure objects.
     */
    List<LotoProcedure> getLotoProcedureList();

    /**
     * Creates a new LOTO procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of a new work procedure.
     */
    void CreateLotoProcedure(RequestLotoProcedure request);

    /**
     * Updates a LOTO procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of the LOTO procedure to update.
     */
    void UpdateLotoProcedure(RequestLotoProcedure request);

    /**
     * Deletes a LOTO procedure in the database.
     *
     * @param request Record of the LOTO procedure to delete.
     */
    void DeleteLotoProcedure(RequestLotoProcedure request);
}
