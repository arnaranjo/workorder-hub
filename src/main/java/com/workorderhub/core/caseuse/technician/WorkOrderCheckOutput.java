package com.workorderhub.core.caseuse.technician;

import java.util.List;

public interface WorkOrderCheckOutput {
    /**
     * Display the details of a specific work order for the technician.
     * @param responseWorkOrderData Response object containing the details of the work order to display.
     */
    void displayWorkOrderData(ResponseWorkOrderData responseWorkOrderData);

    /**
     * Display the details of the holder of the work order for the technician.
     * @param responseHolderInfo Response object containing the details of the holder to display.
     */
    void displayHolderInfo(ResponseHolderInfo responseHolderInfo);

    /**
     * Display the details of a specific plant element for the technician.
     * @param responsePlantElement Response object containing the details of the plant element to display.
     */
    void displayPlantElement(ResponsePlantElement responsePlantElement);

    /**
     * Display the details of a specific work procedure for the technician.
     * @param responseProcedureInfo Response object containing the details of the work procedure to display.
     */
    void displayProcedureInfo(ResponseProcedureInfo responseProcedureInfo);

    /**
     * Display the details of a specific work permit for the technician.
     * @param responseWorkPermit Response object containing the details of the work permit to display.
     */
    void displayWorkPermit(ResponseWorkPermit responseWorkPermit);

    /**
     * Display the list of participants involved in the work order for the technician.
     * @param participantList List of participants to display.
     */
    void displayParticipantList(List<ResponseParticipant> participantList);

    /**
     * Display the list of tools used in the work order for the technician.
     * @param usedSparePartList List of tools used to display.
     */
    void displayUsedSparePartList(List<ResponseUsedSparePart> usedSparePartList);

    /**
     * Display a success message after successfully operations related to the work order check.
     *
     * @param workOrderCheckEnum Enum value representing the technician who performed the check.
     */
    void displaySuccess(WorkOrderCheckEnum workOrderCheckEnum);

    /**
     * Display a confirmation message before performing operations related to the work order check.
     * @param workOrderCheckEnum Enum value representing the technician who is about to perform the check.
     * @return boolean value indicating whether the technician confirmed the action (true) or canceled it (false).
     */
    boolean requestConfirmation(WorkOrderCheckEnum workOrderCheckEnum);
}
