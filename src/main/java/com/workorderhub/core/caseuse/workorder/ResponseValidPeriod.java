package com.workorderhub.core.caseuse.workorder;

import java.time.LocalDate;

public record ResponseValidPeriod(
        LocalDate startDate,
        LocalDate endDate
) {
}
