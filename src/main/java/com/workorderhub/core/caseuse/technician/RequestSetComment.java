package com.workorderhub.core.caseuse.technician;

public record RequestSetComment(
        long workOrderId,
        String comment
) {
}
