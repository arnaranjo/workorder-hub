package com.workorderhub.core.caseuse.adminpanel;

import com.workorderhub.provider.models.WorkFrontModel;
import com.workorderhub.provider.models.WorkLogModel;

import java.util.List;

public interface AdminMainView {

    /**
     * Displays the list of work fronts that are open and in progress.
     *
     * @param workFrontModelList List of work fronts to be displayed.
     */
    void setWorkFrontList(List<WorkFrontModel> workFrontModelList);

    /**
     * Displays the list of work fronts that are closed.
     * @param closedOrdersList List of closed work fronts to be displayed.
     */
    void setClosedOrdersList(List<WorkFrontModel> closedOrdersList);

    /**
     * Displays the list of work logs.
     * @param workLogList List of work logs to be displayed.
     */
    void setWorkLogList(List<WorkLogModel> workLogList);
}
