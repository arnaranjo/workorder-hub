package com.workorderhub.core.caseuse.technician;

public record ResponseUsedSparePart(
        long workOrderId,
        int sparePartId,
        int selectedNumber,
        int currentStock,
        String spareName,
        String spareNumber
) {
}
