package com.workorderhub.core.caseuse.supervisor;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.entity.StatusEnum;
import com.workorderhub.core.entity.WorkOrderElement;
import com.workorderhub.core.gateway.WorkOrderGateway;

import java.time.LocalDate;
import java.util.List;

public class SupervisorMainInteractor implements SupervisorMainInput {

    private final SupervisorMainOutput output;
    private final WorkOrderGateway workOrderGateway;

    public SupervisorMainInteractor(SupervisorMainOutput output, WorkOrderGateway workOrderGateway) {
        this.output = output;
        this.workOrderGateway = workOrderGateway;
    }

    @Override
    public void retrieveWorkFront() {
        List<WorkFrontRow> workFrontList = workOrderGateway
                .getWorkFrontList(StatusEnum.OPEN, StatusEnum.ONGOING).stream()
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

        output.displayWorkFrontList(workFrontList);
    }

    @Override
    public void retrieveClosedWork(RequestClosedWork request) {
        LocalDate startDate = request.startDate();
        List<WorkFrontRow> closedWorkList = workOrderGateway
                .getClosedWorkList(startDate, StatusEnum.CLOSED).stream()
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

        output.displayClosedWorkList(closedWorkList);
    }

    @Override
    public void reviewWorkOrder(RequestReviewWorkOrder request) {
        if (request.workOrderId() <= 0) {
            return;
        }

        WorkOrderElement workOrderElement = workOrderGateway.getWorkFrontElement(request.workOrderId());

        if (workOrderElement == null) {
            return;
        }

        output.loadWorkOrderView(new ResponseReviewWorkOrder(request.workOrderId()));
    }
}
