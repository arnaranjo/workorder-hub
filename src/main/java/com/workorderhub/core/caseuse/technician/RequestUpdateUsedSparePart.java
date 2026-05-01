package com.workorderhub.core.caseuse.technician;

public record RequestUpdateUsedSparePart(
        long workOrderId,
        int sparePartId,
        int numberToUse,
        int currentStock,
        int numberFinallyUsed
) {
}
