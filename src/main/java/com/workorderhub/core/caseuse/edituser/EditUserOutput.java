package com.workorderhub.core.caseuse.edituser;

public interface EditUserOutput {
    void displayConfirmation();
    void displayError(EditUserEnum editUserEnum);
    void displayAllUserInformation(SearchUserResponse response);
    void displayUserInformation(SearchUserResponse response);
}
