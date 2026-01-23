package com.workorderhub.core.entity;

public enum UserRoleEnum {
    MANAGER("Manager"),
    SUPERVISOR("Supervisor"),
    TECHNICIAN("Technician");

    private final String userRole;

    UserRoleEnum(String userRole) {
        this.userRole = userRole;
    }

    public String GetRoleName(){
        return this.userRole;
    }
}
