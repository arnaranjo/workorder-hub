package com.workorderhub.core.caseuse.newuser;

public record NewUserRequest(
    String userName,
    String userPhoneNumber,
    String userEmail,
    Integer userRoleId,
    String userLoginName,
    String userPassword
    ){}
