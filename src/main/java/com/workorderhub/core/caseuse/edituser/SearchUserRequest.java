package com.workorderhub.core.caseuse.edituser;

public record SearchUserRequest(
        String userName,
        String userEmail
) {}
