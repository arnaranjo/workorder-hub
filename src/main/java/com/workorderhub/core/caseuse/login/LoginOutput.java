package com.workorderhub.core.caseuse.login;

import com.workorderhub.core.entity.UserRoleEnum;

public interface LoginOutput {

    /**
     * Displays a message at the top of the screen is the user is not found.
     */
    void displayUserNoFound();

    /**
     * Loads the window according to the user role provided.
     *
     * @param response Username loaded in the system.
     * @param userRole User role to load the proper window.
     */
    void loadView(ResponseLogin response, UserRoleEnum userRole);
}
