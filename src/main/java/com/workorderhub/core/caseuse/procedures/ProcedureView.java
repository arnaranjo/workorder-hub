package com.workorderhub.core.caseuse.procedures;

import java.util.List;

public interface ProcedureView {
    /**
     * Displays a message with information about actions performed with the procedures.
     * E.g. Update a document code or detele a procedure.
     * @param message
     */
    void setInfoDisplay(String message);

    /**
     * Set the function that allows to the user to edit the document code and name,
     * directly in the work procedure table view.
     */
    void setWorkProcedureTableEdition();

    /**
     * Set the function that allows to the user to edit the document code and name,
     * directly in the LOTO procedure table view.
     */
    void setLotoProcedureTableEdition();

    /**
     * Populates the work procedure table.
     */
    void setWorkProcedureTableItems(List<RowWorkProcedure> workProcedureList);

    /**
     * Populates the loto procedure table.
     */
    void setLotoProcedureTableItems(List<RowLotoProcedure> lotoProcedureList);

    /**
     * Sets the columns title in the work procedure table.
     * @param documentCode Document code column title.
     * @param documentName Document name column title.
     */
    void setWorkProcedureTableTitles(String documentCode, String documentName);

    /**
     * Sets the columns title in the LOTO procedure table.
     * @param documentCode Document code column title.
     * @param documentName Document name column title.
     */
    void setLotoProcedureTableTitles(String documentCode, String documentName);
}

