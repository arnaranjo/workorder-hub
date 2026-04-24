package com.workorderhub.core.caseuse.workorder;

import java.util.List;

public interface WorkOrderPeriodOutput {

    /**
     * Displays the list of work orders that are active (Open and On going))
     * @param workFrontRowList List of work orders active represented as WorkFrontRow objects.
     */
    void displayWorkFrontList(List<WorkFrontRow> workFrontRowList);

    /**
     * Displays the information of the valid period of the work order.
     * @param response Response with the valid period information to be displayed in the view.
     */
    void displayValidPeriodInfo(ResponseValidPeriod response);
}
