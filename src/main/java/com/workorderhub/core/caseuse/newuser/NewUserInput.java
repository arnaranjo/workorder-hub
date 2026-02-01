package com.workorderhub.core.caseuse.newuser;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface NewUserInput {

    /**
     * Inserts a new user in the database.
     *
     * @param request the information to create a new user. If the username and password are included,
     *                the credentials will also be inserted.
     */
    void createNewUser(RequestNewUser request);

    /**
     * Gets the user role list to fill the selector in the UI.
     *
     * @return List of UserRoles objects.
     */
    List<UserRole> getUserRoleList();
}
