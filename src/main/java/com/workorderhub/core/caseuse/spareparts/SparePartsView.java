package com.workorderhub.core.caseuse.spareparts;

import java.util.List;

public interface SparePartsView {
    /**
     * Sets the items in the spare parts table with the provided list of RowSparePart objects.
     *
     * @param sparePartList A list of RowSparePart objects containing the information
     *                      to be displayed in the spare parts table.
     */
    void setSparePartTableItems(List<RowSparePart> sparePartList);
}
