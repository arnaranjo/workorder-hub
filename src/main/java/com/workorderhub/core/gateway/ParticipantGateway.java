package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.Participant;

import java.util.List;

public interface ParticipantGateway {

    /**
     * Retrieves a list of participants associated with a specific work order.
     * @param workOrderId ID of the work order.
     * @return List of participants involved in the work order.
     */
    List<Participant> getParticipantsByWorkOrder(long workOrderId);

    /**
     * Adds a new participant to a work order.
     * @param participant The participant to be added.
     * @return True if the participant was successfully added, false otherwise.
     */
    boolean insertParticipant(Participant participant);

    /**
     * Adds multiple participants to a work order in a single operation.
     * @param participantList List of participants to be added.
     * @return True if all participants were successfully added, false otherwise.
     */
    boolean insertParticipantBatch(List<Participant> participantList);

    /**
     * Removes a participant from a work order.
     * @param participant The participant to be removed.
     * @return True if the participant was successfully removed, false otherwise.
     */
    boolean deleteParticipant(Participant participant);

    /**
     * Updates the details of an existing participant in a work order.
     * @param participant The participant with updated information.
     * @param newParticipant The new participant information to replace the existing participant.
     * @return True if the participant was successfully updated, false otherwise.
     */
    boolean updateParticipant(Participant participant, int newParticipant);
}
