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
    void displayConfirmation(ResponseNewPlantElement response, PlantElementEnum plantElementEnum);

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
