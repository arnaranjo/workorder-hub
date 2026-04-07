package com.workorderhub.provider.models;

import java.time.LocalDate;

/**
 * Model class representing a work order in the system, used for displaying work order information in a table format.
 */
public class WorkFrontModel {

    private long workOrderId;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String plantElementTag;
    private String workProcedureCode;
    private String lockDeviceId;
    private String lotoProcedureCode;
    private String holderName;
    private String status;

    public WorkFrontModel(){}

    public WorkFrontModel(
            long workOrderId,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            String plantElementTag,
            String workProcedureCode,
            String lockDeviceId,
            String lotoProcedureCode,
            String holderName,
            String status
    ) {
        this.workOrderId = workOrderId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plantElementTag = plantElementTag;
        this.workProcedureCode = workProcedureCode;
        this.lockDeviceId = lockDeviceId;
        this.lotoProcedureCode = lotoProcedureCode;
        this.holderName = holderName;
        this.status = status;
    }

    public long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPlantElementTag() {
        return plantElementTag;
    }

    public void setPlantElementTag(String plantElementTag) {
        this.plantElementTag = plantElementTag;
    }

    public String getWorkProcedureCode() {
        return workProcedureCode;
    }

    public void setWorkProcedureCode(String workProcedureCode) {
        this.workProcedureCode = workProcedureCode;
    }

    public String getLockDeviceId() {
        return lockDeviceId;
    }

    public void setLockDeviceId(String lockDeviceId) {
        this.lockDeviceId = lockDeviceId;
    }

    public String getLotoProcedureCode() {
        return lotoProcedureCode;
    }

    public void setLotoProcedureCode(String lotoProcedureCode) {
        this.lotoProcedureCode = lotoProcedureCode;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
