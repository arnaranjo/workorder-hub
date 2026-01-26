package com.workorderhub.core.caseuse.newuser;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface NewUserInput {
    void createNewUser(NewUserRequest request);
    List<UserRole> getUserRoleList();
}
