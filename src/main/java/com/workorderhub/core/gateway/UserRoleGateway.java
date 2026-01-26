package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface UserRoleGateway {
    List<UserRole> GetAllRoles();
    UserRole GetRole(String role);
    int InsertRole(String roleName);
    boolean DeleteRole(String roleName);
    boolean UpdateRole(UserRole role);
}
