package com.workorderhub.infrastructure.common;


/**
 * AppState loads fixed lists, object references or catalogs to make them available in the app.
 * CODE REFERENCE
 * 1 - <a href="https://dev.to/amanshaw4511/dont-let-your-singleton-break-heres-how-to-make-it-100-thread-safe-in-java-1k57">Singleton threat-safe<a/>
 */
public final class AppState {

    private static volatile AppState instance;
    private String loggedUser = null;
    private int loggedUserId = 0;
    private long workOrderId = 0;
    private boolean adminMode = false;
    private boolean supervisorMode = false;

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
     * @param loggedUserId the ID of the logged user to set
     */
    public void setLoggedUser(String loggedUser, int loggedUserId){
        this.loggedUser = loggedUser;
        this.loggedUserId = loggedUserId;
    }

    /**
     * Gets the logged user name from the app state.
      * @return the name of the logged user, or null if no user is logged in
     */
    public String getLoggedUser(){
        return loggedUser;
    }

    /**
     * Gets the logged user ID from the app state.
     * @return the ID of the logged user, or 0 if no user is logged in
     */
    public int getLoggedUserId() {
        return loggedUserId;
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
     * Gets whether the app is currently in supervisor mode.
     * @return true when supervisor mode is active, false otherwise.
     */
    public boolean isSupervisorMode() {
        return supervisorMode;
    }

    /**
     * Gets whether the app is currently in admin mode.
     * @return true when admin mode is active, false otherwise.
     */
    public boolean isAdminMode() {
        return adminMode;
    }

    /**
     * Sets admin mode in the app state.
     * @param adminMode true to enable admin mode, false to disable it.
     */
    public void setAdminMode(boolean adminMode) {
        this.adminMode = adminMode;
    }

    /**
     * Sets supervisor mode in the app state.
     * @param supervisorMode true to enable supervisor mode, false to disable it.
     */
    public void setSupervisorMode(boolean supervisorMode) {
        this.supervisorMode = supervisorMode;
    }

    /**
     * Resets work order context in the app state.
     * It clears the selected work order and disables admin/supervisor modes.
     */
    public void resetWorkOrder(){
        this.workOrderId = 0;
        this.adminMode = false;
        this.supervisorMode = false;
    }
}
