package com.workorderhub.core.caseuse.supervisor;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;

import java.util.List;

public interface SupervisorMainOutput {

	void displayWorkFrontList(List<WorkFrontRow> workFrontRowList);

	void displayClosedWorkList(List<WorkFrontRow> closedWorkList);
}
