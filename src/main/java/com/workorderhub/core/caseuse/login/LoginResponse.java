package com.workorderhub.core.caseuse.login;

public class LoginResponse {
    String userName;

    public LoginResponse(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
