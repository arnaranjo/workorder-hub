package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.provider.models.WorkFrontModel;

import java.util.List;

public interface WorkOrderPeriodView {

    void setWorkFrontList(List<WorkFrontModel> workFrontModelList);
}
