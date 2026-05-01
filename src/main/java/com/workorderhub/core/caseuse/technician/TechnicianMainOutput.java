package com.workorderhub.core.caseuse.technician;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;

import java.util.List;

public interface TechnicianMainOutput {

    /**
     * Display the list of work orders assigned to the technician.
     * @param workOrderList List of work orders to display.
     */
    void displayWorkFrontList(List<WorkFrontRow> workOrderList);

    /**
     * Display an error message based on the provided TechnicianEnum value.
     * @param technicianEnum Enum value representing the specific error to display.
     */
    void displayError(TechnicianEnum technicianEnum);

    /**
     * Display a success message based on the provided TechnicianEnum value.
     * @param technicianEnum Enum value representing the specific success message to display.
     */
    void displaySuccess(TechnicianEnum technicianEnum);

    /**
     * Request confirmation from the technician for a specific action, based on the provided TechnicianEnum value.
     * @param technicianEnum Enum value representing the specific action for which confirmation is requested.
     */
    boolean requestConfirmation(TechnicianEnum technicianEnum);

    /**
     * Navigate to the work order check view, passing the necessary response data.
     */
    void loadWorkOrderCheckView();
}
