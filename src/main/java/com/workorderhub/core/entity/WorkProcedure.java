package com.workorderhub.core.entity;

public class WorkProcedure {

    private int procedureId;
    private String documentCode;
    private String documentName;

    public WorkProcedure() {
    }

    public WorkProcedure(int procedureId, String documentCode, String documentName) {
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
