package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.core.entity.Category;
import com.workorderhub.core.entity.User;

import java.util.List;

public interface WorkOrderDataOutput {

    /**
     * Sets the list of work order categories to be displayed in the view.
     *
     * @param categoryList A list of Category objects representing the work order categories.
     */
    void setCategoryList(List<Category> categoryList);

    /**
     * Displays the information of the retrieved plant element
     *
     * @param response Response with the plant element information to be displayed in the view.
     */
    void displayPlantElementInfo(ResponsePlantElement response);

    /**
     * Displays the list of technicians in the view.
     *
     * @param technicianList A list of strings representing the names of the user as technicians.
     */
    void displayTechnicianList(List<User> technicianList);

    /**
     * Displays the list of holders in the view.
     *
     * @param holderList A list of strings representing the names of the user as holders.
     * @param userRole   A string representing the role of the user supervisor.
     */
    void displayHolderList(List<User> holderList, String userRole);

    /**
     * Displays the list of spare parts in the view.
     *
     * @param sparePartRowList A list of SparePartRow objects representing the spare parts to be displayed in the view.
     */
    void displaySparePartsList(List<SparePartRow> sparePartRowList);

    /**
     * Displays an error message in the view based on the provided WorkOrderEnum value.
     *
     * @param workOrderEnum An enum value representing the specific error to be displayed in the view.
     *
     */
    void displayError(WorkOrderEnum workOrderEnum);

    /**
     * Displays a confirmation message in the view based on the provided WorkOrderEnum value.
     *
     * @param workOrderEnum An enum value representing the specific confirmation to be displayed in the view.
     * @return A boolean value indicating whether the user confirmed the action (true) or canceled it (false).
     */
    boolean requestConfirmation(WorkOrderEnum workOrderEnum);

    /**
     * Displays a success message in the view based on the provided WorkOrderEnum value.
     *
     * @param workOrderEnum An enum value representing the specific success message to be displayed in the view.
     */
    void displaySuccess(WorkOrderEnum workOrderEnum);
}
