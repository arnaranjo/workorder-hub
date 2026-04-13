package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.provider.models.WorkProcedureModel;

import java.util.List;

public interface WorkOrderProcedureView {

    /**
     * Sets the list of work procedures to be displayed in the view.
     * @param workProcedureList A list of WorkProcedureModel representing the work procedures to be displayed in the view.
     */
    void setWorkProcedureList(List<WorkProcedureModel> workProcedureList);
}
