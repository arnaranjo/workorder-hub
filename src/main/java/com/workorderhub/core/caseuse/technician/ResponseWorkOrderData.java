package com.workorderhub.core.caseuse.technician;

import java.time.LocalDate;
import java.util.List;

public record ResponseWorkOrderData(
        String workOrderDescripcion,
        String workOrderStatus,
        LocalDate startDate,
        LocalDate endDate,
        List<String> workOrderCategoryList
) {
}
