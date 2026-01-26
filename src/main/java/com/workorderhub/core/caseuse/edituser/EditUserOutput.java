package com.workorderhub.core.caseuse.edituser;

public interface EditUserOutput {
    boolean requestConfirmation(EditUserEnum editUserEnum);
    void displayConfirmation(EditUserEnum editUserEnum);
    void displayError(EditUserEnum editUserEnum);
    void displayAllUserInformation(SearchUserResponse response);
    void displayUserInformation(SearchUserResponse response);
}
