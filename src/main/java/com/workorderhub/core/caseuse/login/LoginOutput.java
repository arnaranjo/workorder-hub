package com.workorderhub.core.caseuse.login;

import com.workorderhub.core.entity.UserRoleEnum;

public interface LoginOutput {
    void displayUserNoFound();
    void loadView(ResponseLogin response, UserRoleEnum userRole);
}
