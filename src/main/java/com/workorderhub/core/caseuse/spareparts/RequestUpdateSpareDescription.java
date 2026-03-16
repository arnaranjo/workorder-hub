package com.workorderhub.core.caseuse.spareparts;

public record RequestUpdateSpareDescription(
        int sparePartId,
        String sparePartName,
        String partNumber,
        String oldDescription,
        String newDescription,
        int tableRowIndex
) {
}
