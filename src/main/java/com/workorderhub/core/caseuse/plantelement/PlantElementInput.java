package com.workorderhub.core.caseuse.plantelement;

public interface PlantElementInput {
    /**
     * Gets the complete plant element list, when the window is loaded.
     */
    void retrievePlantElements();

    /**
     * Creates a new plant element in the database.
     *
     * @param request Record of a plant element with all information to create a new plant element.
     */
    void createPlantElement(RequestNewPlantElement request);

    /**
     * Updates the plant element tag in the database.
     *
     * @param request Record of a plant element. It contains the old and new name tag, element ID
     *                and selected column row.
     */
    void updateElementTag(RequestUpdateElementTag request);

    /**
     * Updates the plant element description in the database.
     *
     * @param request Record of a plant element. It contains the old and new description, element ID
     *                and selected column row.
     */
    void updateElementDescription(RequestUpdateElementDescription request);

    /**
     * Updates the plant element location in the database.
     *
     * @param request Record of a plant element. It contains the old and new location, element ID
     *                and selected column row.
     */
    void updateElementLocation(RequestUpdateElementLocation request);

    /**
     * Updates the plant element inspection date in the database.
     *
     * @param request Record of a plant element. It contains the old and new inspection date, element ID
     *                and selected column row.
     */
    void updateElementInspectionDate(RequestUpdateElementInspectionDate request);

    /**
     * Updates the plant element monthly inspection frequency in the database.
     *
     * @param request Record of a plant element. It contains the old and new inspection frequency, element ID
     *                and selected column row.
     */
    void updateElementInspectionFrequency(RequestUpdateElementInspectionFrequency request);

    /**
     * Deletes a plant element in the database.
     *
     * @param request Record of a plant element. It contains the element ID, name tag and
     *                its index of the retrieved plant element list.
     */
    void deletePlantElement(RequestDeletePlantElement request);
}
