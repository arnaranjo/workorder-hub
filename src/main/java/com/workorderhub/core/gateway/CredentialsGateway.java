package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.Credentials;

public interface CredentialsGateway {
    int GetCredentialsId(Credentials credentials);
    Credentials GetCredentialsById(int id);
    int InsertCredentials(Credentials credentials);
    boolean DeleteCredentials(Credentials credentials);
    boolean UpdateCredentials(Credentials credentials);
}
