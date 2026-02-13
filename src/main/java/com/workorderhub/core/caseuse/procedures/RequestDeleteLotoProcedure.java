package com.workorderhub.core.caseuse.procedures;

public record RequestDeleteLotoProcedure(
        int documentId,
        String documentCode,
        String documentName
) {
}
