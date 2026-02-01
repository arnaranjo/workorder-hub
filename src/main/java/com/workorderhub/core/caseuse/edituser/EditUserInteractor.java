package com.workorderhub.core.caseuse.edituser;

import com.workorderhub.core.entity.Credentials;
import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRole;
import com.workorderhub.core.gateway.CredentialsGateway;
import com.workorderhub.core.gateway.UserGateway;
import com.workorderhub.core.gateway.UserRoleGateway;

import java.util.List;

public class EditUserInteractor implements EditUserInput {

    private User userFound = null;

    EditUserOutput output;
    UserGateway userGateway;
    CredentialsGateway credentialsGateway;
    UserRoleGateway userRoleGateway;

    public EditUserInteractor(
            EditUserOutput output,
            UserGateway userGateway,
            CredentialsGateway credentialsGateway,
            UserRoleGateway userRoleGateway
    ) {
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
    public void searchUser(RequestSearchUser request) {
        userFound = userGateway.getUser(request.userName(), request.userEmail());
        if (userFound != null && userFound.getIdAccess() != 0) {
            Credentials userCredentials = credentialsGateway.getCredentialsById(userFound.getIdAccess());

            ResponseSearchUser response = new ResponseSearchUser(
                    userFound.getUserName(),
                    userFound.getUserEmail(),
                    userFound.getUserPhoneNumber(),
                    userFound.getIdRol(),
                    userCredentials.getName(),
                    userCredentials.getAccessKey()
            );
            output.displayAllUserInformation(response);

        } else if (userFound != null && userFound.getIdAccess() == 0) {
            ResponseSearchUser response = new ResponseSearchUser(
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
    public void editUser(RequestEditUser request) {
        if (!request.name().isEmpty() && !request.phoneNumber().isEmpty() && !request.email().isEmpty()) {

            UserRole role = userRoleGateway.GetRole(request.roleName());
            User user = new User(
                    userFound.getUserId(),
                    request.name(),
                    request.phoneNumber(),
                    request.email(),
                    role.getRoleId(),
                    userFound.getIdAccess()
            );

            if (
                    !request.loginName().isEmpty() &&
                            !request.password().isEmpty() &&
                            !request.confPassword().isEmpty() &&
                            userFound.getIdAccess() != 0
            ) {
                if (output.requestConfirmation(EditUserEnum.CONFIRM_UPDATE_USER)) {

                    if (userGateway.updateUser(user) && editUserCredentials(request)) {
                        output.displayConfirmation(EditUserEnum.USER_UPDATED);
                        output.resetFields();
                    } else {
                        output.displayError(EditUserEnum.USER_UPDATE_ERROR);
                    }
                }
            } else if (!request.loginName().isEmpty() &&
                    !request.password().isEmpty() &&
                    !request.confPassword().isEmpty() &&
                    userFound.getIdAccess() == 0
            ) {
                if (output.requestConfirmation(EditUserEnum.CONFIRM_UPDATE_USER)) {

                    int accessId = insertUserCredentials(request);
                    user.setIdAccess(accessId);

                    if (userGateway.updateUser(user) && accessId != 0) {
                        output.displayConfirmation(EditUserEnum.USER_UPDATED);
                        output.resetFields();
                    } else {
                        output.displayError(EditUserEnum.USER_UPDATE_ERROR);
                    }
                }
            } else {
                if (output.requestConfirmation(EditUserEnum.CONFIRM_UPDATE_USER)) {
                    if (userGateway.updateUser(user)) {
                        output.displayConfirmation(EditUserEnum.USER_UPDATED);
                        output.resetFields();
                    } else {
                        output.displayError(EditUserEnum.USER_UPDATE_ERROR);
                    }
                }
            }

        } else {
            output.displayError(EditUserEnum.INCOMPLETE_INFORMATION);
        }
    }

    @Override
    public void deleteUser(RequestSearchUser request) {
        if (request.userName().isEmpty() || request.userEmail().isEmpty()) {
            output.displayError(EditUserEnum.INCOMPLETE_INFORMATION);

        } else {
            User userFound = userGateway.getUser(request.userName(), request.userEmail());

            if (userFound == null) {
                output.displayError(EditUserEnum.USER_NO_FOUND);

            } else if (output.requestConfirmation(EditUserEnum.CONFIRM_DELETE_USER)) {
                if (userFound.getIdAccess() != 0) {
                    Credentials userFounfCredentials = credentialsGateway.getCredentialsById(userFound.getIdAccess());

                    if (userGateway.deleteUser(userFound) && credentialsGateway.deleteCredentials(userFounfCredentials)) {
                        output.displayConfirmation(EditUserEnum.USER_DELETED);
                        output.resetFields();

                    } else {
                        output.displayError(EditUserEnum.USER_DELETION_ERROR);

                    }
                } else {
                    if (userGateway.deleteUser(userFound)) {
                        output.displayConfirmation(EditUserEnum.USER_DELETED);
                        output.resetFields();

                    } else {
                        output.displayError(EditUserEnum.USER_DELETION_ERROR);

                    }
                }
            }
        }
    }

    @Override
    public int insertUserCredentials(RequestEditUser request) {
        int id = 0;
        if (!request.password().equals(request.confPassword())) {
            output.displayError(EditUserEnum.PASSWORD_DO_NOT_MATCH);
            return id;

        } else {
            Credentials newCredentials = new Credentials(request.loginName(), request.password());
            return credentialsGateway.insertCredentials(newCredentials);

        }
    }

    @Override
    public boolean editUserCredentials(RequestEditUser request) {
        if (!request.password().equals(request.confPassword())) {
            output.displayError(EditUserEnum.PASSWORD_DO_NOT_MATCH);
            return false;

        } else {
            Credentials newCredentials = credentialsGateway.getCredentialsById(userFound.getIdAccess());
            newCredentials.setName(request.loginName());
            newCredentials.setAccessKey(request.password());

            return credentialsGateway.updateCredentials(newCredentials);

        }
    }

    @Override
    public void deleteCredentials(RequestDeleteCredentials request) {
        if (!request.loginName().isEmpty() && !request.password().isEmpty()) {
            Credentials credentials = new Credentials(request.loginName(), request.password());

            int userCredentialsId = credentialsGateway.getCredentialsId(credentials);
            credentials.setAccessId(userCredentialsId);

            if (output.requestConfirmation(EditUserEnum.CONFIRM_DELETE_CREDENTIALS)) {
                if (credentialsGateway.deleteCredentials(credentials)) {
                    output.displayConfirmation(EditUserEnum.CREDENTIALS_DELETED);
                    output.resetFields();

                } else {
                    output.displayError(EditUserEnum.CREDENTIALS_DELETION_ERROR);
                }
            }
        } else {
            output.displayError(EditUserEnum.NO_ACCESS_CREDENTIALS);
        }
    }
}
