package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.core.entity.*;
import com.workorderhub.core.gateway.*;
import com.workorderhub.infrastructure.common.AppState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        if (request.plantElementId() == null) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_PLANT_ELEMENT_ERROR);
            return;
        }
        if (request.description() == null || request.description().isBlank()) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_DESCRIPTION_ERROR);
            return;
        }
        if (categoryList == null || categoryList.isEmpty()) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CATEGORY_ERROR);
            return;
        }
        if (request.holderId() == null) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_HOLDER_ERROR);
            return;
        }

        String permitDescription = request.getWorkPermitDescription();
        String permitLockDevices = request.getWorkPermitLockDevices();
        Integer permitLotoProcedureId = request.getWorkPermitLotoProcedureId();
        boolean hasRequestedPermit = hasWorkPermitData(permitDescription, permitLockDevices, permitLotoProcedureId);

        if (hasRequestedPermit && !hasText(permitDescription)) {
            dataOutput.displayError(WorkOrderEnum.WORK_PERMIT_DESCRIPTION_ERROR);
            return;
        }

        if (!dataOutput.requestConfirmation(WorkOrderEnum.REQUEST_WORK_ORDER_CREATION)) {
            return;
        }

        long workOrderId = createWorkOrderId();

        int workPermitId = 0;
        if (hasRequestedPermit) {
            workPermitId = createWorkPermit(permitDescription, permitLockDevices, permitLotoProcedureId);

            if (workPermitId == 0) {
                dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
                return;
            }
        }

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

        if (workOrderGateway.insertWorkOrder(newWorkOrder) &&
                associateDataToWorkOrder(workOrderId, categoryList, participantsList, usedSparePartList)) {

            ResponseNewWorkOrder response = new ResponseNewWorkOrder(workOrderId);
            dataOutput.displayConfirmation(WorkOrderEnum.WORK_ORDER_CREATED, response);
        } else {

            // Remove the work permit that was created if the work order creation fails.
            if (workPermitId != 0) {
                workPermitGateway.deleteWorkPermit(new WorkPermit(workPermitId, null, null, 0));
            }

            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
        }
    }

    @Override
    public void updateWorkOrder(
            RequestUpdateWorkOrder request,
            List<RequestAssignCategory> categoryList,
            List<RequestParticipants> participantsList,
            List<RequestUseSpareParts> usedSparePartList
    ) {
        long workOrderId = AppState.getInstance().getWorkOrderId();

        List<RequestAssignCategory> safeCategoryList = categoryList == null ? List.of() : categoryList;
        List<RequestParticipants> safeParticipantsList = participantsList == null ? List.of() : participantsList;
        List<RequestUseSpareParts> safeUsedSparePartList = usedSparePartList == null ? List.of() : usedSparePartList;

        if (workOrderId == 0) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
            return;
        }

        WorkOrderInfo currentWorkOrder = workOrderGateway.getWorkOrderById(workOrderId);
        if (currentWorkOrder == null) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
            return;
        }

        if (request.plantElementId() == null) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_PLANT_ELEMENT_ERROR);
            return;
        }
        if (request.description() == null || request.description().isBlank()) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_DESCRIPTION_ERROR);
            return;
        }
        if (safeCategoryList.isEmpty()) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CATEGORY_ERROR);
            return;
        }
        if (request.holderId() == null) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_HOLDER_ERROR);
            return;
        }

        String permitDescription = request.getWorkPermitDescription();
        String permitLockDevices = request.getWorkPermitLockDevices();
        Integer permitLotoProcedureId = request.getWorkPermitLotoProcedureId();
        boolean hasRequestedPermit = hasWorkPermitData(permitDescription, permitLockDevices, permitLotoProcedureId);

        if (hasRequestedPermit && !hasText(permitDescription)) {
            dataOutput.displayError(WorkOrderEnum.WORK_PERMIT_DESCRIPTION_ERROR);
            return;
        }

        // Work permit management

        int currentPermitId = currentWorkOrder.getWorkPermitId();
        int finalPermitId = currentPermitId;
        int permitIdToDelete = 0;
        int insertedPermitId = 0;

        if (hasRequestedPermit) {
            if (currentPermitId == 0) {
                insertedPermitId = createWorkPermit(
                        permitDescription,
                        permitLockDevices,
                        permitLotoProcedureId
                );

                if (insertedPermitId == 0) {
                    dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
                    return;
                }
                finalPermitId = insertedPermitId;

            } else {
                WorkPermit permitToUpdate = new WorkPermit(
                        currentPermitId,
                        permitDescription,
                        permitLockDevices,
                        permitLotoProcedureId == null ? 0 : permitLotoProcedureId
                );

                if (!workPermitGateway.updateWorkPermit(permitToUpdate)) {
                    dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
                    return;
                }
            }

        } else if (currentPermitId != 0) {
            finalPermitId = 0;
            permitIdToDelete = currentPermitId;
        }

        // Work order update

        WorkOrderInfo workOrderToUpdate = new WorkOrderInfo(
                workOrderId,
                request.description(),
                currentWorkOrder.getComments(),
                request.getStartDate(),
                request.getEndDate(),
                request.holderId(),
                currentWorkOrder.getStatusId(),
                request.plantElementId(),
                request.getProcedureId() == null ? 0 : request.getProcedureId(),
                finalPermitId
        );

        if (!workOrderGateway.updateWorkOrder(workOrderToUpdate)) {
            if (insertedPermitId != 0) {
                workPermitGateway.deleteWorkPermit(new WorkPermit(insertedPermitId, null, null, 0));
            }
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_UPDATE_ERROR);
            return;
        }

        if (!syncAssignedCategories(workOrderId, safeCategoryList) ||
                !syncParticipants(workOrderId, safeParticipantsList) ||
                !syncUsedSpareParts(workOrderId, safeUsedSparePartList)) {
            dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
            return;
        }

        if (permitIdToDelete != 0) {
            WorkPermit permitToDelete = new WorkPermit(permitIdToDelete, null, null, 0);
            if (!workPermitGateway.deleteWorkPermit(permitToDelete)) {
                dataOutput.displayError(WorkOrderEnum.WORK_ORDER_CREATION_ERROR);
            }
        }
        dataOutput.displayConfirmation(WorkOrderEnum.WORK_ORDER_UPDATED, null);
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

        // If the lists are null, they will be treated as empty lists to avoid null pointer exceptions.
        List<RequestAssignCategory> safeCategoryList = categoryList == null ? List.of() : categoryList;
        List<RequestParticipants> safeParticipantsList = participantsList == null ? List.of() : participantsList;
        List<RequestUseSpareParts> safeUsedSparePartList = sparePartList == null ? List.of() : sparePartList;

        List<UsedSparePart> usedSparePartList = safeUsedSparePartList.stream()
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

        // The assigned categories and participants are mandatory.
        if (safeCategoryList.isEmpty() || safeParticipantsList.isEmpty()) {
            return false;

        } else {
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
        }
    }

    /**
     * Synchronizes the assigned categories of a work order by comparing the current categories in the database
     * with the requested categories and performing the necessary insertions and deletions.
     *
     * @param workOrderId         ID of the work order to synchronize the categories for.
     * @param requestedCategories List of categories that should be assigned to the work order after synchronization.
     * @return true if the synchronization is successful, false otherwise.
     */
    private boolean syncAssignedCategories(long workOrderId, List<RequestAssignCategory> requestedCategories) {

        List<AssignedCategory> currentCategories = assignedCategoryGateway.getCategoriesByWorkOrder(workOrderId);

        // A linked hash map is used to maintain the order of the categories as they are displayed in the UI.
        Map<Integer, RequestAssignCategory> requestedById = new LinkedHashMap<>();
        for (RequestAssignCategory category : requestedCategories) {
            requestedById.put(category.id(), category);
        }

        Map<Integer, AssignedCategory> currentById = new LinkedHashMap<>();
        for (AssignedCategory category : currentCategories) {
            currentById.put(category.getCategoryId(), category);
        }

        // Delete the categories that are currently assigned to the work order but are not in the requested categories list.
        for (AssignedCategory currentCategory : currentCategories) {
            if (!requestedById.containsKey(currentCategory.getCategoryId()) &&
                    !assignedCategoryGateway.deleteAssignedCategory(currentCategory)) {
                return false;
            }
        }

        // Insert the categories that are in the requested categories list but are not currently assigned to the work order.
        for (RequestAssignCategory requestCategory : requestedById.values()) {
            if (!currentById.containsKey(requestCategory.id())) {
                AssignedCategory newCategory = new AssignedCategory(
                        workOrderId,
                        requestCategory.id(),
                        requestCategory.name()
                );

                if (!assignedCategoryGateway.insertAssignedCategory(newCategory)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Synchronizes the participants of a work order by comparing the current participants in the database
     *
     * @param workOrderId           ID of the work order to synchronize the participants for.
     * @param requestedParticipants List of participants that should be assigned to the work order after synchronization.
     * @return true if the synchronization is successful, false otherwise.
     */
    private boolean syncParticipants(long workOrderId, List<RequestParticipants> requestedParticipants) {

        List<Participant> currentParticipants = participantGateway.getParticipantsByWorkOrder(workOrderId);

        Map<Integer, RequestParticipants> requestedById = new LinkedHashMap<>();
        for (RequestParticipants participant : requestedParticipants) {
            requestedById.put(participant.userId(), participant);
        }

        Map<Integer, Participant> currentById = new LinkedHashMap<>();
        for (Participant participant : currentParticipants) {
            currentById.put(participant.getUserId(), participant);
        }

        for (Participant currentParticipant : currentParticipants) {
            if (!requestedById.containsKey(currentParticipant.getUserId()) &&
                    !participantGateway.deleteParticipant(currentParticipant)) {
                return false;
            }
        }

        for (RequestParticipants requestParticipant : requestedById.values()) {
            if (!currentById.containsKey(requestParticipant.userId())) {
                Participant newParticipant = new Participant(
                        workOrderId,
                        requestParticipant.userId(),
                        requestParticipant.employeeName(),
                        requestParticipant.employeeEmail(),
                        requestParticipant.employeePhoneNumber()
                );

                if (!participantGateway.insertParticipant(newParticipant)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Synchronizes the used spare parts of a work order by comparing the current used spare parts in the database
     *
     * @param workOrderId         ID of the work order to synchronize the used spare parts for.
     * @param requestedSpareParts List of used spare parts that should be assigned to the work order after synchronization.
     * @return true if the synchronization is successful, false otherwise.
     */
    private boolean syncUsedSpareParts(long workOrderId, List<RequestUseSpareParts> requestedSpareParts) {

        List<UsedSparePart> currentSpareParts = usedSparePartGateway.getUsedSpareByWorkOrder(workOrderId);

        Map<Integer, RequestUseSpareParts> requestedById = new LinkedHashMap<>();
        for (RequestUseSpareParts sparePart : requestedSpareParts) {
            requestedById.put(sparePart.sparePartId(), sparePart);
        }

        Map<Integer, UsedSparePart> currentById = new LinkedHashMap<>();
        for (UsedSparePart sparePart : currentSpareParts) {
            currentById.put(sparePart.getSparePartId(), sparePart);
        }

        for (UsedSparePart currentSparePart : currentSpareParts) {
            if (!requestedById.containsKey(currentSparePart.getSparePartId()) &&
                    !usedSparePartGateway.deleteUsedSparePart(currentSparePart)) {
                return false;
            }
        }

        for (RequestUseSpareParts requestSparePart : requestedById.values()) {
            UsedSparePart currentSparePart = currentById.get(requestSparePart.sparePartId());

            if (currentSparePart == null) {
                UsedSparePart newSparePart = new UsedSparePart(
                        workOrderId,
                        requestSparePart.sparePartId(),
                        requestSparePart.selectedNumber(),
                        requestSparePart.spareName(),
                        requestSparePart.spareNumber()
                );

                if (!usedSparePartGateway.insertUsedSparePart(newSparePart)) {
                    return false;
                }

            } else if (currentSparePart.getSelectedNumber() != requestSparePart.selectedNumber()) {
                UsedSparePart updatedSparePart = new UsedSparePart(
                        workOrderId,
                        requestSparePart.sparePartId(),
                        requestSparePart.selectedNumber(),
                        requestSparePart.spareName(),
                        requestSparePart.spareNumber()
                );

                if (!usedSparePartGateway.updateUsedSparePart(updatedSparePart)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the user has filled any of the fields in the "Work permit" section of the form.
     *
     * @param description     of the work permit, it is a mandatory field if the user fill any of the fields in the "Work permit" section.
     * @param lockDevices     associated with the work permit, it is an optional field.
     * @param lotoProcedureId associated with the work permit, it is an optional field.
     * @return true if the user has filled any of the fields in the "Work permit" section, false otherwise.
     */
    private boolean hasWorkPermitData(String description, String lockDevices, Integer lotoProcedureId) {
        return hasText(description) || hasText(lockDevices) || lotoProcedureId != null;
    }

    /**
     * Checks if a string value is not null and not blank.
     *
     * @param value to check.
     * @return true if the value is not null and not blank, false otherwise.
     */
    private boolean hasText(String value) {
        return value != null && !value.isBlank();
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
