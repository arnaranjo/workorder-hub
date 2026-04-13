package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.core.entity.*;
import com.workorderhub.core.gateway.*;

import java.util.List;

public class WorkOrderInteractor implements WorkOrderInput {

    private final WorkOrderMainOutput mainOutput;
    private final WorkOrderDataOutput dataOutput;
    private final WorkOrderPeriodOutput periodOutput;
    private final WorkOrderProcedureOutput procedureOutput;
    private final WorkOrderPermitOutput permitOutput;

    private final WorkOrderGateway workOrderGateway;
    private final CategoryGateway categoryGateway;
    private final UserGateway userGateway;
    private final PlantElementGateway plantElementGateway;
    private final SparePartGateway sparePartGateway;
    private final WorkProcedureGateway workProcedureGateway;

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
    }

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
            categoryList.add(new Category("No categories available", ""));

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
                        ""
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

        } else{
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

        public WorkOrderInteractor build() {
            return new WorkOrderInteractor(this);

        }
    }
}
