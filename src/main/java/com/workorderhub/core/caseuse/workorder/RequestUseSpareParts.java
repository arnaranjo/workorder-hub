package com.workorderhub.core.caseuse.workorder;

public record RequestUseSpareParts(
        int sparePartId,
        int selectedNumber,
        String spareName,
        String spareNumber
) {
}
