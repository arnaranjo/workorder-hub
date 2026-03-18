package com.workorderhub.core.entity;

/**
 * Represents the status of a work order.
 */
public class Status {

    private int statusId;
    private String orderStatus;


    public Status() {
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}