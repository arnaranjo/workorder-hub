package com.workorderhub.core.caseuse.plantelement;

import java.util.List;

public interface PlantElementOutput {
    /**
     * Displays a window to confirm an action by the user.
     *
     * @param plantElementEnum Request confirmation delete a plant element.
     * @return True if the user accepts the request, false if the user cancel the request.
     */
    boolean requestConfirmation(PlantElementEnum plantElementEnum);

    /**
     * Displays a message when the action is completed successfully.
     *
     * @param response         Record of the plant element added, update or delete.
     * @param plantElementEnum Display the message when the new plant element added, update or delete.
     */
    void displayConfirmation(ResponsePlantElementConfirmation response, PlantElementEnum plantElementEnum);

    /**
     * Updates the element tag in the plant element table.
     *
     * @param response It contains the tag name and table row.
     */
    void updateElementTagCell(ResponseUpdateElementTag response);

    /**
     * Updates the element description in the plant element table.
     *
     * @param response It contains the description and table row.
     */
    void updateElementDescriptionCell(ResponseUpdateElementDescription response);

    /**
     * Updates the element location in the plant element table.
     *
     * @param response It contains the location and table row.
     */
    void updateElementLocationCell(ResponseUpdateElementLocation response);

    /**
     * Updates the element inspection date in the plant element table.
     *
     * @param response It contains the date and table row.
     */
    void updateElementInspectionDateCell(ResponseUpdateElementInspectionDate response);

    /**
     * Updates the element inspection frequency in the plant element table.
     *
     * @param response It contains the inspection frequency value and table row.
     */
    void updateElementInspectionFrequencyCell(ResponseUpdateElementInspectionFrequency response);

    /**
     * Adds a new row in the plant element table.
     *
     * @param plantElementRow It contains all the information of a new plant element.
     */
    void addNewTableRow(PlantElementRow plantElementRow);


    void removePlantElementItem(ResponseDeletePlantElement response);

    /**
     * Displays a message when the action is not completed successfully.
     *
     * @param plantElementEnum Displays the message when errors occur.
     */
    void displayError(PlantElementEnum plantElementEnum);

    /**
     * Populates the plan element table.
     *
     * @param plantElementRowList List of plant element records.
     */
    void populatePlantElementTable(List<PlantElementRow> plantElementRowList);
}
