package com.workorderhub.core.caseuse.workorder;

import java.time.LocalDate;

public record ResponsePlantElement (
        int elementId,
        String elementTag,
        String elementDescription,
        String elementLocation,
        LocalDate inspectionDate,
        int inspectionFrequency
){
}
