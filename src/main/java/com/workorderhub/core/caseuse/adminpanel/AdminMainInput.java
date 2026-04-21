package com.workorderhub.core.caseuse.adminpanel;

public interface AdminMainInput {

    void retrieveWorkFronts();

    void retrieveClosedOrders(RequestClosedOrders request);

    void retrieveWorkLogs(RequestWorkLogs request);

}
