package com.workorderhub.provider.tablemodels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class WorkProcedureModel {

    private SimpleIntegerProperty workProcedureId;
    private SimpleStringProperty workProcedureCode;
    private SimpleStringProperty workProcedureName;

    public WorkProcedureModel() {
        workProcedureId = new SimpleIntegerProperty();
        workProcedureCode = new SimpleStringProperty();
        workProcedureName = new SimpleStringProperty();
    }

    public WorkProcedureModel(int id, String code, String name) {
        workProcedureId = new SimpleIntegerProperty(id);
        workProcedureCode = new SimpleStringProperty(code);
        workProcedureName = new SimpleStringProperty(name);
    }

    public int getWorkProcedureId() {
        return workProcedureId.get();
    }

    public SimpleIntegerProperty workProcedureIdProperty() {
        return workProcedureId;
    }

    public void setWorkProcedureId(int workProcedureId) {
        this.workProcedureId.set(workProcedureId);
    }

    public String getWorkProcedureCode() {
        return workProcedureCode.get();
    }

    public SimpleStringProperty workProcedureCodeProperty() {
        return workProcedureCode;
    }

    public void setWorkProcedureCode(String workProcedureCode) {
        this.workProcedureCode.set(workProcedureCode);
    }

    public String getWorkProcedureName() {
        return workProcedureName.get();
    }

    public SimpleStringProperty workProcedureNameProperty() {
        return workProcedureName;
    }

    public void setWorkProcedureName(String workProcedureName) {
        this.workProcedureName.set(workProcedureName);
    }
}
