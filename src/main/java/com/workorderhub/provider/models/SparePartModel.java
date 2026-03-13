package com.workorderhub.provider.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SparePartModel {
    private SimpleIntegerProperty spareId;
    private SimpleStringProperty spareName;
    private SimpleStringProperty spareNumber;
    private SimpleStringProperty spareDescription;
    private SimpleIntegerProperty spareStock;
    private SimpleStringProperty spareCategory;

    public SparePartModel() {
        spareId = new SimpleIntegerProperty();
        spareName = new SimpleStringProperty();
        spareNumber = new SimpleStringProperty();
        spareDescription = new SimpleStringProperty();
        spareStock = new SimpleIntegerProperty();
        spareCategory = new SimpleStringProperty();
    }

    public SparePartModel(
            int id, String name, String number, String description, int stock, String category
    ) {
        spareId = new SimpleIntegerProperty(id);
        spareName = new SimpleStringProperty(name);
        spareNumber = new SimpleStringProperty(number);
        spareDescription = new SimpleStringProperty(description);
        spareStock = new SimpleIntegerProperty(stock);
        spareCategory = new SimpleStringProperty(category);
    }

    public int getSparePartId() {
        return spareId.get();
    }

    public SimpleIntegerProperty spareIdProperty() {
        return spareId;
    }

    public void setSparePartId(int id) {
        this.spareId.set(id);
    }

    public String getSpareName() {
        return spareName.get();
    }

    public SimpleStringProperty spareNameProperty() {
        return spareName;
    }

    public void setSpareName(String spareName) {
        this.spareName.set(spareName);
    }

    public String getSpareNumber() {
        return spareNumber.get();
    }

    public SimpleStringProperty spareNumberProperty() {
        return spareNumber;
    }

    public void setSpareNumber(String spareNumber) {
        this.spareNumber.set(spareNumber);
    }

    public String getSpareDescription() {
        return spareDescription.get();
    }

    public SimpleStringProperty spareDescriptionProperty() {
        return spareDescription;
    }

    public void setSpareDescription(String spareDescription) {
        this.spareDescription.set(spareDescription);
    }

    public int getSpareStock() {
        return spareStock.get();
    }

    public SimpleIntegerProperty spareStockProperty() {
        return spareStock;
    }

    public void setSpareStock(int spareStock) {
        this.spareStock.set(spareStock);
    }

    public String getSpareCategory() {
        return spareCategory.get();
    }

    public SimpleStringProperty spareCategoryProperty() {
        return spareCategory;
    }

    public void setSpareCategory(String spareCategory) {
        this.spareCategory.set(spareCategory);
    }
}
