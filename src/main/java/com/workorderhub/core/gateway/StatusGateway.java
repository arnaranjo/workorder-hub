package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.Status;

import java.util.List;

public interface StatusGateway {

    /**
     * Retrieves a list of all work order status.
     * @return A list of Status objects.
     */
    List<Status> getStatusList();

    /**
     * Inserts a new work order status into the system.
     * @param status The Status object to be inserted.
     * @return The ID of the newly inserted status, or 0 if the insertion failed.
     */
    int insertStatus(Status status);

    /**
     * Deletes an existing work order status from the system.
     * @param status The Status object to be deleted.
     * @return true if the deletion was successful, false otherwise.
     */
    boolean deleteStatus(Status status);

    /**
     * Updates an existing work order status in the system.
     * @param status The Status object containing the updated information.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateStatus(Status status);
}
