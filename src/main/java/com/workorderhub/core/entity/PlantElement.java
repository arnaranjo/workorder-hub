package com.workorderhub.core.entity;

import java.time.LocalDate;

/**
 * A plant element represents an instrument, piping element, or equipment located in the plant's process line.
 * These elements are identified on process and instrumentation diagrams with a tag, and most require inspection
 * during plant operation.
 */
public class PlantElement {

    private int elementId;
    private String elementTag;
    private String elementDescription;
    private String elementLocation;
    private LocalDate inspectionDate;
    private int inspectionFrequency;

    private PlantElement(Builder builder) {
        this.elementId = builder.elementId;
        this.elementTag = builder.elementTag;
        this.elementDescription = builder.elementDescription;
        this.elementLocation = builder.elementLocation;
        this.inspectionDate = builder.inspectionDate;
        this.inspectionFrequency = builder.inspectionFrequency;
    }

    public int getElementId() {
        return elementId;
    }

    public String getElementTag() {
        return elementTag;
    }

    public String getElementDescription() {
        return elementDescription;
    }

    public String getElementLocation() {
        return elementLocation;
    }

    public LocalDate getInspectionDate() {
        return inspectionDate;
    }

    public int getInspectionFrequency() {
        return inspectionFrequency;
    }

    public static class Builder {

        private int elementId;
        private String elementTag;
        private String elementDescription;
        private String elementLocation;
        private LocalDate inspectionDate;
        private int inspectionFrequency;

        public Builder withElementId(int elementId) {
            this.elementId = elementId;
            return this;
        }

        public Builder withElementTag(String elementTag) {
            this.elementTag = elementTag;
            return this;
        }

        public Builder withElementDescription(String elementDescription) {
            this.elementDescription = elementDescription;
            return this;
        }

        public Builder withElementLocation(String elementLocation) {
            this.elementLocation = elementLocation;
            return this;
        }

        public Builder withInspectionDate(LocalDate inspectionDate) {
            this.inspectionDate = inspectionDate;
            return this;
        }

        public Builder withInspectionFrequency(int inspectionFrequency) {
            this.inspectionFrequency = inspectionFrequency;
            return this;
        }

        public PlantElement build() {
            return new PlantElement(this);
        }
    }
}
