package com.workorderhub.core.caseuse.supervisor;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;

import java.util.List;

public interface SupervisorMainOutput {

    /**
     * Displays the list of work fronts assigned to the supervisor.
     * @param workFrontRowList contains the data to be displayed in the work front list.
     */
	void displayWorkFrontList(List<WorkFrontRow> workFrontRowList);

    /**
     * Displays the list of closed work fronts for the supervisor.
     * @param closedWorkList contains the data to be displayed in the closed work front list.
     */
	void displayClosedWorkList(List<WorkFrontRow> closedWorkList);

    /**
     * Signals the presenter to load the work order review view.
     * @param response contains the ID of the order to be reviewed.
     */
    void loadWorkOrderView(ResponseReviewWorkOrder response);
}
