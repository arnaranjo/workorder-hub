package com.workorderhub.core.caseuse.edituser;

public record RequestDeleteCredentials(
        String loginName,
        String password
) {}
