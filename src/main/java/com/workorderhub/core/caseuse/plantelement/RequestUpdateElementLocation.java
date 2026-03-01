package com.workorderhub.core.caseuse.plantelement;

public record RequestUpdateElementLocation(
        int elementId,
        String elementTag,
        String newElementLocation,
        String oldElementLocation,
        int row
) {
}
