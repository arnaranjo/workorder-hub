package com.workorderhub.core.caseuse.spareparts;

import java.util.List;

public interface SparePartOutput {

    /**
     * Populates the spare parts table with the provided list of spare part rows.
     *
     * @param rowSparePartList A list of RowSparePart objects containing the information
     *                         to be displayed in the spare parts table.
     */
    void populatesSparePartsTable(List<RowSparePart> rowSparePartList);
}
