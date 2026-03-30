package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.provider.models.CategoryModel;
import com.workorderhub.provider.models.ParticipantModel;
import com.workorderhub.provider.models.SparePartModel;
import com.workorderhub.provider.models.UsedSparePartModel;

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
     * @param elementId The ID of the plant element to be confirmed.
     */
    void confirmPlantElement(int elementId);

    /**
     * Populates the spare part table.
     *
     * @param sparePartList A list of SparePartRow objects containing the information
     *                      to be displayed in the spare parts table.
     */
    void setSparePartTableItems(List<SparePartModel> sparePartList);

    /**
     * Checks if the plant element has been confirmed by the user.
     * @return true if the plant element has been confirmed, false otherwise.
     */
    boolean isPlantElementConfirmed();

    /**
     * Retrieves the ID of the confirmed plant element.
     * @return the ID of the confirmed plant element, or -1 if no plant element has been confirmed.
     */
    int getPlantElementId();

    /**
     * Gets the list of selected category IDs.
     * @return a list of integers representing the IDs of the selected categories,
     * or an empty list if no categories have been selected.
     *
     */
    List<Integer> getAssignedCategories();

    /**
     * Checks if a technician has been confirmed by the user.
     * @return true if a technician has been confirmed, false otherwise.
     */
    boolean isHolderConfirmed();

    /**
     * Retrieves the ID of the confirmed holder.
     * @return the ID of the confirmed holder, or -1 if no holder has been confirmed.
     */
    int getHolderId();

    /**
     * Gets the list of selected technician IDs.
     * @return a list of integers representing the IDs of the selected technicians,
     * or an empty list if no technicians have been selected.
     */
    List<Integer> getParticipantsList();

    /**
     * Gets the list of selected spare parts.
     * @return a list of SparePartModel objects representing the selected spare parts,
     * or an empty list if no spare parts have been selected.
     */
    List<UsedSparePartModel> getSparePartsList();

}
