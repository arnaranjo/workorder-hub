package com.workorderhub.core.caseuse.plantelement;

import java.time.LocalDate;

public record RequestNewPlantElement(
        String elementTag,
        String elementDescription,
        String elementLocation,
        LocalDate inspectionDate,
        int inspectionFrequency
) {
}
