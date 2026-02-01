package com.workorderhub.core.caseuse.edituser;

public record RequestEditUser(
        String name,
        String phoneNumber,
        String email,
        String roleName,
        String loginName,
        String password,
        String confPassword
) {
}
