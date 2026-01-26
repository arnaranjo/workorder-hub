package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.edituser.EditUserEnum;
import com.workorderhub.core.caseuse.edituser.EditUserOutput;
import com.workorderhub.core.caseuse.edituser.SearchUserResponse;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;

public class EditUserPresenter implements EditUserOutput {

    private EditUserView view;

    public void setView(EditUserView view){
        this.view = view;
    }

    @Override
    public void displayConfirmation() {
        String titleConf = "editUserView.updatedUserConfirmation.title";
        String contentConf = "editUserView.updatedUserConfirmation.content";
        Util.ShowMessage(titleConf, contentConf);
    }

    @Override
    public void displayError(EditUserEnum editUserEnum) {
        switch (editUserEnum){
            case USER_NO_FOUND:
                view.setTopDisplay(
                        PropertiesLoader.GetText("editUserView.userNotFound"),
                        PropertiesLoader.GetText("editUserView.failStyle")
                );
                break;

            case INCOMPLETE_INFORMATION:
                view.setTopDisplay(
                        PropertiesLoader.GetText("editUserView.wrongUserInfo"),
                        PropertiesLoader.GetText("editUserView.failStyle")
                );
            break;

            case PASSWORD_DO_NOT_MATCH:
                view.setTopDisplay(
                        PropertiesLoader.GetText("editUserView.wrongUserCredentials"),
                        PropertiesLoader.GetText("editUserView.failStyle")
                );
                break;
        }
    }

    @Override
    public void displayAllUserInformation(SearchUserResponse response) {
        view.setNewNameText(response.userName());
        view.setNewPhoneText(response.userPhone());
        view.setNewEmailText(response.userEmail());
        view.setRoleName(response.roleId());
        view.activateCredentials();
        view.setNewLoginName(response.userLoginName());
        view.setNewPassword(response.userPassword());
    }

    @Override
    public void displayUserInformation(SearchUserResponse response) {
        view.setNewNameText(response.userName());
        view.setNewPhoneText(response.userPhone());
        view.setNewEmailText(response.userEmail());
        view.setRoleName(response.roleId());
        view.deactivateCredentials();
    }
}
