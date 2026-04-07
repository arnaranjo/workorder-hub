package com.workorderhub.core.caseuse.workorder;

import java.util.List;

public interface WorkOrderPeriodOutput {

    /**
     * Displays the list of work orders that are active (Open and On going))
     * @param workFrontRowList List of work orders active represented as WorkFrontRow objects.
     */
    void displayWorkFrontList(List<WorkFrontRow> workFrontRowList);
}
