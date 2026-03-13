package com.workorderhub.core.caseuse.spareparts;

import com.workorderhub.provider.models.SparePartCategoryModel;

import java.util.List;

public interface SparePartsView {

    /**
     * Sets the message to be displayed in the information area of the spare parts view.
     *
     * @param message The message to be displayed.
     */
    void setInfoDisplay(String message);

    /**
     * Sets the list of categories to be displayed in the category selector.
     *
     * @param categoryList A list of category names to be displayed in the category selection component.
     */
    void setCategoryList(List<SparePartCategoryModel> categoryList);

    /**
     * Set the function that allows to the user to edit the spare part table cells,
     * directly in the table view.
     */
    void setSparePartTableEdition();

    /**
     * Populates the spare part table.
     *
     * @param sparePartList A list of SparePartRow objects containing the information
     *                      to be displayed in the spare parts table.
     */
    void setSparePartTableItems(List<SparePartRow> sparePartList);

    /**
     * Adds a new item to the spare part table.
     *
     * @param sparePartRow Record of a spare part to be added to the table.
     */
    void addSparePartItem(SparePartRow sparePartRow);

    /**
     * Removes an item from the spare part list.
     *
     * @param itemIndex Index of the spare part in the list to be removed.
     */
    void removeSparePartItem(int itemIndex);

    /**
     * Sets the name of the spare part in the specified row of the table.
     *
     * @param sparePartName The name of the spare part to be set in the table.
     * @param row           The index of the row in the table.
     */
    void setTableItemName(String sparePartName, int row);

    /**
     * Sets the part number of the spare part in the specified row of the table.
     *
     * @param sparePartNumber The part number code of the spare part to be set in the table.
     * @param row             The index of the row in the table.
     */
    void setTableItemPartNumber(String sparePartNumber, int row);

    /**
     * Sets the description of the spare part in the specified row of the table.
     *
     * @param sparePartDescription The description of the spare part to be set in the table.
     * @param row                  The index of the row in the table.
     */
    void setTableItemDescription(String sparePartDescription, int row);

    /**
     * Sets the stock quantity of the spare part in the specified row of the table.
     *
     * @param sparePartStock The stock quantity of the spare part to be set in the table.
     * @param row            The index of the row in the table.
     */
    void setTableItemStock(int sparePartStock, int row);

    /**
     * Sets the category of the spare part in the specified row of the table.
     *
     * @param sparePartCategory The category of the spare part to be set in the table.
     */
    void setTableItemCategory(String sparePartCategory, int row);


}
