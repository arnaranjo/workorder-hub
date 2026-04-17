package com.workorderhub.core.caseuse.workorder;

public record RequestParticipants(
        int userId,
        String employeeName,
        String employeeEmail,
        String employeePhoneNumber
) {
}
