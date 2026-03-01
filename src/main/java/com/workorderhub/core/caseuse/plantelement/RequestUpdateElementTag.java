package com.workorderhub.core.caseuse.plantelement;

public record RequestUpdateElementTag(
        int elementId,
        String newElementTag,
        String oldElementTag,
        int row
) {
}
