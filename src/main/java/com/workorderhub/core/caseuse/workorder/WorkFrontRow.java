package com.workorderhub.core.caseuse.workorder;

import java.time.LocalDate;

public record WorkFrontRow(
        long workOrderId,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        String plantElementTag,
        String workProcedureCode,
        String lockDeviceId,
        String lotoProcedureCode,
        String holderName,
        String status
) {
}
