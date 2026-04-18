package com.workorderhub.core.caseuse.workorder;

import java.time.LocalDate;

public record RequestUpdateWorkOrder(

        String description,
        Integer plantElementId,
        Integer holderId,
        ValidPeriod validPeriod,
        SelectedProcedure selectedProcedure,
        WorkPermit workPermit

) {

    // Nested records

    private record ValidPeriod(
            LocalDate startDate,
            LocalDate endDate
    ) {
    }

    private record SelectedProcedure(
            Integer procedureId
    ) {
    }

    private record WorkPermit(
            String description,
            String lockDevices,
            Integer lotoProcedureId
    ) {
    }

    // Getters for nested records

    public LocalDate getStartDate() {
        return validPeriod.startDate();
    }

    public LocalDate getEndDate() {
        return validPeriod.endDate();
    }

    public Integer getProcedureId() {
        return selectedProcedure.procedureId();
    }

    public String getWorkPermitDescription() {
        return workPermit.description();
    }

    public String getWorkPermitLockDevices() {
        return workPermit.lockDevices();
    }

    public Integer getWorkPermitLotoProcedureId() {
        return workPermit.lotoProcedureId();
    }

    // Builder

    public static class Builder {

        private String description;
        private Integer plantElementId;
        private Integer holderId;
        private ValidPeriod validPeriod;
        private SelectedProcedure selectedProcedure;
        private WorkPermit workPermit;

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPlantElementId(Integer plantElementId) {
            this.plantElementId = plantElementId;
            return this;
        }

        public Builder withHolderId(Integer holderId) {
            this.holderId = holderId;
            return this;
        }

        public Builder withValidPeriod(LocalDate startDate, LocalDate endDate) {
            this.validPeriod = new ValidPeriod(startDate, endDate);
            return this;
        }

        public Builder withSelectedProcedure(Integer procedureId) {
            this.selectedProcedure = new SelectedProcedure(procedureId);
            return this;
        }

        public Builder withWorkPermit(String description, String lockDevices, Integer lotoProcedureId) {
            this.workPermit = new WorkPermit(description, lockDevices, lotoProcedureId);
            return this;
        }

        public RequestUpdateWorkOrder build() {
            return new RequestUpdateWorkOrder(
                    description,
                    plantElementId,
                    holderId,
                    validPeriod,
                    selectedProcedure,
                    workPermit
            );
        }
    }
}
