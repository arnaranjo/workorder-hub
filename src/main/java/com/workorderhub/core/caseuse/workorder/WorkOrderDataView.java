package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.infrastructure.models.CategoryModel;
import com.workorderhub.infrastructure.models.ParticipantModel;
import com.workorderhub.infrastructure.models.SparePartModel;
import com.workorderhub.infrastructure.models.UsedSparePartModel;

import java.util.List;

public interface WorkOrderDataView {

    /**
     * Sets the list of work order categories to be displayed in the view.
     *
     * @param categoryList An array of strings representing the work order categories.
     */
    void setCategoryList(List<CategoryModel> categoryList);

    /**
     * Displays the list of technicians in the view.
     *
     * @param technicianList A list of UserModel objects representing the user as technicians.
     */
    void setTechnicianList(List<ParticipantModel> technicianList);

    /**
     * Displays the information of a technician in the view.
     *
     * @param technicianName        The name of the technician.
     * @param technicianPhoneNumber The phone number of the technician.
     * @param technicianEmail       The email of the technician.
     */
    void displayTechnicianInfo(String technicianName, String technicianPhoneNumber, String technicianEmail);

    /**
     * Displays the list of holders in the view.
     *
     * @param holderList A list of UserModel objects representing the user as holders.
     */
    void setHolderList(List<ParticipantModel> holderList);

    /**
     * Displays the information of a holder in the view.
     *
     * @param holderName        The name of the holder.
     * @param holderPhoneNumber The phone number of the holder.
     * @param holderEmail       The email of the holder.
     */
    void displayHolderInfo(String holderName, String holderPhoneNumber, String holderEmail);

    /**
     * Displays the information of the retrieved plant element
     *
     * @param elementTag         The tag of the plant element.
     * @param elementDescription The description of the plant element.
     * @param elementLocation    The location of the plant element.
     */
    void displayPlantElementInfo(String elementTag, String elementDescription, String elementLocation);

    /**
     * Checks if the period of the work order is required.
     *
     * @return true if the period is required, false otherwise.
     */
    boolean isValidPeriodRequired();

    /**
     * Checks if the work procedure is required.
     *
     * @return true if the work procedure is required, false otherwise.
     */
    boolean isWorkProcedureRequired();

    /**
     * Checks if the work permit is required.
     *
     * @return true if the work permit is required, false otherwise.
     */
    boolean isWorkPermitRequired();

    /**
     * Retrieves the description of the work order from the view.
     *
     * @return the description of the work order, or null if no description has been provided.
     */
    String getWorkOrderDescription();

    /**
     * Confirms the plant element is valid and can be associated with the work order.
     * To be used after the plant element information is displayed, to allow the user to confirm
     * the association of the plant element with the work order.
     *
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
     *
     * @return true if the plant element has been confirmed, false otherwise.
     */
    boolean isPlantElementConfirmed();

    /**
     * Retrieves the ID of the confirmed plant element.
     *
     * @return the ID of the confirmed plant element, or -1 if no plant element has been confirmed.
     */
    Integer getPlantElementId();

    /**
     * Checks if a technician has been confirmed by the user.
     *
     * @return true if a technician has been confirmed, false otherwise.
     */
    boolean isHolderConfirmed();

    /**
     * Retrieves the ID of the confirmed holder.
     *
     * @return the ID of the confirmed holder, or -1 if no holder has been confirmed.
     */
    Integer getHolderId();

    /**
     * Gets the list of selected category IDs.
     *
     * @return a list of integers representing the IDs of the selected categories,
     * or an empty list if no categories have been selected.
     *
     */
    List<CategoryModel> getAssignedCategories();

    /**
     * Gets the list of selected technician IDs.
     *
     * @return a list of integers representing the IDs of the selected technicians,
     * or an empty list if no technicians have been selected.
     */
    List<ParticipantModel> getParticipantsList();

    /**
     * Gets the list of selected spare parts.
     *
     * @return HashMap that contains the spare part ID and number of element to use of that spare part.
     */
    List<UsedSparePartModel> getSparePartsList();

}
