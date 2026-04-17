package com.workorderhub.core.entity;

/**
 * Used spare part in a work permit.
 */
public class UsedSparePart {

    private long workOrderId;
    private int sparePartId;
    private int selectedNumber;
    private String spareName;
    private String spareNumber;

    public UsedSparePart() {
    }

    public UsedSparePart(
            long workOrderId,
            int sparePartId,
            int selectedNumber,
            String spareName,
            String spareNumber
    ) {
        this.workOrderId = workOrderId;
        this.sparePartId = sparePartId;
        this.selectedNumber = selectedNumber;
        this.spareName = spareName;
        this.spareNumber = spareNumber;
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
