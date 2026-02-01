package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.UserRole;

import java.util.List;

public interface UserRoleGateway {

    /**
     * Gets all user roles.
     *
     * @return List of user roles.
     */
    List<UserRole> GetAllRoles();

    /**
     * Gets the user role by the role name.
     *
     * @param role Role name.
     * @return User role object.
     */
    UserRole GetRole(String role);

    int InsertRole(String roleName);
    boolean DeleteRole(String roleName);
    boolean UpdateRole(UserRole role);
}
