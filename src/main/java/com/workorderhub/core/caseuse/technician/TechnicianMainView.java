package com.workorderhub.core.caseuse.technician;

import com.workorderhub.infrastructure.models.WorkFrontModel;

import java.util.List;

public interface TechnicianMainView {

    /**
     * Display the list of work orders assigned to the technician.
     * @param workFrontList List of work orders to display.
     */
    void setWorkFrontList(List<WorkFrontModel> workFrontList);
}
