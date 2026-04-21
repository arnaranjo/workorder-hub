package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.edituser.EditUserEnum;
import com.workorderhub.core.caseuse.edituser.EditUserOutput;
import com.workorderhub.core.caseuse.edituser.EditUserView;
import com.workorderhub.core.caseuse.edituser.ResponseSearchUser;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;

public class EditUserPresenter implements EditUserOutput {

    private EditUserView view;

    public void setView(EditUserView view) {
        this.view = view;
    }

    @Override
    public boolean requestConfirmation(EditUserEnum editUserEnum) {
        boolean confirmation = false;
        switch (editUserEnum) {
            case CONFIRM_DELETE_CREDENTIALS:
                String confCredentialsTitle = "editUserView.deletedAccessConfirmation.title";
                String confCredentialsMessage = "editUserView.deletedAccessConfirmation.content";

                confirmation = Util.RequestConfirmation(
                        confCredentialsTitle,
                        confCredentialsMessage
                );
                break;

            case CONFIRM_DELETE_USER:
                String confUserTitle = "editUserView.deletedUserConfirmation.title";
                String confUserMessage = "editUserView.deletedUserConfirmation.content";

                confirmation = Util.RequestConfirmation(
                        confUserTitle,
                        confUserMessage
                );
                break;

            case CONFIRM_UPDATE_USER:
                String confUpdateUserTitle = "editUserView.updatedUserConfirmation.title";
                String confUpdateUserMessage = "editUserView.updatedUserConfirmation.content";

                confirmation = Util.RequestConfirmation(
                        confUpdateUserTitle,
                        confUpdateUserMessage
                );
                break;

        }
        return confirmation;
    }

    @Override
    public void displayConfirmation(EditUserEnum editUserEnum) {
        switch (editUserEnum) {
            case CREDENTIALS_DELETED:
                String credentialsTitle = "editUserView.deletedAccessApproval.title";
                String credentialsMessage = "editUserView.deletedAccessApproval.content";

                Util.RequestConfirmation(
                        credentialsTitle,
                        credentialsMessage
                );
                break;

            case USER_DELETED:
                String userTitle = "editUserView.deletedUserApproval.title";
                String userMessage = "editUserView.deletedUserApproval.content";

                Util.RequestConfirmation(
                        userTitle,
                        userMessage
                );
                break;

            case USER_UPDATED:
                String updateUserTitle = "editUserView.updatedUserApproval.title";
                String updateUserMessage = "editUserView.updatedUserApproval.content";

                Util.RequestConfirmation(
                        updateUserTitle,
                        updateUserMessage
                );
                break;
        }
    }

    @Override
    public void displayError(EditUserEnum editUserEnum) {
        switch (editUserEnum) {
            case USER_NO_FOUND:
                view.setTopDisplay(
                        PropertiesLoader.GetText("editUserView.userNotFound"),
                        PropertiesLoader.GetText("editUserView.failStyle")
                );
                break;

            case NO_ACCESS_CREDENTIALS:
                String noCredentialTitle = "editUserView.deletedAccessError.title";
                String noCredentialMessage = "editUserView.deletedAccessError.content";

                Util.ShowMessage(
                        noCredentialTitle,
                        noCredentialMessage
                );
                break;

            case INCOMPLETE_PASSWORD:
                view.setTopDisplay(
                        PropertiesLoader.GetText("editUserView.wrongUserCredentials"),
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
                        PropertiesLoader.GetText("editUserView.wrongPassword"),
                        PropertiesLoader.GetText("editUserView.failStyle")
                );
                break;

            case CREDENTIALS_DELETION_ERROR:
                String credentialDeleteErrTitle = "editUserView.deletedAccessError.title";
                String credentialDeleteErrMessage = "editUserView.deletedAccessError.deletion";

                Util.ShowMessage(
                        credentialDeleteErrTitle,
                        credentialDeleteErrMessage
                );
                break;

            case USER_DELETION_ERROR:
                String userDeleteErrTitle = "editUserView.deletedUserError.title";
                String userDeleteErrMessage = "editUserView.deletedUserError.deletion";

                Util.ShowMessage(
                        userDeleteErrTitle,
                        userDeleteErrMessage
                );
                break;

            case USER_UPDATE_ERROR:
                String userUpdateErrTitle = "editUserView.updatedUserError.title";
                String userUpdateErrMessage = "editUserView.updatedUserError.deletion";

                Util.ShowMessage(
                        userUpdateErrTitle,
                        userUpdateErrMessage
                );
                break;
        }
    }

    @Override
    public void displayAllUserInformation(ResponseSearchUser response) {
        view.setNewNameText(response.userName());
        view.setNewPhoneText(response.userPhone());
        view.setNewEmailText(response.userEmail());
        view.setRoleName(response.roleId());
        view.activateCredentials();
        view.setNewLoginName(response.userLoginName());
        view.setNewPassword(response.userPassword());
        view.setConfNewPassword(response.userPassword());
    }

    @Override
    public void displayUserInformation(ResponseSearchUser response) {
        view.setNewNameText(response.userName());
        view.setNewPhoneText(response.userPhone());
        view.setNewEmailText(response.userEmail());
        view.setRoleName(response.roleId());
        view.deactivateCredentials();
    }

    @Override
    public void resetFields() {
        view.setDefaultView();

        view.setNameText("");
        view.setEmail("");
        view.setNewNameText("");
        view.setNewPhoneText("");
        view.setNewEmailText("");
    }
}
