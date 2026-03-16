package com.workorderhub.core.caseuse.spareparts;

public record RequestUpdateSpareCategory(
    int sparePartId,
    String sparePartName,
    String partNumber,
    String oldCategoryName,
    String newCategoryName,
    int tableRowIndex
) {
}
