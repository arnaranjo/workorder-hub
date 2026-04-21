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
    private String loggedUser = null;
    private List<StatusModel> workOrderStatusList;
    private long workOrderId = 0;

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

    private AppState() {}

    /**
     * Sets the logged user name in the app state.
     * @param loggedUser the name of the logged user to set
     */
    public void setLoggedUser(String loggedUser){
        this.loggedUser = loggedUser;
    }

    /**
     * Gets the logged user name from the app state.
      * @return the name of the logged user, or null if no user is logged in
     */
    public String getLoggedUser(){
        return loggedUser;
    }

    public List<StatusModel> getWorkOrderStatusList() {
        return workOrderStatusList;
    }

    public void setWorkOrderStatusList(List<StatusModel> workOrderStatusList) {
        this.workOrderStatusList = workOrderStatusList;
    }

    /**
     * Gets the work order ID from the app state.
     * @return the work order ID, or 0 if no work order ID is set
     */
    public long getWorkOrderId() {
        return workOrderId;
    }

    /**
     * Sets the work order ID in the app state.
     * @param workOrderId the work order ID to set
     */
    public void setWorkOrderId(long workOrderId) {
        this.workOrderId = workOrderId;
    }

    /**
     * Resets the work order ID in the app state.
     */
    public void resetWorkOrder(){
        this.workOrderId = 0;
    }
}
