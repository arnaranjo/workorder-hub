package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.gateway.WorkOrderGateway;

public class WorkOrderInteractor implements WorkOrderInput {

    private final WorkOrderMainOutput mainOutput;
    private final WorkOrderDataOutput dataOutput;
    private final WorkOrderPeriodOutput periodOutput;
    private final WorkOrderProcedureOutput procedureOutput;
    private final WorkOrderPermitOutput permitOutput;

    private final WorkOrderGateway workOrderGateway;

    private WorkOrderInteractor(Builder builder) {
        this.mainOutput = builder.mainOutput;
        this.dataOutput = builder.dataOutput;
        this.procedureOutput = builder.procedureOutput;
        this.permitOutput = builder.permitOutput;
        this.workOrderGateway = builder.workOrderGateway;
        this.periodOutput = builder.periodOutput;
    }

    //Methods



    public static class Builder {
        private WorkOrderMainOutput mainOutput;
        private WorkOrderDataOutput dataOutput;
        private WorkOrderProcedureOutput procedureOutput;
        private WorkOrderPeriodOutput periodOutput;
        private WorkOrderPermitOutput permitOutput;
        private WorkOrderGateway workOrderGateway;

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

        public WorkOrderInteractor build() {
            return new WorkOrderInteractor(this);

        }
    }
}

