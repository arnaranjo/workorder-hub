package com.workorderhub.core.caseuse.technician;

public record ResponseHolderInfo(
        int userId,
        String userName,
        String userPhoneNumber,
        String userEmail
) {
}
