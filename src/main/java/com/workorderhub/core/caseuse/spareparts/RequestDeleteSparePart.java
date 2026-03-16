package com.workorderhub.core.caseuse.spareparts;

public record RequestDeleteSparePart(
        int sparePartId,
        String sparePartName,
        String partNumber,
        int sparePartIndex
) {
}
