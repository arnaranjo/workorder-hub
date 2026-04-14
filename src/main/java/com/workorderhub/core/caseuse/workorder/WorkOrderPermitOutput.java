package com.workorderhub.core.caseuse.workorder;

import java.util.List;

public interface WorkOrderPermitOutput {

    /**
     * Displays the list of LOTO procedures available and their information.
     * @param lotoProcedureList List of LOTO procedures to be displayed.
     */
    void displayLotoProcedureList(List<LotoProcedureRow> lotoProcedureList);
}
