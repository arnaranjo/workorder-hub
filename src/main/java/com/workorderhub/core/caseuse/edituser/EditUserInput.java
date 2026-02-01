package com.workorderhub.core.caseuse.edituser;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface EditUserInput {

    /**
     * Gets the user role list to fill the selector in the UI.
     *
     * @return List of UserRoles objects.
     */
    List<UserRole> getUserRoleList();

    /**
     * Searches function looks for the user to load basic information and credentials if the user has them.
     *
     * @param request It provides the username and email.
     */
    void searchUser(RequestSearchUser request);

    /**
     * Edits all user information. If the user has no credentials, but a username and password are requested,
     * It must also insert the credentials into the database.
     *
     * @param request It provides the new name, email address, phone number and, if applicable, username and password.
     */
    void editUser(RequestEditUser request);

    /**
     * Removes the user information and credentials if user has them.
     *
     * @param request It provides the username and email.
     */
    void deleteUser(RequestSearchUser request);

    /**
     * Inserts a new credentials in the database.
     *
     * @param request It provides new credentials.
     * @return ID of new credentials inserted to set it on the user.
     */
    int insertUserCredentials(RequestEditUser request);

    /**
     * Updated the user credentials in the database.
     *
     * @param request It provides new credentials.
     * @return True if the update was successful.
     */
    boolean editUserCredentials(RequestEditUser request);

    /**
     * Remove the user credentials from the database
     *
     * @param request It provides the credentials to remove.
     */
    void deleteCredentials(RequestDeleteCredentials request);
}
