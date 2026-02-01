package com.workorderhub.core.caseuse.login;

public class ResponseLogin {
    String userName;

    public ResponseLogin(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
