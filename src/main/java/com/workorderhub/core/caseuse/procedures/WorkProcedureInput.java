package com.workorderhub.core.caseuse.procedures;

import com.workorderhub.core.entity.WorkProcedure;

import java.util.List;

public interface WorkProcedureInput {

    /**
     * Gets the complete work procedure list to populates the table, when the window is loaded.
     *
     * @return List of Work Procedure objects.
     */
    List<WorkProcedure> getWorkProcedureList();

    /**
     * Creates a new work procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of a new work procedure.
     */
    void CreateWorkProcedure(RequestWorkProcedure request);

    /**
     * Updates a work procedure in the database.
     * It must compare and check if the document code is used in other document.
     *
     * @param request Record of the work procedure to update.
     */
    void UpdateWorkProcedure(RequestWorkProcedure request);

    /**
     * Deletes a work procedure in the database.
     *
     * @param request Record of the work procedure to delete.
     */
    void DeleteWorkProcedure(RequestWorkProcedure request);
}
