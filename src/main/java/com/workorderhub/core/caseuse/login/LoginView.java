package com.workorderhub.core.caseuse.login;

public interface LoginView {

    /**
     * Displays a message at the top of the screen when an error occurs.
     *
     * @param message Message to display.
     * @param style   Label style.
     */
    void setTopDisplay(String message, String style);

    /**
     * Closes the running window.
     */
    void closedScreen();
}
