package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.infrastructure.models.WorkProcedureModel;

import java.util.List;

public interface WorkOrderProcedureView {

    /**
     * Sets the list of work procedures to be displayed in the view.
     * @param workProcedureList A list of WorkProcedureModel representing the work procedures to be displayed in the view.
     */
    void setWorkProcedureList(List<WorkProcedureModel> workProcedureList);

    /**
     * Checks if a work procedure has been selected by the user.
     * @return true if a work procedure has been selected, false otherwise.
     */
    boolean isWorkProcedureSelected();

    /**
     * Retrieves the ID of the selected work procedure.
     * @return the ID of the selected work procedure, or -1 if no work procedure has been selected.
     */
    int getSelectedWorkProcedure();
}
