package com.workorderhub.core.caseuse.spareparts;

public record RequestUpdateSpareStock(
        int sparePartId,
        String sparePartName,
        String partNumber,
        int currentStock,
        int newValue,
        int tableRowIndex
) {
}
