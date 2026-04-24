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
     * Retrieves the ID of the selected work procedure.
     * @return the ID of the selected work procedure, or -1 if no work procedure has been selected.
     */
    int getSelectedWorkProcedure();

    /**
     * Displays the information of a specific work procedure in the view.
     * @param response A ResponseProcedureInfo object containing the information of the work procedure to be displayed in the view.
     */
    void setProcedureInfo(ResponseProcedureInfo response);
}
