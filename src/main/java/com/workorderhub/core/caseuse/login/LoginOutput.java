package com.workorderhub.core.caseuse.login;

import com.workorderhub.core.entity.UserRoleEnum;

public interface LoginOutput {
    void displayUserNoFound();
    void loadView(LoginResponse response, UserRoleEnum userRole);
}
