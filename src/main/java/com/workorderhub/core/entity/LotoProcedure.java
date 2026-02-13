package com.workorderhub.core.entity;

/**
 * A LOTO (Lock out - Tag out) procedure is a document that describes the execution to lock and isolate a plant element,
 * such as equipment or valves.
 * These documents are usually identified internally within the company with a code and name.
 */
public class LotoProcedure {

    private int procedureId;
    private String documentCode;
    private String documentName;

    public LotoProcedure(int procedureId, String documentCode, String documentName) {
        this.procedureId = procedureId;
        this.documentCode = documentCode;
        this.documentName = documentName;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
