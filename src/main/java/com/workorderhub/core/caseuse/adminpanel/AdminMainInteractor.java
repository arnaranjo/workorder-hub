package com.workorderhub.core.caseuse.adminpanel;

import com.workorderhub.core.caseuse.workorder.WorkFrontRow;
import com.workorderhub.core.entity.StatusEnum;
import com.workorderhub.core.entity.UsedSparePart;
import com.workorderhub.core.gateway.AssignedCategoryGateway;
import com.workorderhub.core.gateway.ParticipantGateway;
import com.workorderhub.core.gateway.UsedSparePartGateway;
import com.workorderhub.core.gateway.WorkLogGateway;
import com.workorderhub.core.gateway.WorkOrderGateway;
import com.workorderhub.core.entity.AssignedCategory;
import com.workorderhub.core.entity.Participant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminMainInteractor implements AdminMainInput {

    private AdminMainOutput output;
    private WorkOrderGateway workOrderGateway;
    private WorkLogGateway workLogGateway;
    private AssignedCategoryGateway assignedCategoryGateway;
    private ParticipantGateway participantGateway;
    private UsedSparePartGateway usedSparePartGateway;

    public AdminMainInteractor(
            AdminMainOutput output,
            WorkOrderGateway workOrderGateway,
            WorkLogGateway workLogGateway,
            AssignedCategoryGateway assignedCategoryGateway,
            ParticipantGateway participantGateway,
            UsedSparePartGateway usedSparePartGateway
    ) {
        this.output = output;
        this.workOrderGateway = workOrderGateway;
        this.workLogGateway = workLogGateway;
        this.assignedCategoryGateway = assignedCategoryGateway;
        this.participantGateway = participantGateway;
        this.usedSparePartGateway = usedSparePartGateway;
    }

    @Override
    public void retrieveWorkFront() {
        List<WorkFrontRow> workFrontRowList = workOrderGateway
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

        output.displayWorkFrontList(workFrontRowList);

    }

    @Override
    public void retrieveClosedOrders(RequestClosedOrders request) {
        LocalDate date = request.startDate();
        List<WorkFrontRow> closedOrdersList = workOrderGateway
                .getClosedWorkList(date, StatusEnum.CLOSED).stream()
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

    @Override
    public void deleteWorkOrder(RequestDeleteWorkOrder request) {
        if (request.workOrderId() <=0 ) {
            output.displayError(AdminMainEnum.DELETE_WORK_ORDER_ERROR);
            return;
        }

        if (!deleteAssociatedInformation(request.workOrderId())) {
            output.displayError(AdminMainEnum.DELETE_WORK_ORDER_ERROR);
            return;
        }

        if (workOrderGateway.deleteWorkOrder(request.workOrderId())) {
            output.displaySuccess(AdminMainEnum.DELETE_WORK_ORDER_SUCCESS);
            retrieveWorkFront();

        } else {
            output.displayError(AdminMainEnum.DELETE_WORK_ORDER_ERROR);
        }
    }

    // Auxiliary methods

    boolean deleteAssociatedInformation(long workOrderId){
        if (assignedCategoryGateway == null || participantGateway == null || usedSparePartGateway == null) {
            return false;
        }

        List<AssignedCategory> assignedCategories = assignedCategoryGateway.getCategoriesByWorkOrder(workOrderId);
        for (AssignedCategory assignedCategory : assignedCategories) {
            if (!assignedCategoryGateway.deleteAssignedCategory(assignedCategory)) {
                return false;
            }
        }

        List<Participant> participants = participantGateway.getParticipantsByWorkOrder(workOrderId);
        for (Participant participant : participants) {
            if (!participantGateway.deleteParticipant(participant)) {
                return false;
            }
        }

        List<UsedSparePart> usedSpareParts = usedSparePartGateway.getUsedSpareByWorkOrder(workOrderId);
        for (UsedSparePart usedSparePart : usedSpareParts) {
            if (!usedSparePartGateway.deleteUsedSparePart(usedSparePart)) {
                return false;
            }
        }

        return true;
    }
}
