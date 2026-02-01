package com.workorderhub.core.caseuse.newuser;

import com.workorderhub.core.entity.Credentials;
import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRole;
import com.workorderhub.core.gateway.CredentialsGateway;
import com.workorderhub.core.gateway.UserGateway;
import com.workorderhub.core.gateway.UserRoleGateway;

import java.util.List;

public class NewUserInteractor implements NewUserInput{

    NewUserOutput output;
    UserGateway userGateway;
    CredentialsGateway credentialsGateway;
    UserRoleGateway userRoleGateway;

    public NewUserInteractor (
            NewUserOutput output,
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
    public void createNewUser(RequestNewUser request) {
        if (request.userLoginName() != null){

            Credentials newCredentials = new Credentials(
                    request.userLoginName(),
                    request.userPassword()
            );
            int credentialId = credentialsGateway.insertCredentials(newCredentials);

            User newUser = new User(
                    0,
                request.userName(),
                request.userPhoneNumber(),
                request.userEmail(),
                request.userRoleId(),
                credentialId
            );
            if (userGateway.insertUser(newUser) != 0 ){
                output.displayConfirmation();
            }
        }
        else {
            User newUser = new User(
                    0,
                    request.userName(),
                    request.userPhoneNumber(),
                    request.userEmail(),
                    request.userRoleId(),
                    0
            );
            if (userGateway.insertUser(newUser) != 0 ){
                output.displayConfirmation();
            }
        }
    }

    @Override
    public List<UserRole> getUserRoleList() {
        List<UserRole> userRoleList;
        userRoleList = userRoleGateway.GetAllRoles();
        return userRoleList;
    }
}
