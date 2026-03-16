package com.workorderhub.core.caseuse.spareparts;

public record RequestUpdateSpareNumber(
        int sparePartId,
        String sparePartName,
        String oldPartNumber,
        String newPartNumber,
        int tableRowIndex
) {
}
