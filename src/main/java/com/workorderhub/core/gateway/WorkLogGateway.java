package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.WorkLogElement;
import com.workorderhub.core.entity.WorkLogInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkLogGateway {
    /**
     * Retrieves a list of WorkLogElement objects that have a start date greater than or equal to the specified startDate.
     * @param startDate The LocalDateTime object representing the start date to filter the WorkLogElement objects.
     * @return A list of WorkLogElement objects that match the specified criteria.
     */
    List<WorkLogElement> getWorkLogElementList(LocalDateTime startDate);

    /**
     * Retrieves a list of WorkLogInfo objects.
     * @return A list of WorkLogInfo objects representing the work log information.
     */
    List<WorkLogInfo> getWorkLogInfoList();

    /**
     * Inserts a new WorkLogInfo object into the data source.
     * @param workLogInfo The WorkLogInfo object containing the information to be inserted into the data source.
     */
    void insertWorkLogInfo(WorkLogInfo workLogInfo);

    /**
     * Deletes a WorkLogInfo object from the data source.
     * @param workLogInfo The WorkLogInfo object containing the information to be deleted from the data source.
     * @return True is the WorkLogInfo object was successfully deleted, false otherwise.
     */
    boolean deleteWorkLogInfo(WorkLogInfo workLogInfo);

}
