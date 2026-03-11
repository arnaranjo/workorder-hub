package com.workorderhub.core.entity;

/**
 * Represents a spare part.
 * Consumable items which may be used during maintenance work and which are included in the work order.
 */
public class SparePart {
    private int spareId;
    private String spareName;
    private String spareNumber;
    private String spareDescription;
    private int spareStock;
    private int spareCategory;

    private SparePart(Builder builder) {
        this.spareId = builder.spareId;
        this.spareName = builder.spareName;
        this.spareNumber = builder.spareNumber;
        this.spareDescription = builder.spareDescription;
        this.spareStock = builder.spareStock;
        this.spareCategory = builder.spareCategory;
    }

    public int getSpareId() {
        return spareId;
    }

    public String getSpareName() {
        return spareName;
    }

    public String getSpareNumber() {
        return spareNumber;
    }

    public String getSpareDescription() {
        return spareDescription;
    }

    public int getSpareStock() {
        return spareStock;
    }

    public int getSpareCategory() {
        return spareCategory;
    }

    public static class Builder {
        private int spareId;
        private String spareName;
        private String spareNumber;
        private String spareDescription;
        private int spareStock;
        private int spareCategory;

        public Builder withSpareId(int spareId) {
            this.spareId = spareId;
            return this;
        }

        public Builder withSpareName(String spareName) {
            this.spareName = spareName;
            return this;
        }

        public Builder withSpareNumber(String spareNumber) {
            this.spareNumber = spareNumber;
            return this;
        }

        public Builder withSpareDescription(String spareDescription) {
            this.spareDescription = spareDescription;
            return this;
        }

        public Builder withSpareStock(int spareStock) {
            this.spareStock = spareStock;
            return this;
        }

        public Builder withSpareCategory(int spareCategory) {
            this.spareCategory = spareCategory;
            return this;
        }

        public SparePart build() {
            return new SparePart(this);
        }
    }
}
