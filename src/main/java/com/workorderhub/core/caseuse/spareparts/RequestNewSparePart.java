package com.workorderhub.core.caseuse.spareparts;

public record RequestNewSparePart(
        String spareName,
        String spareNumber,
        String spareDescription,
        String spareCategory,
        int spareStock
) {
}
