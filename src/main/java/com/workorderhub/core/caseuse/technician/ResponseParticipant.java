package com.workorderhub.core.caseuse.technician;

public record ResponseParticipant(
        int userId,
        String userName,
        String userEmail,
        String userPhone
) {
}
