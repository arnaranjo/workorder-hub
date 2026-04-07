package com.workorderhub.core.entity;

import java.time.LocalDate;

/**
 * Represents a complete work order element, including its main information, the assigned employee,
 * the related plant element, and all associated procedures and permits.
 * This class acts as an aggregated view that gathers all entities required to manage and display
 * a work order comprehensively.
 */
public class WorkOrderElement {
    private WorkOrderInfo workOrderInfo;
    private User user;
    private PlantElement plantElement;
    private WorkProcedure workProcedure;
    private WorkPermit workPermit;
    private LotoProcedure lotoProcedure;
    private Status currentStatus;

    private WorkOrderElement(Builder builder) {
        this.workOrderInfo = builder.workOrderInfo;
        this.user = builder.user;
        this.plantElement = builder.plantElement;
        this.workProcedure = builder.workProcedure;
        this.workPermit = builder.workPermit;
        this.lotoProcedure = builder.lotoProcedure;
        this.currentStatus = builder.status;
    }

    public WorkOrderInfo getWorkOrder() {
        return workOrderInfo;
    }

    public User getUser() {
        return user;
    }

    public PlantElement getPlantElement() {
        return plantElement;
    }

    public WorkProcedure getWorkProcedure() {
        return workProcedure;
    }

    public WorkPermit getWorkPermit() {
        return workPermit;
    }

    public LotoProcedure getLotoProcedure() {
        return lotoProcedure;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public static class Builder {

        private WorkOrderInfo workOrderInfo;
        private User user;
        private PlantElement plantElement;
        private WorkProcedure workProcedure;
        private WorkPermit workPermit;
        private LotoProcedure lotoProcedure;
        private Status status;

        public Builder withWorkOrder(WorkOrderInfo workOrderInfo) {
            this.workOrderInfo = workOrderInfo;
            return this;
        }

        public Builder withEmployee(User user) {
            this.user = user;
            return this;
        }

        public Builder withPlantElement(PlantElement plantElement) {
            this.plantElement = plantElement;
            return this;
        }

        public Builder withWorkProcedure(WorkProcedure workProcedure) {
            this.workProcedure = workProcedure;
            return this;
        }

        public Builder withWorkPermit(WorkPermit workPermit) {
            this.workPermit = workPermit;
            return this;
        }

        public Builder withLotoProcedure(LotoProcedure lotoProcedure) {
            this.lotoProcedure = lotoProcedure;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public WorkOrderElement build() {
            return new WorkOrderElement(this);
        }
    }
}
