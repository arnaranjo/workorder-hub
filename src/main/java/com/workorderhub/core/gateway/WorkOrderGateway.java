package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.StatusEnum;
import com.workorderhub.core.entity.WorkOrderElement;
import com.workorderhub.core.entity.WorkOrderInfo;

import java.time.LocalDate;
import java.util.List;

public interface WorkOrderGateway {
    /**
     * Get a list of all work orders in the system.
     *
     * @return A list of WorkOrderInfo objects representing all work orders.
     */
    List<WorkOrderInfo> getWorkOrderList();

    /**
     * Get a list of work order elements that are currently in the "work front" status.
     *
     * @param firstStatus first status to include in the query.
     * @param secondStatus second status to include in the query.
     * @return A list of WorkOrderElement objects representing work orders in the "work front" status.
     */
    List<WorkOrderElement> getWorkFrontList(StatusEnum firstStatus, StatusEnum secondStatus);

    /**
     * Get a list of work order elements that are assigned to a specific employee.
     *
     * @param employeeId The ID of the employee for whom to retrieve assigned work orders.
     * @param firstStatus first status to include in the query.
     * @param secondStatus second status to include in the query.
     * @return A list of WorkOrderElement objects representing work orders assigned to the specified employee.
     */
    List<WorkOrderElement> getAssignedWorkList(int employeeId, StatusEnum firstStatus, StatusEnum secondStatus);

    /**
     * Get a list of work order elements that have been closed since a specified start date.
     *
     * @param startDate The date from which to retrieve closed work orders.
     *                  Only work orders closed on or after this date will be included in the result.
     * @param statusEnum status to include in the closed work orders query.
     * @return  A list of WorkOrderElement objects representing work orders that have been closed
     *          since the specified start date.
     */
    List<WorkOrderElement> getClosedWorkList(LocalDate startDate, StatusEnum statusEnum);

    /**
     * Get a work order element by its unique identifier.
     * @param workOrderId The ID of the work order for which to retrieve the element.
     * @return A WorkOrderElement object representing the work order with the specified ID,
     *          or null if no such work order exists.
     */
    WorkOrderElement getWorkFrontElement(long workOrderId);

    /**
     * Get work order info element by its unique identifier.
     * @param workOrderId The ID of the work order for which to retrieve information.
     * @return A WorkOrderInfo object containing detailed information about the work order with the specified ID,
     */
    WorkOrderInfo getWorkOrderById(long workOrderId);

    /**
     * Insert a new work order into the system.
     * @param workOrderInfo A WorkOrderInfo object containing the details of the work order to be inserted.
     * @return true if the work order was successfully inserted, false otherwise.
     */
    boolean insertWorkOrder(WorkOrderInfo workOrderInfo);

    /**
     * Delete an existing work order from the system.
     * @param workOrderInfo A WorkOrderInfo object representing the work order to be deleted.
     * @return true if the work order was successfully deleted, false otherwise.
     */
    boolean deleteWorkOrder(WorkOrderInfo workOrderInfo);

    /**
     * Update an existing work order in the system.
     * @param workOrderInfo A WorkOrderInfo object containing the updated details of the work order to be updated.
     * @return true if the work order was successfully updated, false otherwise.
     */
    boolean updateWorkOrder(WorkOrderInfo workOrderInfo);

    /**
     * Start a specific work order in the system.
     * @param workOrderId The ID of the work order to be started.
     * @param status New status to assign to the work order.
     * @return true if the work order was successfully started, false otherwise.
     */
    boolean updateWorkOrderStatus(long workOrderId, StatusEnum status);


    /**
     * Complete a specific work order in the system.
     * @param workOrderId The ID of the work order.
     * @param comment A comment to be added to the work order.
     * @return true if the work order was successfully completed, false otherwise.
     */
    boolean setWorkOrderComment(long workOrderId, String comment);
}
