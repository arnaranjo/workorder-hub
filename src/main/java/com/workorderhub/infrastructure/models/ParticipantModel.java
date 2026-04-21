package com.workorderhub.infrastructure.models;

public class ParticipantModel {

    private long workOrderId;
    private int userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;

    public ParticipantModel() {
    }

    public ParticipantModel(
            long workOrderId,
            int userId,
            String userName,
            String userEmail,
            String userPhoneNumber
    ) {
        this.workOrderId = workOrderId;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
