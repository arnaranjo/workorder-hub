package com.workorderhub.core.caseuse.supervisor;

import com.workorderhub.infrastructure.models.WorkFrontModel;

import java.util.List;

public interface SupervisorMainView {

	void setWorkFrontList(List<WorkFrontModel> workFrontList);

	void setClosedWorkList(List<WorkFrontModel> closedWorkList);
}
