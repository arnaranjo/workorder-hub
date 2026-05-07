package com.workorderhub.core.caseuse.adminpanel;

public interface AdminMainInput {

    /**
     * Retrieve the list of work fronts with status OPEN and ONGOING, and pass it to the output for display.
     */
    void retrieveWorkFront();

    /**
     * Retrieve the list of closed work orders starting from a specified date, and pass it to the output for display.
     *
     * @param request the request containing the start date for retrieving closed work orders.
     *                If the start date is null, all closed work orders will be retrieved regardless of their closing date.
     */
    void retrieveClosedOrders(RequestClosedOrders request);

    /**
     * Retrieve the list of work logs for a specific work order, and pass it to the output for display.
     *
     * @param request the request containing the work order ID for retrieving work logs.
     */
    void retrieveWorkLogs(RequestWorkLogs request);

    /**
     * Delete a work order based on the provided request, which contains the necessary information to identify and delete the work order.
     *
     * @param request the request containing the information needed to delete the work order.
     */
    void deleteWorkOrder(RequestDeleteWorkOrder request);
}
