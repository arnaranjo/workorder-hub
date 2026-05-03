package com.workorderhub.core.caseuse.technician;

import com.workorderhub.core.entity.AssignedCategory;
import com.workorderhub.core.entity.Participant;
import com.workorderhub.core.entity.UsedSparePart;
import com.workorderhub.core.entity.WorkOrderElement;
import com.workorderhub.core.gateway.*;

import java.util.List;

public class WorkOrderCheckInteractor implements WorkOrderCheckInput {

    private WorkOrderCheckOutput output;
    private WorkOrderGateway workOrderGateway;
    private ParticipantGateway participantGateway;
    private UsedSparePartGateway usedSparePartGateway;
    private AssignedCategoryGateway assignedCategoryGateway;
    private SparePartGateway sparePartGateway;

    public WorkOrderCheckInteractor(
            WorkOrderCheckOutput output,
            WorkOrderGateway workOrderGateway,
            ParticipantGateway participantGateway,
            UsedSparePartGateway usedSparePartGateway,
            AssignedCategoryGateway assignedCategoryGateway,
            SparePartGateway sparePartGateway
    ) {
        this.output = output;
        this.workOrderGateway = workOrderGateway;
        this.participantGateway = participantGateway;
        this.usedSparePartGateway = usedSparePartGateway;
        this.assignedCategoryGateway = assignedCategoryGateway;
        this.sparePartGateway = sparePartGateway;
    }

    @Override
    public void loadWorkOrderData(RequestLoadWorkOrder request) {

        if (request.workOrderId() <= 0) {
            return;
        }

        WorkOrderElement workOrderElement = workOrderGateway.getWorkFrontElement(request.workOrderId());

        if (workOrderElement == null) {
            return;
        }

        List<AssignedCategory> categoryList = assignedCategoryGateway.getCategoriesByWorkOrder(request.workOrderId());
        List<String> categoryNameList = categoryList.stream()
                .map(AssignedCategory -> {
                    if (AssignedCategory.getCategoryName() != null) {
                        return AssignedCategory.getCategoryName();
                    } else {
                        return "Uncategorized";
                    }
                })
                .toList();

        ResponseWorkOrderData responseWorkOrderData = new ResponseWorkOrderData(
                workOrderElement.getWorkOrder().getDescription(),
                workOrderElement.getCurrentStatus().getOrderStatus(),
                workOrderElement.getWorkOrder().getStartDate(),
                workOrderElement.getWorkOrder().getEndDate(),
                categoryNameList
        );
        output.displayWorkOrderData(responseWorkOrderData);

        ResponsePlantElement responsePlantElement = new ResponsePlantElement(
                workOrderElement.getPlantElement().getElementId(),
                workOrderElement.getPlantElement().getElementTag(),
                workOrderElement.getPlantElement().getElementDescription(),
                workOrderElement.getPlantElement().getElementLocation(),
                workOrderElement.getPlantElement().getInspectionDate(),
                workOrderElement.getPlantElement().getInspectionFrequency()
        );
        output.displayPlantElement(responsePlantElement);

        ResponseHolderInfo responseHolderInfo = new ResponseHolderInfo(
                workOrderElement.getUser().getUserId(),
                workOrderElement.getUser().getUserName(),
                workOrderElement.getUser().getUserPhoneNumber(),
                workOrderElement.getUser().getUserEmail()
        );
        output.displayHolderInfo(responseHolderInfo);

        if (workOrderElement.getWorkProcedure() != null) {
            ResponseProcedureInfo responseProcedureInfo = new ResponseProcedureInfo(
                    workOrderElement.getWorkProcedure().getProcedureId(),
                    workOrderElement.getWorkProcedure().getDocumentCode(),
                    workOrderElement.getWorkProcedure().getDocumentName()
            );
            output.displayProcedureInfo(responseProcedureInfo);
        }

        if (workOrderElement.getWorkPermit() != null) {
            boolean isLoto = workOrderElement.getLotoProcedure() == null;

            ResponseWorkPermit responseWorkPermit = new ResponseWorkPermit(
                    workOrderElement.getWorkPermit().getDescription(),
                    workOrderElement.getWorkPermit().getLockoutDeviceId(),
                    isLoto ? null : workOrderElement.getLotoProcedure().getDocumentCode(),
                    isLoto ? null : workOrderElement.getLotoProcedure().getDocumentName()
            );
            output.displayWorkPermit(responseWorkPermit);
        }

        List<Participant> participants = participantGateway.getParticipantsByWorkOrder(request.workOrderId());
        if (participants != null && !participants.isEmpty()) {
            List<ResponseParticipant> responseList = participants.stream()
                    .map(participant -> new ResponseParticipant(
                            participant.getUserId(),
                            participant.getEmployeeName(),
                            participant.getEmployeeEmail(),
                            participant.getEmployeePhoneNumber()
                    )).toList();
            output.displayParticipantList(responseList);
        }

        // Load the used spare parts for the work order and display them to the technician
        loadUsedSpareParts(request.workOrderId());
    }

    @Override
    public void setTechnicianComment(RequestSetComment request) {
        if (request.workOrderId() <= 0) {
            return;
        }

        if (request.comment() == null || request.comment().isBlank()) {
            return;
        }

        if (output.requestConfirmation(WorkOrderCheckEnum.REQUEST_ADD_COMMENT)) {
            if (workOrderGateway.setWorkOrderComment(request.workOrderId(), request.comment())) {
                output.displaySuccess(WorkOrderCheckEnum.ADDED_COMMENT_SUCCESS);
            }
        }
    }

    @Override
    public void updateUsedSparePart(RequestUpdateUsedSparePart request) {

        if (request.workOrderId() <= 0 || request.sparePartId() <= 0 ||
                request.numberFinallyUsed() < 0 || request.currentStock() < 0) {
            return;
        }

        int finallyUsed = request.numberFinallyUsed();
        int alreadyUsed = request.numberToUse();
        int currentStock = request.currentStock();

        // Ensure that the finally used number does not exceed the sum of current stock and already used
        if (finallyUsed > currentStock + alreadyUsed) {
            finallyUsed = currentStock + alreadyUsed;
        }

        // Calculate the new stock based on the difference between already used and finally used
        int stockDifference = alreadyUsed - finallyUsed;
        int newStock = currentStock + stockDifference;

        UsedSparePart usedSparePart = new UsedSparePart(
                request.workOrderId(),
                request.sparePartId(),
                finallyUsed,
                newStock
        );

        if (usedSparePartGateway.updateUsedSparePart(usedSparePart) &&
                sparePartGateway.updateSparePartStock(request.sparePartId(), newStock)) {

            output.displaySuccess(WorkOrderCheckEnum.UPDATED_SPARE_PARTS_SUCCESS);
        }

        loadUsedSpareParts(request.workOrderId());
    }

    /**
     * Load the used spare parts for a specific work order and display them to the technician.
     * @param workOrderId The ID of the work order for which to load the used spare parts.
     */
    private void loadUsedSpareParts(long workOrderId){
        List<UsedSparePart> usedSpareParts = usedSparePartGateway.getUsedSpareByWorkOrder(workOrderId);
        if (usedSpareParts != null && !usedSpareParts.isEmpty()) {
            List<ResponseUsedSparePart> responseList = usedSpareParts.stream()
                    .map(usedSparePart -> new ResponseUsedSparePart(
                            usedSparePart.getWorkOrderId(),
                            usedSparePart.getSparePartId(),
                            usedSparePart.getSelectedNumber(),
                            usedSparePart.getCurrentStock(),
                            usedSparePart.getSpareName(),
                            usedSparePart.getSpareNumber()
                    )).toList();
            output.displayUsedSparePartList(responseList);
        }
    }
}
