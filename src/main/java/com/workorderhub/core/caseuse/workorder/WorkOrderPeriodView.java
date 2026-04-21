package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.infrastructure.models.WorkFrontModel;

import java.time.LocalDate;
import java.util.List;

public interface WorkOrderPeriodView {

    /**
     * Displays the list of work fronts that are open and in progress.
     *
     * @param workFrontModelList List of work fronts to be displayed.
     */
    void setWorkFrontList(List<WorkFrontModel> workFrontModelList);

    /**
     * Indicates whether the user has selected a valid period for the work order.
     *
     * @return true if the user has selected a valid period, false otherwise.
     */
    public boolean isPeriodSelected();

    /**
     * Retrieves the start date of the valid period selected for the work order.
     *
     * @return the start date of the valid period selected for the work order,
     * null if no valid period has been selected.
     */
    public LocalDate getStartDate();

    /**
     * Retrieves the end date of the valid period selected for the work order.
     *
     * @return the end date of the valid period selected for the work order,
     * null if no valid period has been selected.
     */
    public LocalDate getEndDate();
}
