package com.workorderhub.core.caseuse.plantelement;

import java.time.LocalDate;

public record RequestUpdateElementInspectionDate(
        int elementId,
        String elementTag,
        LocalDate newInspectionDate,
        LocalDate oldInspectionDate,
        int row
) {
}
