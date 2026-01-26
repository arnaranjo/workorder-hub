package com.workorderhub.core.entity;

/**
 * Represents the rol that an employee has in the company.
 * @version 1.0
 * @since 2024
 */
public class UserRole {

    private int roleId;
    private String roleName;

    /**
     * Retrieves the identification (ID) of the role.
     * @return the ID of the role.
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets the identification (ID) of the role.
     * @param roleId the ID of the role.
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Retrieves the name of the role.
     * @return the name of the role.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the name of the role.
     * @param roleName the name of the role.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}