package com.workorderhub.core.caseuse.edituser;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface EditUserInput {
    List<UserRole> getUserRoleList();
    void searchUser(SearchUserRequest request);
    void editUser(EditUserRequest request);
    void deleteUser(SearchUserRequest request);
    void editUserCredentials(EditCredentialsRequest request);
    void deleteCredentials(EditCredentialsRequest request);
}
