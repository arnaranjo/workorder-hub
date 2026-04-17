package com.workorderhub.core.entity;

public enum StatusEnum {
    OPEN("Open"),
    ONGOING("On going"),
    CLOSED("Closed");

    private final String status;

    StatusEnum(String status){
        this.status = status;
    }

    public String GetStatus(){
        return status;
    }
}
