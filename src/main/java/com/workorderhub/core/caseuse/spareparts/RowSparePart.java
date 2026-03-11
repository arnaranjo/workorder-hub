package com.workorderhub.core.caseuse.spareparts;

public record RowSparePart(
        int spareId,
        String spareName,
        String spareNumber,
        String spareDescription,
        int spareStock,
        String spareCategory
) {
}
