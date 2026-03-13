package com.workorderhub.provider.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LotoProcedureModel {

    private SimpleIntegerProperty lotoProcedureId;
    private SimpleStringProperty lotoProcedureCode;
    private SimpleStringProperty lotoProcedureName;

    public LotoProcedureModel() {
        lotoProcedureId = new SimpleIntegerProperty();
        lotoProcedureCode = new SimpleStringProperty();
        lotoProcedureName = new SimpleStringProperty();
    }

    public LotoProcedureModel(int id, String code, String name) {
        lotoProcedureId = new SimpleIntegerProperty(id);
        lotoProcedureCode = new SimpleStringProperty(code);
        lotoProcedureName = new SimpleStringProperty(name);
    }

    public int getLotoProcedureId() {
        return lotoProcedureId.get();
    }

    public SimpleIntegerProperty lotoProcedureIdProperty() {
        return lotoProcedureId;
    }

    public void setLotoProcedureId(int lotoProcedureId) {
        this.lotoProcedureId.set(lotoProcedureId);
    }

    public String getLotoProcedureCode() {
        return lotoProcedureCode.get();
    }

    public SimpleStringProperty lotoProcedureCodeProperty() {
        return lotoProcedureCode;
    }

    public void setLotoProcedureCode(String lotoProcedureCode) {
        this.lotoProcedureCode.set(lotoProcedureCode);
    }

    public String getLotoProcedureName() {
        return lotoProcedureName.get();
    }

    public SimpleStringProperty lotoProcedureNameProperty() {
        return lotoProcedureName;
    }

    public void setLotoProcedureName(String lotoProcedureName) {
        this.lotoProcedureName.set(lotoProcedureName);
    }
}
