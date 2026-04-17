package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.WorkPermit;

import java.util.List;

public interface WorkPermitGateway {

    /**
     * Retrieves the list of work permits.
     *
     * @return a list of work permits, or an empty list if no work permits are available.
     */
    List<WorkPermit> getWorkPermitList();

    /**
     * Retrieves a work permit by its unique identifier.
     *
     * @param workPermitId the ID of the work permit to retrieve.
     * @return the work permit with the specified ID, or 0 if no such work permit exists.
     */
    WorkPermit getWorkPermitById(int workPermitId);

    /**
     * Inserts a new work permit into the data source.
     *
     * @param workPermit the work permit to insert.
     * @return the ID of the newly inserted work permit, or 0 if the insertion failed.
     */
    int insertWorkPermit(WorkPermit workPermit);

    /**
     * Deletes an existing work permit from the data source.
     *
     * @param workPermit the work permit to delete.
     * @return true if the work permit was successfully deleted, false otherwise.
     */
    boolean deleteWorkPermit(WorkPermit workPermit);

    /**
     * Updates an existing work permit in the data source.
     *
     * @param workPermit the work permit to update.
     * @return true if the work permit was successfully updated, false otherwise.
     */
    boolean updateWorkPermit(WorkPermit workPermit);
}
