package com.workorderhub.core.caseuse.spareparts;

import java.util.List;

public interface SparePartOutput {

    /**
     * Requests confirmation from the user for a specific spare part action, such as deletion or update.
     *
     * @param sparePartEnum An enum value representing the specific action for which confirmation is being requested.
     * @return True if the user accepts the request, false if the user cancel the request.
     */
    boolean requestConfirmation(SparePartEnum sparePartEnum);

    /**
     * Displays a message when the action is completed successfully.
     *
     * @param response      Record of the spare part added, update or delete.
     * @param sparePartEnum Display the message when the new spare part added, update or delete.
     */
    void displayConfirmation(ResponseSparePartConfirmation response, SparePartEnum sparePartEnum);

    /**
     * Updates the spare part sparePartName in the spare part table.
     *
     * @param response It contains the sparePartName and table row.
     */
    void updateNameCell(ResponseUpdateSpareName response);

    /**
     * Updates the spare part number in the spare part table.
     *
     * @param response It contains the number and table row.
     */
    void updateNumberCell(ResponseUpdateSpareNumber response);

    /**
     * Updates the spare part description in the spare part table.
     *
     * @param response It contains the description and table row.
     */
    void updateDescriptionCell(ResponseUpdateSpareDescription response);

    /**
     * Updates the spare part stock in the spare part table.
     *
     * @param response It contains the stock and table row.
     */
    void updateStockCell(ResponseUpdateSpareStock response);

    /**
     * Updates the spare part category in the spare part table.
     *
     * @param response It contains the category and table row.
     */
    void updateCategoryCell(ResponseUpdateSpareCategory response);

    /**
     * Adds a new row in the spare part table.
     *
     * @param sparePartRow It contains all the information of a new spare part.
     */
    void addNewRow(SparePartRow sparePartRow);

    /**
     * Removes a spare part item from the spare part table.
     *
     * @param response It contains the sparePartId of the spare part to be removed from the table.
     */
    void removeSparePartItem(ResponseDeleteSparePart response);

    /**
     * Displays a message when the action is not completed successfully.
     *
     * @param sparePartEnum Displays the message when errors occur.
     */
    void displayError(SparePartEnum sparePartEnum);

    /**
     * Populates the spare parts table with the provided list of spare part rows.
     *
     * @param sparePartRowList A list of SparePartRow objects containing the information
     *                         to be displayed in the spare parts table.
     */
    void populatesSparePartTable(List<SparePartRow> sparePartRowList);

    /**
     * Provides the list of spare part categories to be displayed in the category selector.
     *
     * @param categoryList A list of ResponseSparePartCategories objects representing the spare part categories.
     */
    void provideSparePartCategories(List<ResponseSparePartCategories> categoryList);
}
