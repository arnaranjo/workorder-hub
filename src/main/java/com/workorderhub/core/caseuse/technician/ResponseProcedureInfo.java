package com.workorderhub.core.caseuse.technician;

public record ResponseProcedureInfo(
        int procedureId,
        String documentCode,
        String documentName
) {
}
