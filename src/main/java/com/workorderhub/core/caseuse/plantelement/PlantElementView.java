package com.workorderhub.core.caseuse.plantelement;

import java.time.LocalDate;
import java.util.List;

public interface PlantElementView {
    /**
     * Displays a message with information about actions performed.
     *
     * @param message message to display.
     */
    void setInfoDisplay(String message);

    /**
     * Set the function that allows to the user to edit the plant element table cells,
     * directly in the table view.
     */
    void setPlantElementTableEdition();

    /**
     * Populates the plant element table.
     *
     * @param PlantElementList list of plant elements.
     */
    void setPlantElementTableItems(List<PlantElementRow> PlantElementList);

    /**
     * Adds a new item to the plant element table.
     *
     * @param plantElementRow Record of a plant element.
     */
    void addPlantElementItem(PlantElementRow plantElementRow);

    /**
     * Remove an item from the plant element list.
     *
     * @param itemIndex index of the plant element in the list.
     */
    void removePlantElementItem(int itemIndex);

    /**
     * Sets the tag of the plant element.
     *
     * @param elementTag plant element tag.
     * @param row        table row index.
     */
    void setTableItemTag(String elementTag, int row);

    /**
     * Sets the description of the plant element.
     *
     * @param elementDescription plant element description.
     * @param row                table row index.
     */
    void setTableItemDescription(String elementDescription, int row);

    /**
     * Sets the location of the plant element.
     *
     * @param elementLocation plant element location.
     * @param row             table row index.
     */
    void setTableItemLocation(String elementLocation, int row);

    /**
     * Sets the inspection date of the plant element.
     *
     * @param date plant element inspection date.
     * @param row  table row index.
     */
    void setTableItemInspectionDate(LocalDate date, int row);

    /**
     * Sets the inspection frequency of the plant element.
     *
     * @param frequency plant element inspection frequency.
     * @param row       table row index.
     */
    void setTableItemInspectionFrequency(int frequency, int row);
}
