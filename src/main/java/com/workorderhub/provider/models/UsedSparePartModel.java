package com.workorderhub.provider.models;

public class UsedSparePartModel {

    private long workOrderId;
    private int sparePartId;
    private int selectedNumber;
    private String spareName;
    private String spareNumber;

    public UsedSparePartModel() {
    }

    public UsedSparePartModel(long workOrderId, int sparePartId, int selectedNumber) {
        this.workOrderId = workOrderId;
        this.sparePartId = sparePartId;
        this.selectedNumber = selectedNumber;
    }

    public long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public int getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(int selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    public String getSpareName() {
        return spareName;
    }

    public void setSpareName(String spareName) {
        this.spareName = spareName;
    }

    public String getSpareNumber() {
        return spareNumber;
    }

    public void setSpareNumber(String spareNumber) {
        this.spareNumber = spareNumber;
    }
}
