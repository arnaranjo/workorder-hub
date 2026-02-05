package com.workorderhub.core.caseuse.procedures;

import com.workorderhub.core.entity.LotoProcedure;
import com.workorderhub.core.entity.WorkProcedure;

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
     * @param workProcedureEnum Displays a message when the document successfully created, updated or deleted.
     */
    void displayLotoProcedureConfirmation(ResponseLotoProcedure response, LotoProcedureEnum workProcedureEnum);

    /**
     * Displays a message when an error occurs.
     * @param lotoProcedureEnum Displays a message when an error occurs during the creation, update or deletion.
     */
    void displayLotoProcedureError(LotoProcedureEnum lotoProcedureEnum);

    /**
     * Maps the retrieved work produce list to the entity model to populate the table view.
     * @param workProcedureList List of work procedure objects.
     */
    void MapWorkProcedureList(List<WorkProcedure> workProcedureList);

    /**
     * Maps the retrieved loto produce list to the entity model to populate the table view.
     * @param lotoProceduresList List of loto procedure objects.
     */
    void MapLotoProcedureList(List<LotoProcedure> lotoProceduresList);
}
