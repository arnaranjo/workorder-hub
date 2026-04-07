package com.workorderhub.core.entity;


/**
 * A work permit is a document associated to the work order for special tasks.
 * That implies the use of lockout devices, and the safety instructions of the work to be done.
 */
public class WorkPermit {

    private int workPermitId;
    private String lockoutDeviceId;
    private String description;
    private int lotoProcedureId;

    public WorkPermit(){}

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
