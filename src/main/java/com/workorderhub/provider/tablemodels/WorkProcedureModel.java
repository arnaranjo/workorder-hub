package com.workorderhub.provider.tablemodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * A work procedure is a document that describes the step-by-step execution of a task.
 * These documents are usually identified internally within the company with a code and name.
 */
public class WorkProcedureModel {

    private SimpleIntegerProperty procedureId;
    private SimpleStringProperty procedureCode;
    private SimpleStringProperty procedureName;

    public WorkProcedureModel() {
        procedureId = new SimpleIntegerProperty();
        procedureCode = new SimpleStringProperty();
        procedureName = new SimpleStringProperty();
    }

    public WorkProcedureModel(int id, String code, String name) {
        procedureId = new SimpleIntegerProperty(id);
        procedureCode = new SimpleStringProperty(code);
        procedureName = new SimpleStringProperty(name);
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

    public String getProcedureCode() {
        return procedureCode.get();
    }

    public SimpleStringProperty procedureCodeProperty() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode.set(procedureCode);
    }

    public String getProcedureName() {
        return procedureName.get();
    }

    public SimpleStringProperty procedureNameProperty() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName.set(procedureName);
    }
}
