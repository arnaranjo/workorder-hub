package com.workorderhub.core.caseuse.edituser;

public record ResponseSearchUser(
        String userName,
        String userEmail,
        String userPhone,
        int roleId,
        String userLoginName,
        String userPassword
) {}
