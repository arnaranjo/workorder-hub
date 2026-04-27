package com.workorderhub.core.caseuse.technician;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.gateway.WorkOrderGateway;

import java.util.List;

public class TechnicianMainInteractor implements TechnicianMainInput {

    private TechnicianMainOutput output;
    private WorkOrderGateway workOrderGateway;

    public TechnicianMainInteractor(
            TechnicianMainOutput output,
            WorkOrderGateway workOrderGateway
    ) {
        this.output = output;
        this.workOrderGateway = workOrderGateway;
    }

    @Override
    public void retrieveWorkFrontList(RequestWorkFront request) {
        List<WorkFrontRow> workFrontRowList = workOrderGateway.getAssignedWorkList(request.userId()).stream()
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
