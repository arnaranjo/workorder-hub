package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.infrastructure.models.LotoProcedureModel;

import java.util.List;

public interface WorkOrderPermitView {

    /**
     * Displays the list of LOTO procedures available and their information.
     * @param lotoProcedureList List of LOTO procedures to be displayed.
     */
    void setLotoProcedureList(List<LotoProcedureModel> lotoProcedureList);

    /**
     * Retrieves the LOTO procedure selected for the work permit.
     *
     * @return the LOTO procedure selected for the work permit, null if no LOTO procedure has been selected.
     */
    String getPermitDescription();

    /**
     * Gets the lock devices specified for the LOTO procedure of the work permit.
     *
     * @return the lock devices specified for the LOTO procedure of the work permit,
     * null if no lock devices have been specified.
     */
    String getLockDevices();

    /**
     * Gets the selected LOTO procedure for the work permit.
     *
     * @return the selected LOTO procedure ID for the work permit, null if no LOTO procedure has been selected.
     */
    Integer getSelectedLotoProcedureId();

    /**
     * Sets the information of the work permit, including the description, lock devices and the selected LOTO procedure.
     * @param description the description of the work permit.
     * @param lockDevices the lock devices specified for the LOTO procedure of the work permit.
     * @param model the selected LOTO procedure for the work permit.
     */
    void setWorkPermitInfo(String description, String lockDevices, LotoProcedureModel model);
}
