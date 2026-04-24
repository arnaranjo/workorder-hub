package com.workorderhub.core.caseuse.workorder;

public record ResponseRequirements(
        boolean isValidPeriodRequired,
        boolean isWorkProcedureRequired,
        boolean isWorkPermitRequired
) {
}
