package com.workorderhub.core.caseuse.adminpanel;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.gateway.WorkLogGateway;
import com.workorderhub.core.gateway.WorkOrderGateway;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminMainInteractor implements AdminMainInput {

    private AdminMainOutput output;
    private WorkOrderGateway workOrderGateway;
    private WorkLogGateway workLogGateway;

    public AdminMainInteractor(
            AdminMainOutput output,
            WorkOrderGateway workOrderGateway,
            WorkLogGateway workLogGateway
    ) {
        this.output = output;
        this.workOrderGateway = workOrderGateway;
        this.workLogGateway = workLogGateway;
    }

    @Override
    public void retrieveWorkFronts() {
        List<WorkFrontRow> workFrontRowList = workOrderGateway.getWorkFrontList().stream()
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

    @Override
    public void retrieveClosedOrders(RequestClosedOrders request) {
        LocalDate date = request.startDate();
        List<WorkFrontRow> closedOrdersList = workOrderGateway.getClosedWorkList(date).stream()
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
        output.displayClosedOrdersList(closedOrdersList);
    }

    @Override
    public void retrieveWorkLogs(RequestWorkLogs request) {
        LocalDateTime date = request.startDate().atStartOfDay();
        List<WorkLogRow> workLogRowList = workLogGateway.getWorkLogElementList(date).stream()
                .map(workLogElement -> new WorkLogRow(
                        workLogElement.getLogId(),
                        workLogElement.getLogDate(),
                        workLogElement.getLogComment(),
                        workLogElement.getWorkOrderId(),
                        workLogElement.getUserName(),
                        workLogElement.getWorkOrderStartDate(),
                        workLogElement.getWorkOrderEndDate(),
                        workLogElement.getHolderName(),
                        workLogElement.getWorkPermitId()
                )).toList();
        output.displayWorkLogList(workLogRowList);
    }
}
