package com.workorderhub.core.caseuse.workorder;

public record ResponseHolderInfo(
        int userId,
        String userName,
        String userPhoneNumber,
        String userEmail
) {
}
