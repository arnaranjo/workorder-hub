package com.workorderhub.core.caseuse.workorder;

import java.util.List;

public interface WorkOrderProcedureOutput {

    /**
     * Retrieves the list of work procedures and updates the view accordingly.
     * @param workProcedureList An array of WorkProcedureRow representing the work procedures to be displayed in the view.
     */
    void retrieveWorkProcedureList(List<WorkProcedureRow> workProcedureList);

    /**
     * Displays the information of a specific work procedure in the view.
     * @param response A ResponseProcedureInfo object containing the information of the work procedure to be displayed in the view.
     */
    void displayProcedureInfo(ResponseProcedureInfo response);
}
