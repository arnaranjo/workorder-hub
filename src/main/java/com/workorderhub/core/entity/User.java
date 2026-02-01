package com.workorderhub.core.entity;

/**
 * Represents the company's employees, who are the end users of the application.
 */
public class User {

    private int userId;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private int idRol;
    private int idAccess;

    public User(
            int userId,
            String userName,
            String userPhoneNumber,
            String userEmail,
            int idRol,
            int idAccess
    ) {
        this.userId = userId;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmail = userEmail;
        this.idRol = idRol;
        this.idAccess = idAccess;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdAccess() {
        return idAccess;
    }

    public void setIdAccess(int idAccess) {
        this.idAccess = idAccess;
    }
}
