package com.workorderhub.core.caseuse.plantelement;

import java.util.List;

public interface PlantElementView {
    /**
     * Displays a message with information about actions performed.
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
     * @param PlantElementList list of plant elements.
     */
    void setPlantElementTableItems(List<PlantElementRow> PlantElementList);
}
