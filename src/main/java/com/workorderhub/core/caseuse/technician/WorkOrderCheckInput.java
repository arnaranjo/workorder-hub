package com.workorderhub.core.caseuse.technician;

public interface WorkOrderCheckInput {
    /**
     * Load the details of a specific work order for the technician.
     * @param request Request object containing necessary parameters to load the work order details.
     */
    void loadWorkOrderData(RequestLoadWorkOrder request);

    /**
     * Set the technician's comment for a specific work order.
     * @param request Request object containing the work order ID and the comment to be set by the technician.
     */
    void setTechnicianComment(RequestSetComment request);

    /**
     * Update the list of used spare parts for a specific work order.
     * @param request Request object containing necessary parameters to update the used spare parts for the work order, such as the work order ID and the list of used spare parts to be updated.
     */
    void updateUsedSparePart(RequestUpdateUsedSparePart request);
}
