package com.workorderhub.core.caseuse.workorder;

public record ResponseWOrkPermitInfo(
        String permitDescription,
        String lockDevices,
        Integer lotoProcedureId,
        String lotoProcedureCode,
        String lotoProcedureName
) {
}
