package com.workorderhub.core.caseuse.workorder;

public record RequestUseSpareParts(
        int sparePartId,
        int selectedNumber,
        int currentStock,
        String spareName,
        String spareNumber
) {
}
