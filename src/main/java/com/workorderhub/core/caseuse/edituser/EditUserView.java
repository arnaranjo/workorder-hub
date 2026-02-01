package com.workorderhub.core.caseuse.edituser;

public interface EditUserView {
    void setDefaultView();
    void getUserRoleList();
    void setTopDisplay(String message, String style);
    void setNameText(String name);
    void setEmail(String email);
    void setNewNameText(String name);
    void setNewPhoneText(String phone);
    void setNewEmailText(String email);
    void setRoleName(int roleId);
    void setNewLoginName(String loginName);
    void setNewPassword(String password);
    void setConfNewPassword(String password);
    void activateCredentials();
    void deactivateCredentials();
}
