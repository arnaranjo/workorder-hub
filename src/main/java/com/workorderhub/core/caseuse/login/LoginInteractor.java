package com.workorderhub.core.caseuse.login;

import com.workorderhub.core.entity.Credentials;
import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRoleEnum;
import com.workorderhub.core.gateway.CredentialsGateway;
import com.workorderhub.core.gateway.UserGateway;

public class LoginInteractor implements LoginInput {

    private UserGateway userGateway;
    private CredentialsGateway credentialsGateway;
    private LoginOutput output;
    private ResponseLogin response;

    public LoginInteractor(
            UserGateway userGateway,
            CredentialsGateway credentialsGateway,
            LoginOutput output
    ) {
        this.userGateway = userGateway;
        this.credentialsGateway = credentialsGateway;
        this.output = output;
    }

    @Override
    public void grantAccess(RequestLogin request) {
        Credentials credentials = new Credentials(request.userName, request.accessKey);
        int id = credentialsGateway.getCredentialsId(credentials);

        if (id == -1) {
            output.displayUserNoFound();

        } else {
            User user = userGateway.getUserByCredentials(id);
            response = new ResponseLogin(user.getUserName());

            switch (user.getIdRol()) {
                case 1:
                    output.loadView(response, UserRoleEnum.MANAGER);
                    break;

                case 2:
                    output.loadView(response, UserRoleEnum.SUPERVISOR);
                    break;

                case 3:
                    output.loadView(response, UserRoleEnum.TECHNICIAN);
                    break;
            }
        }
    }
}

