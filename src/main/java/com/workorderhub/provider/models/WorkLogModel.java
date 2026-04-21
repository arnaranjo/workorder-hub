package com.workorderhub.provider.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkLogModel {

    private Integer logId;
    private LocalDateTime logDate;
    private String LogComment;
    private Long workOrderId;
    private String userName;
    private LocalDate workOrderStartDate;
    private LocalDate workOrderEndDate;
    private String holderName;
    private Integer workPermitId;

    public WorkLogModel() {}

    public WorkLogModel(
            Integer logId,
            LocalDateTime logDate,
            String logComment,
            Long workOrderId,
            String userName,
            LocalDate workOrderStartDate,
            LocalDate workOrderEndDate,
            String holderName,
            Integer workPermitId
    ) {
        this.logId = logId;
        this.logDate = logDate;
        this.LogComment = logComment;
        this.workOrderId = workOrderId;
        this.userName = userName;
        this.workOrderStartDate = workOrderStartDate;
        this.workOrderEndDate = workOrderEndDate;
        this.holderName = holderName;
        this.workPermitId = workPermitId;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public String getLogComment() {
        return LogComment;
    }

    public void setLogComment(String logComment) {
        this.LogComment = logComment;
    }

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getWorkOrderStartDate() {
        return workOrderStartDate;
    }

    public void setWorkOrderStartDate(LocalDate workOrderStartDate) {
        this.workOrderStartDate = workOrderStartDate;
    }

    public LocalDate getWorkOrderEndDate() {
        return workOrderEndDate;
    }

    public void setWorkOrderEndDate(LocalDate workOrderEndDate) {
        this.workOrderEndDate = workOrderEndDate;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public Integer getWorkPermitId() {
        return workPermitId;
    }

    public void setWorkPermitId(Integer workPermitId) {
        this.workPermitId = workPermitId;
    }
}
