package com.workorderhub.core.caseuse.adminpanel;

import java.time.LocalDate;

public record RequestClosedOrders(
        LocalDate startDate
) {
}
