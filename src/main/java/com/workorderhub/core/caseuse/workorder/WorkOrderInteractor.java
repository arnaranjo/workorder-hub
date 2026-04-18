package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.core.entity.*;
import com.workorderhub.core.gateway.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class WorkOrderInteractor implements WorkOrderInput {

    // Output interfaces
    private final WorkOrderMainOutput mainOutput;
    private final WorkOrderDataOutput dataOutput;
    private final WorkOrderPeriodOutput periodOutput;
    private final WorkOrderProcedureOutput procedureOutput;
    private final WorkOrderPermitOutput permitOutput;

    // Gateways
    private final WorkOrderGateway workOrderGateway;
    private final CategoryGateway categoryGateway;
    private final UserGateway userGateway;
    private final PlantElementGateway plantElementGateway;
    private final SparePartGateway sparePartGateway;
    private final WorkProcedureGateway workProcedureGateway;
    private final LotoProcedureGateway lotoProcedureGateway;
    private final WorkPermitGateway workPermitGateway;
    private final StatusGateway statusGateway;
    private final AssignedCategoryGateway assignedCategoryGateway;
    private final ParticipantGateway participantGateway;
    private final UsedSparePartGateway usedSparePartGateway;

    private WorkOrderInteractor(Builder builder) {

        this.mainOutput = builder.mainOutput;
        this.dataOutput = builder.dataOutput;
        this.procedureOutput = builder.procedureOutput;
        this.permitOutput = builder.permitOutput;
        this.workOrderGateway = builder.workOrderGateway;
        this.periodOutput = builder.periodOutput;

        this.categoryGateway = builder.categoryGateway;
        this.userGateway = builder.userGateway;
        this.plantElementGateway = builder.plantElementGateway;
        this.sparePartGateway = builder.sparePartGateway;
        this.workProcedureGateway = builder.workProcedureGateway;
        this.lotoProcedureGateway = builder.lotoProcedureGateway;
        this.workPermitGateway = builder.workPermitGateway;
        this.statusGateway = builder.statusGateway;
        this.assignedCategoryGateway = builder.assignedCategoryGateway;
        this.participantGateway = builder.participantGateway;
        this.usedSparePartGateway = builder.usedSparePartGateway;
    }

    // Interface methods

    @Override
    public void toggleValidPeriodDisable() {
        mainOutput.toggleValidPeriodSelection();
    }

    @Override
    public void toggleProcedureSelectionDisable() {
        mainOutput.toggleProcedureSelection();
    }

    @Override
    public void togglePermitSelectionDisable() {
        mainOutput.togglePermitSelection();
    }

    @Override
    public void retrieveWorkOrderCategories() {
        List<Category> categoryList = categoryGateway.getCategoryList();

        if (categoryList.isEmpty()) {
            categoryList.add(new Category("No categories available", null));

        } else {
            dataOutput.setCategoryList(categoryList);
        }
    }

    @Override
    public void retrieveTechnicianList() {
        List<User> technicianList = userGateway.getUsersByRole(UserRoleEnum.TECHNICIAN);
        if (technicianList.isEmpty()) {
            dataOutput.displayError(WorkOrderEnum.NO_TECHNICIANS);

        } else {
            dataOutput.displayTechnicianList(technicianList);
        }
    }

    @Override
    public void retrieveHoldersList() {
        List<User> holderList = userGateway.getUsersByRole(UserRoleEnum.SUPERVISOR);
        if (holderList.isEmpty()) {
            dataOutput.displayError(WorkOrderEnum.NO_SUPERVISORS);

        } else {
            dataOutput.displayHolderList(holderList, UserRoleEnum.SUPERVISOR.getRoleName());
        }
    }

    @Override
    public void retrieveSparePartsList() {
        List<SparePartRow> sparePartRowList = sparePartGateway.getSparePartList().stream()
                .map(sparePart -> new SparePartRow(
                        sparePart.getSpareId(),
                        sparePart.getSpareName(),
                        sparePart.getSpareNumber(),
                        sparePart.getSpareDescription(),
                        sparePart.getSpareStock(),
                        null
                )).toList();

        dataOutput.displaySparePartsList(sparePartRowList);
    }

    @Override
    public void getPlantElement(RequestPlantElement request) {
        PlantElement plantElement = plantElementGateway.GetPlantElementByTag(
                request.elementTag()
        );

        if (plantElement != null) {
            ResponsePlantElement response =
                    new ResponsePlantElement(
                            plantElement.getElementId(),
                            plantElement.getElementTag(),
                            plantElement.getElementDescription(),
                            plantElement.getElementLocation(),
                            plantElement.getInspectionDate(),
                            plantElement.getInspectionFrequency()
                    );
            dataOutput.displayPlantElementInfo(response);

        } else {
            dataOutput.displayError(WorkOrderEnum.NO_PLANT_ELEMENT);

        }
    }

    @Override
    public void retrieveWorkFrontList() {
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

        periodOutput.displayWorkFrontList(workFrontRowList);
    }

    @Override
    public void retrieveWorkProcedureList() {
        List<WorkProcedureRow> workProcedureRowList = workProcedureGateway.getWorkProcedureList().stream()
                .map(workProcedure -> new WorkProcedureRow(
                        workProcedure.getProcedureId(),
                        workProcedure.getDocumentCode(),
                        workProcedure.getDocumentName()
                )).toList();

        procedureOutput.retrieveWorkProcedureList(workProcedureRowList);
    }

    @Override
    public void retrieveLotoProcedureList() {
        List<LotoProcedureRow> lotoProcedureRowList = lotoProcedureGateway.getLotoProceduresList().stream()
                .map(lotoProcedure -> new LotoProcedureRow(
                        lotoProcedure.getProcedureId(),
                        lotoProcedure.getDocumentCode(),
                        lotoProcedure.getDocumentName()
                )).toList();
        permitOutput.displayLotoProcedureList(lotoProcedureRowList);
    }

    @Override
    public void createWorkOrder(
            RequestNewWorkOrder request,
            List<RequestAssignCategory> categoryList,
            List<RequestParticipants> participantsList,
            List<RequestUseSpareParts> usedSparePartList
    ) {
        if (request.plantElementId() == null){
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_PLANT_ELEMENT_ERROR);

        } else if (request.description().isBlank()) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_DESCRIPTION_ERROR);

        } else if (categoryList == null || categoryList.isEmpty()) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CATEGORY_ERROR);

        } else if (request.holderId() == null) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_HOLDER_ERROR);

        } else if (request.getWorkPermitDescription().isBlank()) {
            dataOutput.displayError(WorkOrderEnum.WORK_PERMIT_DESCRIPTION_ERROR);

        } else {

            // Generate a work order ID using the current date and time.
            long workOrderId = createWorkOrderId();

            // If the user fill any of the fields in the "Work permit" section,
            // a work permit will be created and associated with the new work order.
            int workPermitId = createWorkPermit(
                    request.getWorkPermitDescription(),
                    request.getWorkPermitLockDevices(),
                    request.getWorkPermitLotoProcedureId()
            );

            //
            WorkOrderInfo newWorkOrder = new WorkOrderInfo(
                    workOrderId,
                    request.description(),
                    null,
                    request.getStartDate(),
                    request.getEndDate(),
                    request.holderId(),
                    getOpenStatus(),
                    request.plantElementId(),
                    request.getProcedureId() == null ? 0 : request.getProcedureId(),
                    workPermitId
            );

            if (dataOutput.requestConfirmation(WorkOrderEnum.REQUEST_WORK_ORDER_CREATION)) {

                //Work order creation and association of the categories, participants and used spare parts.
                if (
                        workOrderGateway.insertWorkOrder(newWorkOrder) &&
                        associateDataToWorkOrder(workOrderId, categoryList,participantsList, usedSparePartList)
                ) {
                    dataOutput.displaySuccess(WorkOrderEnum.WORK_ORDER_CREATED);

                } else {
                    dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);

                }
            }
        }
    }

    // Internal method

    /**
     * Generates an identification code for the work order using the current date.
     *
     * @return long number.
     */
    private long createWorkOrderId() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHms");
        String text = date.format(formatter);
        return Long.parseLong(text.trim());
    }

    /**
     * Gets the id of the status "open",
     * A new work order will have by default this status until a manager or technician edit the order.
     *
     * @return id of status "open"
     */
    private int getOpenStatus() {
        StatusEnum openStatus = StatusEnum.OPEN;
        List<Status> statusList = statusGateway.getStatusList();

        for (Status status : statusList) {
            if (status.getOrderStatus().equals(openStatus.GetStatus())) {
                return status.getStatusId();
            }
        }
        return 0;
    }

    /**
     * Creates a work permit if the user fill any of the fields in the "Work permit" section.
     * All the fields must be filled and correct.
     *
     * @param description     of the work permit, it is a mandatory field if the user fill any of the fields in the "Work permit" section.
     * @param lockDevices     associated with the work permit, it is an optional field.
     * @param lotoProcedureId associated with the work permit, it is an optional field.
     * @return the ID of the new work permit if it is created, or 0 if the work permit is not created.
     */
    private int createWorkPermit(String description, String lockDevices, Integer lotoProcedureId) {
        if (description == null && lockDevices == null && lotoProcedureId == null) {
            return 0;
        }

        WorkPermit newWorkPermit = new WorkPermit(
                0,
                description,
                lockDevices,
                lotoProcedureId == null ? 0 : lotoProcedureId
        );
        return workPermitGateway.insertWorkPermit(newWorkPermit);
    }

    /**
     * Associates the assigned categories, participants and used spare parts to the work order by inserting the corresponding records in the database.
     *
     * @param workOrderId      ID of the work order to associate the data with.
     * @param categoryList     List of categories assigned to the work order.
     * @param participantsList List of participants assigned to the work order.
     * @param sparePartList    List of spare parts used in the work order.
     * @return true if the data is successfully associated with the work order, false otherwise.
     */
    private boolean associateDataToWorkOrder(
            long workOrderId,
            List<RequestAssignCategory> categoryList,
            List<RequestParticipants> participantsList,
            List<RequestUseSpareParts> sparePartList
    ) {
        if (!sparePartList.isEmpty()) {

            List<UsedSparePart> usedSparePartList = sparePartList.stream()
                    .map(requestUseSpareParts -> new UsedSparePart(
                            workOrderId,
                            requestUseSpareParts.sparePartId(),
                            requestUseSpareParts.selectedNumber(),
                            requestUseSpareParts.spareName(),
                            requestUseSpareParts.spareNumber()
                    )).toList();

            if (!usedSparePartGateway.insertUsedSparePartBatch(usedSparePartList)) {
                return false;
            }
        }

        // The assigned categories and participants are mandatory.
        if (!categoryList.isEmpty() && !participantsList.isEmpty()) {

            List<AssignedCategory> assignedCategoryList = categoryList.stream()
                    .map(requestAssignCategory -> new AssignedCategory(
                            workOrderId,
                            requestAssignCategory.id(),
                            requestAssignCategory.name()
                    )).toList();

            List<Participant> participantList = participantsList.stream()
                    .map(requestParticipants -> new Participant(
                            workOrderId,
                            requestParticipants.userId(),
                            requestParticipants.employeeName(),
                            requestParticipants.employeeEmail(),
                            requestParticipants.employeePhoneNumber()
                    )).toList();

            return assignedCategoryGateway.insertAssignedCategoryBatch(assignedCategoryList)
                    && participantGateway.insertParticipantBatch(participantList);

        } else {
            return false;
        }
    }

    // Builder class

    /**
     * Builder class to create a WorkOrderInteractor instance with the necessary output interfaces and gateways.
     */
    public static class Builder {
        private WorkOrderMainOutput mainOutput;
        private WorkOrderDataOutput dataOutput;
        private WorkOrderProcedureOutput procedureOutput;
        private WorkOrderPeriodOutput periodOutput;
        private WorkOrderPermitOutput permitOutput;
        private WorkOrderGateway workOrderGateway;
        private CategoryGateway categoryGateway;
        private UserGateway userGateway;
        private PlantElementGateway plantElementGateway;
        private SparePartGateway sparePartGateway;
        private WorkProcedureGateway workProcedureGateway;
        private LotoProcedureGateway lotoProcedureGateway;
        private WorkPermitGateway workPermitGateway;
        private StatusGateway statusGateway;
        private AssignedCategoryGateway assignedCategoryGateway;
        private ParticipantGateway participantGateway;
        private UsedSparePartGateway usedSparePartGateway;

        public Builder() {
        }

        public Builder withMainOutput(WorkOrderMainOutput mainOutput) {
            this.mainOutput = mainOutput;
            return this;
        }

        public Builder withDataOutput(WorkOrderDataOutput dataOutput) {
            this.dataOutput = dataOutput;
            return this;
        }

        public Builder withPeriodOutput(WorkOrderPeriodOutput periodOutput) {
            this.periodOutput = periodOutput;
            return this;
        }

        public Builder withProcedureOutput(WorkOrderProcedureOutput procedureOutput) {
            this.procedureOutput = procedureOutput;
            return this;
        }

        public Builder withPermitOutput(WorkOrderPermitOutput permitOutput) {
            this.permitOutput = permitOutput;
            return this;
        }

        public Builder withWorkOrderGateway(WorkOrderGateway workOrderGateway) {
            this.workOrderGateway = workOrderGateway;
            return this;
        }

        public Builder withCategoryGateway(CategoryGateway categoryGateway) {
            this.categoryGateway = categoryGateway;
            return this;
        }

        public Builder withUserGateway(UserGateway userGateway) {
            this.userGateway = userGateway;
            return this;
        }

        public Builder withPlantElementGateway(PlantElementGateway plantElementGateway) {
            this.plantElementGateway = plantElementGateway;
            return this;
        }

        public Builder withSparePartGateway(SparePartGateway sparePartGateway) {
            this.sparePartGateway = sparePartGateway;
            return this;
        }

        public Builder withWorkProcedureGateway(WorkProcedureGateway workProcedureGateway) {
            this.workProcedureGateway = workProcedureGateway;
            return this;
        }

        public Builder withLotoProcedureGateway(LotoProcedureGateway lotoProcedureGateway) {
            this.lotoProcedureGateway = lotoProcedureGateway;
            return this;
        }

        public Builder withWorkPermitGateway(WorkPermitGateway workPermitGateway) {
            this.workPermitGateway = workPermitGateway;
            return this;
        }

        public Builder withStatusGateway(StatusGateway statusGateway) {
            this.statusGateway = statusGateway;
            return this;
        }

        public Builder withAssignedCategoryGateway(AssignedCategoryGateway assignedCategoryGateway) {
            this.assignedCategoryGateway = assignedCategoryGateway;
            return this;
        }

        public Builder withParticipantGateway(ParticipantGateway participantGateway) {
            this.participantGateway = participantGateway;
            return this;
        }

        public Builder withUsedSparePartGateway(UsedSparePartGateway usedSparePartGateway) {
            this.usedSparePartGateway = usedSparePartGateway;
            return this;
        }

        public WorkOrderInteractor build() {
            return new WorkOrderInteractor(this);

        }
    }
}
