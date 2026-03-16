package com.workorderhub.core.caseuse.spareparts;

public interface SparePartInput {
    /**
     * Gets the complete spare parts list.
     */
    void retrieveSpareParts();

    /**
     * Gets the complete spare categories list.
     */
    void retrieveSpareCategories();

    /**
     * Creates a new spare part with the provided information.
     *
     * @param request Record with the necessary information to create a new spare part.
     */
    void createSparePart(RequestNewSparePart request);

    /**
     * Updates the sparePartName of the spare part with the specified ID.
     *
     * @param request Record with the necessary information to update the sparePartName of a spare part.
     */
    void updateSpareName(RequestUpdateSpareName request);

    /**
     * Updates the part number of the spare part with the specified ID.
     *
     * @param request Record with the necessary information to update the part number of a spare part.
     */
    void updateSpareNumber(RequestUpdateSpareNumber request);

    /**
     * Updates the description of the spare part with the specified ID.
     *
     * @param request Record with the necessary information to update the description of a spare part.
     */
    void updateSpareDescription(RequestUpdateSpareDescription request);

    /**
     * Updates the stock quantity of the spare part with the specified ID by increasing it.
     *
     * @param request Record with the necessary information to update the stock quantity of a spare part.
     */
    void increaseSpareStock(RequestUpdateSpareStock request);

    /**
     * Updates the stock quantity of the spare part with the specified ID by decreasing it.
     *
     * @param request Record with the necessary information to update the stock quantity of a spare part.
     */
    void decreaseSpareStock(RequestUpdateSpareStock request);

    /**
     * Updates the category of the spare part with the specified ID.
     *
     * @param request Record with the necessary information to update the category of a spare part.
     */
    void updateSpareCategory(RequestUpdateSpareCategory request);

    /**
     * Deletes the spare part with the specified ID.
     *
     * @param request ID of the spare part to be deleted.
     */
    void deleteSparePart(RequestDeleteSparePart request);

}
