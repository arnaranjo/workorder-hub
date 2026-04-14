package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.provider.models.LotoProcedureModel;

import java.util.List;

public interface WorkOrderPermitView {

    /**
     * Displays the list of LOTO procedures available and their information.
     * @param lotoProcedureList List of LOTO procedures to be displayed.
     */
    void setLotoProcedureList(List<LotoProcedureModel> lotoProcedureList);
}
