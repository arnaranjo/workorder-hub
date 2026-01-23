package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.Credentials;

public interface CredentialsGateway {
    int getCredentialsId(Credentials credentials);
    Credentials getCredentialsById(int id);
    int insertCredentials(Credentials credentials);
    boolean deleteCredentials(Credentials credentials);
    boolean updateCredentials(Credentials credentials);
}
