package com.workorderhub.provider.tablemodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A LOTO (Lock out - Tag out) procedure is a document that describes the execution to lock and isolate a plant element,
 * such as equipment or valves.
 * These documents are usually identified internally within the company with a code and name.
 */
public class LotoProcedureModel {

    private SimpleIntegerProperty procedureId;
    private SimpleStringProperty documentCode;
    private SimpleStringProperty documentName;

    public LotoProcedureModel() {
        procedureId = new SimpleIntegerProperty();
        documentCode = new SimpleStringProperty();
        documentName = new SimpleStringProperty();
    }

    public LotoProcedureModel(int id, String code, String name) {
        procedureId = new SimpleIntegerProperty(id);
        documentCode = new SimpleStringProperty(code);
        documentName = new SimpleStringProperty(name);
    }

    public int getProcedureId() {
        return procedureId.get();
    }

    public SimpleIntegerProperty procedureIdProperty() {
        return procedureId;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId.set(procedureId);
    }

    public String getDocumentCode() {
        return documentCode.get();
    }

    public SimpleStringProperty documentCodeProperty() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode.set(documentCode);
    }

    public String getDocumentName() {
        return documentName.get();
    }

    public SimpleStringProperty documentNameProperty() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName.set(documentName);
    }
}
