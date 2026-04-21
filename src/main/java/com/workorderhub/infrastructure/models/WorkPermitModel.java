package com.workorderhub.infrastructure.models;

public class WorkPermitModel {

    private int workPermitId;
    private String lockoutDeviceId;
    private String description;
    private int lotoProcedureId;

    public WorkPermitModel(){}

    public int getWorkPermitId() {
        return workPermitId;
    }

    public void setWorkPermitId(int workPermitId) {
        this.workPermitId = workPermitId;
    }

    public String getLockoutDeviceId() {
        return lockoutDeviceId;
    }

    public void setLockoutDeviceId(String lockoutDeviceId) {
        this.lockoutDeviceId = lockoutDeviceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLotoProcedureId() {
        return lotoProcedureId;
    }

    public void setLotoProcedureId(int lotoProcedureId) {
        this.lotoProcedureId = lotoProcedureId;
    }
}
