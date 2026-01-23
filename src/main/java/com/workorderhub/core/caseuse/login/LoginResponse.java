package com.workorderhub.core.caseuse.login;

public class LoginResponse {
    String userName;
    int rolId;

    public LoginResponse(String userName, int rolId) {
        this.userName = userName;
        this.rolId = rolId;
    }

    public String getUserName() {
        return userName;
    }

    public int getRolId() {
        return rolId;
    }
}
