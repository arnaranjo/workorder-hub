package com.workorderhub.core.caseuse.plantelement;

import java.time.LocalDate;

public record PlantElementRow(
        int elementId,
        String elementTag,
        String elementDescription,
        String elementLocation,
        LocalDate inspectionDate,
        int inspectionFrequency
) {
}
