package com.workorderhub.core.caseuse.login;

import com.workorderhub.core.entity.Credentials;
import com.workorderhub.core.entity.User;
import com.workorderhub.core.gateway.CredentialsGateway;
import com.workorderhub.core.gateway.UserGateway;

public class LoginInteractor implements LoginInput {

    private UserGateway userGateway;
    private CredentialsGateway credentialsGateway;
    private LoginOutput output;
    private LoginResponse response;

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
    public void grantAccess(LoginRequest request) {
        Credentials credentials = new Credentials(request.userName, request.accessKey);
        int id = credentialsGateway.GetCredentialsId(credentials);

        if (id == -1) {
            output.DisplayUserNoFound();

        } else {
            User user = userGateway.GetUserByCredentials(id);
            LoginResponse response = new LoginResponse(user.getUserName(), user.getIdRol());
            output.LoadView(response);

        }
    }
}

