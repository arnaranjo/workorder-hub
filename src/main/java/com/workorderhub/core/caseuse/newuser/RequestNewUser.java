package com.workorderhub.core.caseuse.newuser;

public record RequestNewUser(
    String userName,
    String userPhoneNumber,
    String userEmail,
    int userRoleId,
    String userLoginName,
    String userPassword
    ){}
