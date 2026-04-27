package com.workorderhub.core.caseuse.technician;

public interface TechnicianMainInput {

    /**
     * Retrieve the list of work orders assigned to the technician.
     * @param request Request object containing necessary parameters to retrieve the work order list.
     */
    void retrieveWorkFrontList(RequestWorkFront request);
}
