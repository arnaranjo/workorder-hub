package com.workorderhub.core.caseuse.procedures;

public record RequestUpdateLotoProcedure(
        int documentId,
        String documentCode,
        String documentName
) {
}
