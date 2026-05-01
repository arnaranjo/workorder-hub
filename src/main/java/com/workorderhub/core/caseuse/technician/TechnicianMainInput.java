package com.workorderhub.core.caseuse.technician;

public interface TechnicianMainInput {

    /**
     * Retrieve the list of work orders assigned to the technician.
     * @param request Request object containing necessary parameters to retrieve the work order list.
     */
    void retrieveWorkFrontList(RequestWorkFront request);

    /**
     * Load the details of a specific work order for the technician.
     * @param request Request object containing necessary parameters to load the work order details.
     */
    void loadWorkOrder(RequestLoadWorkOrder request);

    /**
     * Start a specific work order for the technician.
     * @param request Request object containing necessary parameters to start the work order.
     */
    void startWorkOrder(RequestUpdateStatus request);

    /**
     * Set a comment for a specific work order.
     * @param request Request object containing necessary parameters to set the comment for the work order.
     */
    void closeWorkOrder(RequestUpdateStatus request);
}
