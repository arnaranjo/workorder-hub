package com.workorderhub.core.caseuse.technician;

public record ResponseWorkPermit(
        String workPermitDescription,
        String lockDeviceId,
        String LotoProcedureCode,
        String LotoProcedureName
) {
}
