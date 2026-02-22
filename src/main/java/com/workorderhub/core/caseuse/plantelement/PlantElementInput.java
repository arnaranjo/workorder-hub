package com.workorderhub.core.caseuse.plantelement;

public interface PlantElementInput {
    /**
     * Gets the complete plant element list, when the window is loaded.
     */
    void retrievePlantElements();

    /**
     * Creates a new plant element in the database.
     * @param request Record of a plant element.
     * @return ID of the plant element, return 0 is an error arises.
     */
    int createPlantElement(RequestNewPlantElement request);

    /**
     * Updates a plant element in the database.
     * @param request Record of a plant element.
     * @return true if the update is successful or false if not.
     */
    boolean updatePlantElement(RequestUpdatePlantElement request);

    /**
     * Deletes a plant element in the database.
     * @param request Record of a plant element.
     * @return true if the deletion is successful or false if not.
     */
    boolean deletePlantElement(RequestDeletePlantElement request);
}
