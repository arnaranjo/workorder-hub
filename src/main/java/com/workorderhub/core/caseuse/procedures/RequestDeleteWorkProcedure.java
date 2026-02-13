package com.workorderhub.core.caseuse.procedures;

public record RequestDeleteWorkProcedure(
        int documentId,
        String documentCode,
        String documentName
) {
}
