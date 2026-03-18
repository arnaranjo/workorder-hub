package com.workorderhub.core.entity;

/**
 * Represents the category of a work order. For instance, hot works or lifting operations.
 */
public class Category {

    private int categoryId;
    private String categoryName;
    private String categoryDescription;

    public Category() {
    }

    public Category(String name, String description) {
        this.categoryName = name;
        this.categoryDescription = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
}
