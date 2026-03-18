package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.AssignedCategory;

import java.util.List;

public interface AssignedCategoryGateway {

    /**
     * Gets the categories assigned to a specific work order.
     * @param workOrderId the ID of the work order.
     * @return a list of assigned categories for the work order.
     */
    List<AssignedCategory> getCategoriesByWorkOrder(long workOrderId);

    /**
     * Inserts a new assigned category for a work order.
     * @param assignedCategory the assigned category to be inserted.
     * @return true if the insertion was successful, false otherwise.
     */
    boolean insertAssignedCategory(AssignedCategory assignedCategory);

    /**
     * Inserts a batch of assigned categories for work orders.
     * @param assignedCategoryList the list of assigned categories to be inserted.
     * @return true if the batch insertion was successful, false otherwise.
     */
    boolean insertAssignedCategoryBatch(List<AssignedCategory> assignedCategoryList);

    /**
     * Deletes an assigned category from a work order.
     * @param assignedCategory the assigned category to be deleted.
     * @return true if the deletion was successful, false otherwise.
     */
    boolean deleteAssignedCategory(AssignedCategory assignedCategory);

    /**
     * Updates an assigned category for a work order.
     * @param assignedCategory the assigned category to be updated.
     * @param newAssignedCategoryId the new category ID to be assigned.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateAssignedCategory(AssignedCategory assignedCategory, int newAssignedCategoryId);
}
