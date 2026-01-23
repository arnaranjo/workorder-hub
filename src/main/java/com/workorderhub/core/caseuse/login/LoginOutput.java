package com.workorderhub.core.caseuse.login;

public interface LoginOutput {
    public void DisplayUserNoFound();
    public void LoadView(LoginResponse response);
    public void ClosedCurrentScreen();
}
