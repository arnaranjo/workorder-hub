package com.workorderhub.core.caseuse.edituser;

public record SearchUserResponse(
        String userName,
        String userEmail,
        String userPhone,
        int roleId,
        String userLoginName,
        String userPassword
) {}
