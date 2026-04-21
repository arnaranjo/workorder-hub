package com.workorderhub.core.entity;

import java.time.LocalDateTime;

/**
 * Represents the record of creation or edits of documents, work orders and work permits.
 */
public class WorkLogInfo {

    private int logId;
    private LocalDateTime logDate;
    private String logComment;
    private int userId;
    private long workOrderId;
    private int workPermitId;

    public WorkLogInfo() {
    }

    public WorkLogInfo(
            int logId,
            LocalDateTime logDate,
            String logComment,
            int userId,
            long workOrderId,
            int workPermitId
    ) {
        this.logId = logId;
        this.logDate = logDate;
        this.logComment = logComment;
        this.userId = userId;
        this.workOrderId = workOrderId;
        this.workPermitId = workPermitId;

    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public String getLogComment() {
        return logComment;
    }

    public void setLogComment(String logComment) {
        this.logComment = logComment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getWorkPermitId() {
        return workPermitId;
    }

    public void setWorkPermitId(int workPermitId) {
        this.workPermitId = workPermitId;
    }
}
