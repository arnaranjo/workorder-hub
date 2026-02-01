package com.workorderhub.core.caseuse.login;

public record RequestLogin(
        String userName,
        String accessKey
) {
}
