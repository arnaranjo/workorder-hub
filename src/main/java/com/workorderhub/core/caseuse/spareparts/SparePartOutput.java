package com.workorderhub.core.caseuse.spareparts;

import java.util.List;

public interface SparePartOutput {

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
