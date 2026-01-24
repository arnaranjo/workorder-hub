package com.workorderhub.provider.common;


/**
 * AppState loads fixed lists, object references or catalogs to make them available in the app.
 * CODE REFERENCE
 * 1 - <a href="https://dev.to/amanshaw4511/dont-let-your-singleton-break-heres-how-to-make-it-100-thread-safe-in-java-1k57">Singleton threat-safe<a/>
 */
public final class AppState {

    private static volatile AppState instance;
    private String loggedUser;
    //private List<Status> workOrderStatusList;
    //private List<Category> categoryList;
    //private long workOrderId;

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

}
