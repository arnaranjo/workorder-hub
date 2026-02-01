package com.workorderhub.core.caseuse.login;

public interface LoginInput {

    /**
     * Requests the access to the user.
     *
     * @param request It provides the user login name and password.
     */
    void grantAccess(RequestLogin request);
}
