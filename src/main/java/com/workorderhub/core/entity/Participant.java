package com.workorderhub.core.entity;

/**
 * Technician involved in a work order task.
 */
public class Participant {

    private long workOrderId;
    private int userId;
    private String employeeName;
    private String employeeEmail;
    private String employeePhoneNumber;

    public Participant() {
    }

    public Participant(
            long workOrderId,
            int userId,
            String employeeName,
            String employeeEmail,
            String employeePhoneNumber
    ) {
        this.workOrderId = workOrderId;
        this.userId = userId;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePhoneNumber = employeePhoneNumber;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeePhoneNumber() {
        return employeePhoneNumber;
    }

    public void setEmployeePhoneNumber(String employeePhoneNumber) {
        this.employeePhoneNumber = employeePhoneNumber;
    }
}
