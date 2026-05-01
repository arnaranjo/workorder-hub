package com.workorderhub.core.caseuse.workorder;

public record ResponseWorkPermitInfo(
        String permitDescription,
        String lockDevices,
        Integer lotoProcedureId,
        String lotoProcedureCode,
        String lotoProcedureName
) {
}
