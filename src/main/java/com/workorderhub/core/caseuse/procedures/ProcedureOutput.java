package com.workorderhub.core.caseuse.procedures;

import java.util.List;

public interface ProcedureOutput {

    /**
     * Displays a message if the new work procedure is created and add the document ID to the table view.
     * @param response Record of the generated document ID.
     * @param workProcedureEnum Displays a message when the document successfully created, updated or deleted.
     */
    void displayWorkProcedureConfirmation(ResponseWorkProcedure response, WorkProcedureEnum workProcedureEnum);

    /**
     * Displays a message when an error occurs.
     * @param workProcedureEnum Displays a message when an error occurs during the creation, update or deletion.
     */
    void displayWorkProcedureError(WorkProcedureEnum workProcedureEnum);

    /**
     * Displays a message if the new loto procedure is created and add the document ID to the table view.
     * @param response Record of the generated document ID.
     * @param lotoProcedureEnum Displays a message when the document successfully created, updated or deleted.
     */
    void displayLotoProcedureConfirmation(ResponseLotoProcedure response, LotoProcedureEnum lotoProcedureEnum);

    /**
     * Displays a message when an error occurs.
     * @param lotoProcedureEnum Displays a message when an error occurs during the creation, update or deletion.
     */
    void displayLotoProcedureError(LotoProcedureEnum lotoProcedureEnum);

    /**
     * Populates the work table in the UI
     */
    void populateWorkProcedureTable(List<RowWorkProcedure> workProcedureList);

    /**
     * Populates the LOTO procedure table in the UI
     */
    void populateLoroProcedureTable(List<RowLotoProcedure> lotoProcedureList);

}
