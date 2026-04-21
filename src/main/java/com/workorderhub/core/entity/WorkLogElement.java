package com.workorderhub.core.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Represents a complete work log element, with all the associated information about the work order,
 * user and work permit.
 */
public class WorkLogElement {

    private WorkLogInfo workLogInfo;
    private User user;
    private WorkOrderInfo workOrderInfo;
    private User holder;
    private WorkPermit workPermit;

    private WorkLogElement(Builder builder){
        this.workLogInfo = builder.workLogInfo;
        this.user = builder.employee;
        this.workOrderInfo = builder.workOrderInfo;
        this.holder = builder.holder;
        this.workPermit = builder.workPermit;
    }

    public WorkLogInfo getWorkLogInfo() {
        return workLogInfo;
    }

    public User getUser() {
        return user;
    }

    public WorkOrderInfo getWorkOrderInfo() {
        return workOrderInfo;
    }

    public User getHolder() {
        return holder;
    }

    public WorkPermit getWorkPermit() {
        return workPermit;
    }


    public int getLogId(){
        return this.workLogInfo.getLogId();
    }

    public LocalDateTime getLogDate(){
        return this.workLogInfo.getLogDate();
    }

    public String getLogComment(){
        return this.workLogInfo.getLogComment();
    }

    public long getWorkOrderId(){
        return this.workLogInfo.getWorkOrderId();
    }

    public String getUserName(){
        return this.user.getUserName();
    }

    public LocalDate getWorkOrderStartDate(){
        return this.workOrderInfo.getStartDate();
    }

    public LocalDate getWorkOrderEndDate(){
        return this.workOrderInfo.getEndDate();
    }

    public String getHolderName(){
        return this.holder.getUserName();
    }

    public int getWorkPermitId(){
        return this.workPermit.getWorkPermitId();
    }


    public static class Builder {

        private WorkLogInfo workLogInfo;
        private User employee;
        private WorkOrderInfo workOrderInfo;
        private User holder;
        private WorkPermit workPermit;

        public Builder withWorkLog(WorkLogInfo workLogInfo){
            this.workLogInfo = workLogInfo;
            return this;
        }

        public Builder withEmployee(User employee){
            this.employee = employee;
            return this;
        }

        public Builder withWorkOrderInfo(WorkOrderInfo workOrderInfo){
            this.workOrderInfo = workOrderInfo;
            return this;
        }

        public Builder withHolder(User holder){
            this.holder = holder;
            return this;
        }

        public Builder withWorkPermit(WorkPermit workPermit){
            this.workPermit = workPermit;
            return this;
        }

        public WorkLogElement build(){
            return new WorkLogElement(this);
        }
    }

}
