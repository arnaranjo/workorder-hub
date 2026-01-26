package com.workorderhub.core.caseuse.edituser;

public record EditCredentialsRequest(
        String loginName,
        String password
) {}
