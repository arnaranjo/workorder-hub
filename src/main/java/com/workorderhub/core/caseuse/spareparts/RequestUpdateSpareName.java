package com.workorderhub.core.caseuse.spareparts;

public record RequestUpdateSpareName(
        int sparePartId,
        String oldSparePartName,
        String newSparePartName,
        String partNumber,
        int tableRowIndex
) {
}
