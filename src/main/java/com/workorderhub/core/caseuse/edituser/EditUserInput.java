package com.workorderhub.core.caseuse.edituser;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface EditUserInput {
    List<UserRole> getUserRoleList();
    void searchUser(RequestSearchUser request);
    void editUser(RequestEditUser request);
    void deleteUser(RequestSearchUser request);
    int insertUserCredentials(RequestEditUser request);
    boolean editUserCredentials(RequestEditUser request);
    void deleteCredentials(RequestDeleteCredentials request);
}
