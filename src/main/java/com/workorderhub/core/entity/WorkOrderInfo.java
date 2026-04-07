package com.workorderhub.core.entity;

import java.time.LocalDate;


/**
 * Represents the work order document.
 * Includes details such as description, comments, start and end dates, and various identifiers required for
 * managing the work order.
 */
public class WorkOrderInfo {

    private long workOrderId;
    private String description;
    private String comments;
    private LocalDate startDate;
    private LocalDate endDate;
    private int holderId;
    private int statusId;
    private int plantElementId;
    private int workProcedureId;
    private int workPermitId;

    public WorkOrderInfo() {
    }

    public WorkOrderInfo(
            long workOrderId,
            String description,
            String comments,
            LocalDate startDate,
            LocalDate endDate,
            int holderId,
            int statusId,
            int plantElementId,
            int workProcedureId,
            int workPermitId
    ) {
        this.workOrderId = workOrderId;
        this.description = description;
        this.comments = comments;
        this.startDate = startDate;
        this.endDate = endDate;
        this.holderId = holderId;
        this.statusId = statusId;
        this.plantElementId = plantElementId;
        this.workProcedureId = workProcedureId;
        this.workPermitId = workPermitId;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public int getHolderId() {
        return holderId;
    }

    public void setHolderId(int holderId) {
        this.holderId = holderId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getPlantElementId() {
        return plantElementId;
    }

    public void setPlantElementId(int plantElementId) {
        this.plantElementId = plantElementId;
    }

    public int getWorkProcedureId() {
        return workProcedureId;
    }

    public void setWorkProcedureId(int workProcedureId) {
        this.workProcedureId = workProcedureId;
    }

    public int getWorkPermitId() {
        return workPermitId;
    }

    public void setWorkPermitId(int workPermitId) {
        this.workPermitId = workPermitId;
    }
}
