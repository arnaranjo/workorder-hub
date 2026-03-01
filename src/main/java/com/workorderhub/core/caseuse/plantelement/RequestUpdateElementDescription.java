package com.workorderhub.core.caseuse.plantelement;

public record RequestUpdateElementDescription(
        int elementId,
        String elementTag,
        String newElementDescription,
        String oldElementDescription,
        int row
) {
}
