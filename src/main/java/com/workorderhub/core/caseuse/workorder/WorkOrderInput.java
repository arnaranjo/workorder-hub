package com.workorderhub.core.caseuse.workorder;

import java.util.List;

public interface WorkOrderInput {
    /**
     * Toggle the "Valid period" selector and its content.
     * It is allowed to create a work order not scheduled.
     */
    void toggleValidPeriodDisable();

    /**
     * Toggle the "Work procedure" selector and its content.
     * It is allowed to create a work order without a specific work procedure.
     */
    void toggleProcedureSelectionDisable();

    /**
     * Toggles the "Work permit" selector and its content.
     * It is allowed to create a work order without a specific work permit.
     */
    void togglePermitSelectionDisable();

    /**
     * Retrieves the list of work order categories.
     */
    void retrieveWorkOrderCategories();

    /**
     * Retrieves User list that are Technicians.
     */
    void retrieveTechnicianList();

    /**
     * Retrieves the User list allowed to be Holders.
     */
    void retrieveHoldersList();

    /**
     * Retrieves the list of spare parts available.
     */
    void retrieveSparePartsList();

    /**
     * Retrieves the Plant element information based on the provided element tag.
     *
     * @param plantElement Request containing the element tag.
     */
    void getPlantElement(RequestPlantElement plantElement);

    /**
     * Retrieves the list of work orders that are active (Open and In progress) and their information.
     */
    void retrieveWorkFrontList();

    /**
     * Retrieves the list of work procedures available and their information.
     */
    void retrieveWorkProcedureList();

    /**
     * Retrieves the list of LOTO procedures available and their information.
     */
    void retrieveLotoProcedureList();

    /**
     * Creates a new work order with the provided data.
     *
     * @param request the data required to create a new work order,including optional fields such as valid period,
     *                work procedure and work permit.
     * @param categoryList the list of categories to be assigned to the work order.
     * @param participantsList the list of participants to be assigned to the work order.
     * @param usedSparePartList the list of spare parts to be used in the
     */
    void createWorkOrder(
            RequestNewWorkOrder request,
            List<RequestAssignCategory> categoryList,
            List<RequestParticipants> participantsList,
            List<RequestUseSpareParts> usedSparePartList
    );

    /**
     * Updates an existing work order with the provided data.
     * @param request the data required to update the work order, including optional fields such as valid period,
     * @param categoryList the list of categories to be assigned to the work order.
     * @param participantsList the list of participants to be assigned to the work order.
     * @param usedSparePartList the list of spare parts to be used in the work order.
     */
    void updateWorkOrder(
            RequestUpdateWorkOrder request,
            List<RequestAssignCategory> categoryList,
            List<RequestParticipants> participantsList,
            List<RequestUseSpareParts> usedSparePartList
    );
}
