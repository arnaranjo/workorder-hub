package com.workorderhub.core.entity;

/**
 * Category of a specific work order.
 */
public class AssignedCategory {

    private long workOrderId;
    private int categoryId;
    private String categoryName;
    private String categoryDescription;

    public AssignedCategory() {
    }

    public AssignedCategory(
            long workOrderId,
            int categoryId,
            String categoryName,
            String categoryDescription
    ) {
        this.workOrderId = workOrderId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String description) {
        this.categoryDescription = description;
    }
}

