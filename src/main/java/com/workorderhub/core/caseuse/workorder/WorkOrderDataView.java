package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.provider.models.CategoryModel;
import com.workorderhub.provider.models.ParticipantModel;

import java.util.List;

public interface WorkOrderDataView {

    /**
     * Sets the list of work order categories to be displayed in the view.
     * @param categoryList An array of strings representing the work order categories.
     */
    void setCategoryList(List<CategoryModel> categoryList);

    /**
     * Displays the list of technicians in the view.
     * @param technicianList A list of UserModel objects representing the user as technicians.
     */
    void setTechnicianList(List<ParticipantModel> technicianList);

    /**
     * Displays the information of a technician in the view.
     * @param technicianName The name of the technician.
     * @param technicianPhoneNumber The phone number of the technician.
     * @param technicianEmail The email of the technician.
     */
    void displayTechnicianInfo(String technicianName, String technicianPhoneNumber, String technicianEmail);

    /**
     * Displays the list of holders in the view.
     * @param holderList A list of UserModel objects representing the user as holders.
     */
    void setHolderList(List<ParticipantModel> holderList);

    /**
     * Displays the information of a holder in the view.
     * @param holderName The name of the holder.
     * @param holderPhoneNumber The phone number of the holder.
     * @param holderEmail The email of the holder.
     */
    void displayHolderInfo(String holderName, String holderPhoneNumber, String holderEmail);

    /**
     * Displays the information of the retrieved plant element
     * @param elementTag The tag of the plant element.
     * @param elementDescription The description of the plant element.
     * @param elementLocation The location of the plant element.
     */
    void displayPlantElementInfo(String elementTag, String elementDescription, String elementLocation);

    /**
     * Confirms the plant element is valid and can be associated with the work order.
     * To be used after the plant element information is displayed, to allow the user to confirm
     * the association of the plant element with the work order.
     */
    void confirmPlantElement();
}
