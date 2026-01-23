package com.workorderhub.core.caseuse.login;

public class LoginRequest {
    String userName;
    String accessKey;

    public LoginRequest(String userName, String accessKey) {
        this.userName = userName;
        this.accessKey = accessKey;
    }

    public String getUserName() {
        return userName;
    }

    public String getAccessKey() {
        return accessKey;
    }
}
