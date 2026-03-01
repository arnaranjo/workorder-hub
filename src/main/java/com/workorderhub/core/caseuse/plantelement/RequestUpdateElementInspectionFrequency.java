package com.workorderhub.core.caseuse.plantelement;

public record RequestUpdateElementInspectionFrequency(
        int elementId,
        String elementTag,
        int newInspectionFrequency,
        int oldInspectionFrequency,
        int row
) {
}
