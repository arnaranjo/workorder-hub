package com.workorderhub.core.caseuse.supervisor;

public interface SupervisorMainInput {

    /**
     * Requests the output to load the list of work fronts assigned to the supervisor.
     */
	void retrieveWorkFront();

    /**
     * Requests the closed orders for the supervisor.
     * @param request contains the ID of the work front to retrieve closed work for.
     */
	void retrieveClosedWork(RequestClosedWork request);

    /**
     * Requests the details of a specific work order for review.
     * @param request contains the ID of the work order to review.
     */
    void reviewWorkOrder(RequestReviewWorkOrder request);
}
