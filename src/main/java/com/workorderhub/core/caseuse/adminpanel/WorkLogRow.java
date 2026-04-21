package com.workorderhub.core.caseuse.adminpanel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkLogRow(
        Integer logId,
        LocalDateTime logDate,
        String logComment,
        Long workOrderId,
        String userName,
        LocalDate workOrderStartDate,
        LocalDate workOrderEndDate,
        String holderName,
        Integer workPermitId
) {
}
