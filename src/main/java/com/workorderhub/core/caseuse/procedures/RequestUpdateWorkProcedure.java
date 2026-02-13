package com.workorderhub.core.caseuse.procedures;

public record RequestUpdateWorkProcedure(
        int documentId,
        String documentCode,
        String documentName
) {
}
