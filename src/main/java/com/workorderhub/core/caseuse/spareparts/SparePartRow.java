package com.workorderhub.core.caseuse.spareparts;

public record SparePartRow(
        int spareId,
        String spareName,
        String spareNumber,
        String spareDescription,
        int spareStock,
        String spareCategory
) {
}
