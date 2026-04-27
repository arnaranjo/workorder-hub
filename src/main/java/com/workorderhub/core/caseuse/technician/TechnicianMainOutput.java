package com.workorderhub.core.caseuse.technician;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;

import java.util.List;

public interface TechnicianMainOutput {

    /**
     * Display the list of work orders assigned to the technician.
     * @param workOrderList List of work orders to display.
     */
    void displayWorkFrontList(List<WorkFrontRow> workOrderList);
}
