package com.workorderhub.core.caseuse.plantelement;

import java.time.LocalDate;

public record ResponseUpdateElementInspectionDate(
        LocalDate inspectionDate,
        int row
) {
}
