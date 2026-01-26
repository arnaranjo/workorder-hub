package com.workorderhub.core.caseuse.edituser;

import com.workorderhub.core.entity.Credentials;
import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRole;
import com.workorderhub.core.gateway.CredentialsGateway;
import com.workorderhub.core.gateway.UserGateway;
import com.workorderhub.core.gateway.UserRoleGateway;

import java.util.List;

public class EditUserInteractor implements EditUserInput{

    SearchUserRequest searchUserRequest;
    EditUserRequest editUserRequest;
    EditCredentialsRequest editCredentialsRequest;

    EditUserOutput output;
    UserGateway userGateway;
    CredentialsGateway credentialsGateway;
    UserRoleGateway userRoleGateway;

    public EditUserInteractor(
            EditUserOutput output,
            UserGateway userGateway,
            CredentialsGateway credentialsGateway,
            UserRoleGateway userRoleGateway
    ){
        this.output = output;
        this.userGateway = userGateway;
        this.credentialsGateway = credentialsGateway;
        this.userRoleGateway = userRoleGateway;
    }

    @Override
    public List<UserRole> getUserRoleList() {
        List<UserRole> userRoleList;
        userRoleList = userRoleGateway.GetAllRoles();
        return userRoleList;
    }

    @Override
    public void searchUser(SearchUserRequest request) {
        User userFound = userGateway.getUser(request.userName(), request.userEmail());
        if (userFound != null && userFound.getIdAccess() != 0) {
            Credentials userCredentials = credentialsGateway.getCredentialsById(userFound.getIdAccess());

            SearchUserResponse response = new SearchUserResponse(
                    userFound.getUserName(),
                    userFound.getUserEmail(),
                    userFound.getUserPhoneNumber(),
                    userFound.getIdRol(),
                    userCredentials.getName(),
                    userCredentials.getAccessKey()
            );
            output.displayAllUserInformation(response);

        } else if (userFound != null && userFound.getIdAccess() == 0){
            SearchUserResponse response = new SearchUserResponse(
                    userFound.getUserName(),
                    userFound.getUserEmail(),
                    userFound.getUserPhoneNumber(),
                    userFound.getIdRol(),
                    "",
                    ""
            );
            output.displayUserInformation(response);

        } else {
            output.displayError(EditUserEnum.USER_NO_FOUND);
        }
    }

    @Override
    public void editUser(EditUserRequest request) {

    }

    @Override
    public void deleteUser(SearchUserRequest request) {

    }

    @Override
    public void editUserCredentials(EditCredentialsRequest request) {

    }

    @Override
    public void deleteCredentials(EditCredentialsRequest request) {
        if (!request.loginName().isEmpty() && !request.password().isEmpty()){
            Credentials credentials = new Credentials(request.loginName(), request.password());

            int userCredentialsId = credentialsGateway.getCredentialsId(credentials);
            credentials.setAccessId(userCredentialsId);

            if (output.requestConfirmation(EditUserEnum.CONFIRM_DELETE_CREDENTIALS)){
                if (credentialsGateway.deleteCredentials(credentials)){
                    output.displayConfirmation(EditUserEnum.CREDENTIALS_DELETED);
                }
                else {
                    output.displayError(EditUserEnum.CREDENTIALS_DELETION_ERROR);
                }
            }
        }
        else {
            output.displayError(EditUserEnum.NO_ACCESS_CREDENTIALS);
        }
    }
}
