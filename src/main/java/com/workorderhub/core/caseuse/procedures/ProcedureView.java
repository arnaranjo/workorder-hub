package com.workorderhub.core.caseuse.procedures;

public interface ProcedureView {

    /**
     * Displays a message at the bottom label of the screen.
     */
    void setButtonDisplay(String message);

    /**
     * Displays a message at the top of the screen when an error occurs.
     *
     * @param message Message to display.
     * @param style   Label style.
     */
    void setTopDisplay(String message, String style);

    /**
     * Set the function that allows to the user to edit the document code and name, directly in the table view.
     */
    void setTableEdition();

    /**
     * Populates the work procedure table.
     */
    void PopulatesWorkProcedureTable();

    /**
     * Populates the loto procedure table.
     */
    void PopulatesLotoProcedureTable();
}

