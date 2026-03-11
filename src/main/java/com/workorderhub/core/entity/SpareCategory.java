package com.workorderhub.core.entity;

/**
 * Represents a category of spare parts in the system.
 * Categories are used to group spare parts by type, function, or other criteria.
 */
public class SpareCategory {

    private int categoryID;
    private String categoryName;

    public SpareCategory(){}

    public SpareCategory(int id, String category) {
        categoryID = id;
        categoryName = category;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}