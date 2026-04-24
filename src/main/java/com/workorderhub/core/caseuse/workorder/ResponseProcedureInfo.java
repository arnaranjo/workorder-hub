package com.workorderhub.core.caseuse.workorder;

public record ResponseProcedureInfo(
        int procedureId,
        String documentCode,
        String documentName
) {
}
