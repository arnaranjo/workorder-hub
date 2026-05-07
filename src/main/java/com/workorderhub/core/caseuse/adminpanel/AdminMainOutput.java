package com.workorderhub.core.caseuse.adminpanel;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;

import java.util.List;

public interface AdminMainOutput {

    /**
     * Display a list of work fronts in the admin panel.
     *
     * @param workFrontRowList A list of WorkFrontRow objects representing the work fronts to be displayed in the admin panel.
     */
    void displayWorkFrontList(List<WorkFrontRow> workFrontRowList);

    /**
     * Display a list of closed work orders in the admin panel.
     *
     * @param closedOrdersList A list of WorkFrontRow objects representing the closed work orders to be displayed in the admin panel.
     */
    void displayClosedOrdersList(List<WorkFrontRow> closedOrdersList);

    /**
     * Display a list of work logs for a specific work order in the admin panel.
     *
     * @param workLogRowList A list of WorkLogRow objects representing the work logs to be displayed in the admin panel for a specific work order.
     */
    void displayWorkLogList(List<WorkLogRow> workLogRowList);

    /**
     * Display an error message in the admin panel based on the provided AdminMainEnum value,
     * which indicates the type of error that occurred.
     *
     * @param adminMainEnum An AdminMainEnum value representing the type of error that occurred,
     *                      which will be used to determine the appropriate error message to display in the admin panel.
     */
    void displayError(AdminMainEnum adminMainEnum);

    /**
     * Request confirmation from the user in the admin panel based on the provided AdminMainEnum value,
     *
     * @param adminMainEnum An AdminMainEnum value representing the type of action that requires user confirmation,
     *                      will be used to determine the appropriate confirmation message to display in the admin panel and to handle the user's response accordingly.
     * @return true if the user confirms the action, false otherwise.
     */
    boolean requestConfirmation(AdminMainEnum adminMainEnum);

    /**
     * Display a success message in the admin panel based on the provided AdminMainEnum value,
     *
     * @param adminMainEnum An AdminMainEnum value representing the type of successful action that occurred,
     *                      which will be used to determine the appropriate success message to display in the admin panel.
     */
    void displaySuccess(AdminMainEnum adminMainEnum);
}
