package com.workorderhub.provider.tablemodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class PlantElementModel {

    private SimpleIntegerProperty elementId;
    private SimpleStringProperty elementTag;
    private SimpleStringProperty elementDescription;
    private SimpleStringProperty elementLocation;
    private SimpleObjectProperty<LocalDate> inspectionDate;
    private SimpleIntegerProperty inspectionFrequency;

    public PlantElementModel() {
        elementId = new SimpleIntegerProperty();
        elementTag = new SimpleStringProperty();
        elementDescription = new SimpleStringProperty();
        elementLocation = new SimpleStringProperty();
        inspectionDate = new SimpleObjectProperty<>();
        inspectionFrequency = new SimpleIntegerProperty();
    }

    public PlantElementModel(
            int id, String tag, String description, String location, LocalDate date, int frequency
    ) {
        elementId = new SimpleIntegerProperty(id);
        elementTag = new SimpleStringProperty(tag);
        elementDescription = new SimpleStringProperty(description);
        elementLocation = new SimpleStringProperty(location);
        inspectionDate = new SimpleObjectProperty<>(date);
        inspectionFrequency = new SimpleIntegerProperty(frequency);

    }

    public int getElementId() {
        return elementId.get();
    }

    public SimpleIntegerProperty elementIdProperty() {
        return elementId;
    }

    public void setElementId(int elementId) {
        this.elementId.set(elementId);
    }

    public String getElementTag() {
        return elementTag.get();
    }

    public SimpleStringProperty elementTagProperty() {
        return elementTag;
    }

    public void setElementTag(String elementTag) {
        this.elementTag.set(elementTag);
    }

    public String getElementDescription() {
        return elementDescription.get();
    }

    public SimpleStringProperty elementDescriptionProperty() {
        return elementDescription;
    }

    public void setElementDescription(String elementDescription) {
        this.elementDescription.set(elementDescription);
    }

    public String getElementLocation() {
        return elementLocation.get();
    }

    public SimpleStringProperty elementLocationProperty() {
        return elementLocation;
    }

    public void setElementLocation(String elementLocation) {
        this.elementLocation.set(elementLocation);
    }

    public LocalDate getInspectionDate() {
        return inspectionDate.get();
    }

    public SimpleObjectProperty<LocalDate> inspectionDateProperty() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDate inspectionDate) {
        this.inspectionDate.set(inspectionDate);
    }

    public int getInspectionFrequency() {
        return inspectionFrequency.get();
    }

    public SimpleIntegerProperty inspectionFrequencyProperty() {
        return inspectionFrequency;
    }

    public void setInspectionFrequency(int inspectionFrequency) {
        this.inspectionFrequency.set(inspectionFrequency);
    }
}
