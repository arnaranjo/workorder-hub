package com.workorderhub.core.caseuse.adminpanel;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;

import java.util.List;

public interface AdminMainOutput {

    void displayWorkFrontList(List<WorkFrontRow> workFrontRowList);

    void displayClosedOrdersList(List<WorkFrontRow> closedOrdersList);

    void displayWorkLogList(List<WorkLogRow> workLogRowList);
}
