package com.workorderhub.core.caseuse.edituser;

public interface EditUserView {

    /**
     * Sets the default messages and clear the text fields.
     */
    void setDefaultView();

    /**
     * Request the user role list to add it to the selector.
     */
    void getUserRoleList();

    /**
     * Displays a message at the top of the screen when an error occurs.
     *
     * @param message Message to display.
     * @param style   Label style.
     */
    void setTopDisplay(String message, String style);

    /**
     * Sets the current username in the search name field.
     *
     * @param name username.
     */
    void setNameText(String name);

    /**
     * Sets the current email in the search email field.
     *
     * @param email user email.
     */
    void setEmail(String email);

    /**
     * Sets the new username, it is used to update the user information.
     *
     * @param name New username.
     */
    void setNewNameText(String name);

    /**
     * Sets the new user phone contact, it is used to update the user information.
     *
     * @param phone New user phone contact.
     */
    void setNewPhoneText(String phone);

    /**
     * Sets the new user email contact, it is used to update the user information.
     *
     * @param email New user email contact
     */
    void setNewEmailText(String email);

    /**
     * Sets the role name in the selector, when the user data is loaded.
     * The role name is retrieved from the role list.
     *
     * @param roleId User role ID.
     */
    void setRoleName(int roleId);

    /**
     * Sets the new login name, It is used to edit or add credentials to the user.
     *
     * @param loginName New login name.
     */
    void setNewLoginName(String loginName);

    /**
     * Sets the new password, It is used to edit or add credentials to the user.
     *
     * @param password New password.
     */
    void setNewPassword(String password);

    /**
     * Sets the password to confirm the new password.
     *
     * @param password New password.
     */
    void setConfNewPassword(String password);

    /**
     * Enables the credentials text fields.
     * It is used to add new credentials or display the credentials of a retrieved user.
     */
    void activateCredentials();

    /**
     * Disables the credentials text fields.
     */
    void deactivateCredentials();
}
