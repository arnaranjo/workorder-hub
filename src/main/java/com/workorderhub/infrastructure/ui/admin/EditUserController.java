package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.edituser.*;
import com.workorderhub.core.entity.UserRole;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class EditUserController implements EditUserView {

    private EditUserInteractor interactor;
    private List<UserRole> userRoleList;

    @FXML
    private Label editUserLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField newNameField;
    @FXML
    private TextField newPhoneField;
    @FXML
    private TextField newEmailField;
    @FXML
    private ComboBox<String> newRolBox;
    @FXML
    private TextField loginNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confPasswordField;
    @FXML
    private CheckBox cBoxCredentials;


    public EditUserController(EditUserInteractor interactor) {
        this.interactor = interactor;
    }

    public void initialize() {
        editUserLabel.setText(PropertiesLoader.GetText("editUserView.default"));
        editUserLabel.setStyle(PropertiesLoader.GetText("editUserView.defaultStyle"));

        toggleCredentialsFields();
        getUserRoleList();
    }

    @FXML
    private void searchUser() {
        RequestSearchUser request = new RequestSearchUser(
                nameField.getText(),
                emailField.getText()
        );
        interactor.searchUser(request);
    }

    @FXML
    private void toggleCredentialsFields() {
        loginNameField.setDisable(!cBoxCredentials.isSelected());
        passwordField.setDisable(!cBoxCredentials.isSelected());
        confPasswordField.setDisable(!cBoxCredentials.isSelected());
    }

    @FXML
    private void setNewSearch() {
        nameField.setText("");
        emailField.setText("");

        newNameField.setText("");
        newPhoneField.setText("");
        newEmailField.setText("");

        setDefaultView();
    }

    @FXML
    private void deleteCredential() {
        RequestDeleteCredentials request = new RequestDeleteCredentials(
                loginNameField.getText(),
                passwordField.getText()
        );
        interactor.deleteCredentials(request);
    }

    @FXML
    private void deleteUser() {
        RequestSearchUser request = new RequestSearchUser(
                nameField.getText(),
                emailField.getText()
        );
        interactor.deleteUser(request);
    }

    @FXML
    private void updateUser() {
        RequestEditUser request = new RequestEditUser(
                newNameField.getText(),
                newPhoneField.getText(),
                newEmailField.getText(),
                newRolBox.getSelectionModel().getSelectedItem(),
                loginNameField.getText(),
                passwordField.getText(),
                confPasswordField.getText()
        );
        interactor.editUser(request);
    }

    @Override
    public void setDefaultView() {
        editUserLabel.setText(PropertiesLoader.GetText("editUserView.default"));
        editUserLabel.setStyle(PropertiesLoader.GetText("editUserView.defaultStyle"));

        deactivateCredentials();
    }

    @Override
    public void getUserRoleList() {
        userRoleList = interactor.getUserRoleList();
        for (UserRole rol : userRoleList) {
            newRolBox.getItems().add(rol.getRoleName());
        }

        newRolBox.getSelectionModel().selectLast();
    }

    @Override
    public void setTopDisplay(String message, String style) {
        editUserLabel.setText(message);
        editUserLabel.setStyle(style);
    }

    @Override
    public void setNameText(String name) {
        nameField.setText(name);
    }

    @Override
    public void setEmail(String email) {
        emailField.setText(email);
    }

    @Override
    public void setNewNameText(String name) {
        newNameField.setText(name);
    }

    @Override
    public void setNewPhoneText(String phone) {
        newPhoneField.setText(phone);
    }

    @Override
    public void setNewEmailText(String email) {
        newEmailField.setText(email);
    }

    @Override
    public void setRoleName(int roleId) {
        for (UserRole rol : userRoleList) {
            if (rol.getRoleId() == roleId) {
                newRolBox.getSelectionModel().select(rol.getRoleName());
            }
        }
    }

    @Override
    public void setNewLoginName(String loginName) {
        loginNameField.setText(loginName);
    }

    @Override
    public void setNewPassword(String password) {
        passwordField.setText(password);
    }

    @Override
    public void setConfNewPassword(String password) {
        confPasswordField.setText(password);
    }

    @Override
    public void activateCredentials() {
        if (!cBoxCredentials.isSelected()) {
            cBoxCredentials.fire();
        }
    }

    @Override
    public void deactivateCredentials() {
        if (cBoxCredentials.isSelected()) {
            cBoxCredentials.fire();
            loginNameField.setText("");
            passwordField.setText("");
            confPasswordField.setText("");
        }
    }
}
