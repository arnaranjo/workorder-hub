package com.workorderhub.core.caseuse.plantelement;

import java.time.LocalDate;

public record RowPlantElement(
        int elementId,
        String elementTag,
        String elementDescription,
        String elementLocation,
        LocalDate inspectionDate,
        int inspectionFrequency
) {
}
