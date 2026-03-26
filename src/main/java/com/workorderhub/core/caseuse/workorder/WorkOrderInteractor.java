package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.entity.Category;
import com.workorderhub.core.gateway.CategoryGateway;
import com.workorderhub.core.gateway.UserGateway;
import com.workorderhub.core.gateway.WorkOrderGateway;

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

    private WorkOrderInteractor(Builder builder) {
        this.mainOutput = builder.mainOutput;
        this.dataOutput = builder.dataOutput;
        this.procedureOutput = builder.procedureOutput;
        this.permitOutput = builder.permitOutput;
        this.workOrderGateway = builder.workOrderGateway;
        this.periodOutput = builder.periodOutput;
        this.categoryGateway = builder.categoryGateway;
        this.userGateway = builder.userGateway;
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

    }

    @Override
    public void retrieveHoldersList() {

    }

    //Methods


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

        public WorkOrderInteractor build() {
            return new WorkOrderInteractor(this);

        }
    }
}

