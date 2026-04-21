package com.workorderhub.infrastructure.models;

public class SparePartCategoryModel {

    private int categoryID;
    private String categoryName;

    public SparePartCategoryModel(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
