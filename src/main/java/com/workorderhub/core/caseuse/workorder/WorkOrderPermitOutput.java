package com.workorderhub.core.caseuse.workorder;

import java.util.List;

public interface WorkOrderPermitOutput {

    /**
     * Displays the list of LOTO procedures available and their information.
     * @param lotoProcedureList List of LOTO procedures to be displayed.
     */
    void displayLotoProcedureList(List<LotoProcedureRow> lotoProcedureList);

    /**
     * Displays the information of the work permit, including its description, associated lock devices, and selected LOTO procedure details.
     * @param response The response object containing the work permit information to be displayed.
     */
    void displayWorkPermitInfo(ResponseWOrkPermitInfo response);
}
