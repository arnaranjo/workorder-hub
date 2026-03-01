package com.workorderhub.core.caseuse.plantelement;

public record RequestDeletePlantElement(
        int elementId,
        String elementTag,
        int plantElementIndex
) {
}
