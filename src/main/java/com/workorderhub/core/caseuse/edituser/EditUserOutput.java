package com.workorderhub.core.caseuse.edituser;

public interface EditUserOutput {

    /**
     * Displays a pop-up message to confirm an action.
     *
     * @param editUserEnum Request confirmation to remove and user, credentials, and edit and user.
     * @return True if the request was accepted, false if not.
     */
    boolean requestConfirmation(EditUserEnum editUserEnum);

    /**
     * Displays a message when the action is completed successfully.
     *
     * @param editUserEnum Display the message when the user is updated or removed, and credentials removed.
     */
    void displayConfirmation(EditUserEnum editUserEnum);

    /**
     * Displays a message when the action is not completed.
     *
     * @param editUserEnum Displays the message when errors occur such as user not found, password not matching,
     *                     or database failure.
     */
    void displayError(EditUserEnum editUserEnum);

    /**
     * Displays user and credential information in the UI.
     *
     * @param response User information.
     */
    void displayAllUserInformation(ResponseSearchUser response);

    /**
     * Displays only user information in the UI. Used when users do not have credentials.
     *
     * @param response User information without credentials.
     */
    void displayUserInformation(ResponseSearchUser response);

    /**
     * Resets the UI and texts fields.
     */
    void resetFields();
}
