package com.workorderhub.provider.common;


import com.workorderhub.provider.models.StatusModel;

import java.util.List;

/**
 * AppState loads fixed lists, object references or catalogs to make them available in the app.
 * CODE REFERENCE
 * 1 - <a href="https://dev.to/amanshaw4511/dont-let-your-singleton-break-heres-how-to-make-it-100-thread-safe-in-java-1k57">Singleton threat-safe<a/>
 */
public final class AppState {

    private static volatile AppState instance;
    private String loggedUser;
    private List<StatusModel> workOrderStatusList;
    private long workOrderId;

    /**
     * REF. [1]
     */
    public static AppState getInstance() {
        if (instance == null) {
            synchronized (AppState.class) {
                if (instance == null) {
                    instance = new AppState();
                }
            }
        }
        return instance;
    }

    public void setLoggedUser(String loggedUser){
        this.loggedUser = loggedUser;
    }

    public String getLoggedUser(){
        return loggedUser;
    }

    public List<StatusModel> getWorkOrderStatusList() {
        return workOrderStatusList;
    }

    public void setWorkOrderStatusList(List<StatusModel> workOrderStatusList) {
        this.workOrderStatusList = workOrderStatusList;
    }

    public long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(long workOrderId) {
        this.workOrderId = workOrderId;
    }
}
