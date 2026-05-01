package com.workorderhub.core.caseuse.technician;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.entity.StatusEnum;
import com.workorderhub.core.entity.WorkOrderElement;
import com.workorderhub.core.gateway.*;

import java.util.List;

public class TechnicianMainInteractor implements TechnicianMainInput {

    private TechnicianMainOutput output;
    private WorkOrderGateway workOrderGateway;

    private int userId;

    public TechnicianMainInteractor(
            TechnicianMainOutput output,
            WorkOrderGateway workOrderGateway
    ) {
        this.output = output;
        this.workOrderGateway = workOrderGateway;
    }

    @Override
    public void retrieveWorkFrontList(RequestWorkFront request) {
        this.userId = request.userId();
        loadWorkFrontList(userId);
    }

    @Override
    public void loadWorkOrder(RequestLoadWorkOrder request) {
        if (request.workOrderId() <= 0) {
            output.displayError(TechnicianEnum.WORK_ORDER_ID_ERROR);
            return;
        }

        WorkOrderElement workOrderElement = workOrderGateway.getWorkFrontElement(request.workOrderId());

        if (workOrderElement == null) {
            output.displayError(TechnicianEnum.WORK_ORDER_NOT_FOUND);
            return;
        }

        output.loadWorkOrderCheckView();
    }

    @Override
    public void startWorkOrder(RequestUpdateStatus request) {
        if (request.workOrderId() <= 0) {
            output.displayError(TechnicianEnum.WORK_ORDER_ID_ERROR);
            return;

        }
        if (output.requestConfirmation(TechnicianEnum.REQUEST_CONFIRMATION_START_WORK_ORDER)) {

            if (workOrderGateway.updateWorkOrderStatus(request.workOrderId(), StatusEnum.ONGOING)) {
                output.displaySuccess(TechnicianEnum.WORK_ORDER_START_SUCCESS);
                loadWorkFrontList(userId);

            } else {
                output.displayError(TechnicianEnum.WORK_ORDER_START_ERROR);

            }
        }
    }

    @Override
    public void closeWorkOrder(RequestUpdateStatus request) {
        if (request.workOrderId() <= 0) {
            output.displayError(TechnicianEnum.WORK_ORDER_ID_ERROR);
            return;

        }
        if (output.requestConfirmation(TechnicianEnum.REQUEST_CONFIRMATION_CLOSE_WORK_ORDER)) {

            if (workOrderGateway.updateWorkOrderStatus(request.workOrderId(), StatusEnum.CLOSED)) {
                output.displaySuccess(TechnicianEnum.WORK_ORDER_CLOSE_SUCCESS);
                loadWorkFrontList(userId);

            } else {
                output.displayError(TechnicianEnum.WORK_ORDER_CLOSE_ERROR);

            }
        }
    }

    /**
     * Reload the work front list for the technician after performing an action that may have changed the status of work orders.
     * @param userId The ID of the technician to reload the work front list.
     */
    private void loadWorkFrontList(int userId) {
        List<WorkFrontRow> workFrontRowList = workOrderGateway
                .getAssignedWorkList(userId, StatusEnum.OPEN, StatusEnum.ONGOING).stream()
                .map(workOrderElement -> new WorkFrontRow(
                        workOrderElement.getWorkOrder().getWorkOrderId(),
                        workOrderElement.getWorkOrder().getDescription(),
                        workOrderElement.getWorkOrder().getStartDate(),
                        workOrderElement.getWorkOrder().getEndDate(),
                        workOrderElement.getPlantElement().getElementTag(),
                        workOrderElement.getWorkProcedure().getDocumentCode(),
                        workOrderElement.getWorkPermit().getLockoutDeviceId(),
                        workOrderElement.getLotoProcedure().getDocumentCode(),
                        workOrderElement.getUser().getUserName(),
                        workOrderElement.getCurrentStatus().getOrderStatus()
                )).toList();
        output.displayWorkFrontList(workFrontRowList);
    }
}
