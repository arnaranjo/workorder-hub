package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.edituser.EditUserInteractor;
import com.workorderhub.core.caseuse.edituser.SearchUserRequest;
import com.workorderhub.core.entity.UserRole;
import com.workorderhub.provider.common.PropertiesLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class EditUserController implements EditUserView {

    private EditUserInteractor interactor;
    List<UserRole> userRoleList;

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
    private TextField newLoginNameField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confPasswordField;
    @FXML
    private Button searchButton;
    @FXML
    private CheckBox cBoxCredentials;
    @FXML
    private Button newSearchButton;
    @FXML
    private Button deleteCredentialButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button updateButton;

    public EditUserController(EditUserInteractor interactor){
        this.interactor = interactor;
    }

    public void initialize(){
        editUserLabel.setText(PropertiesLoader.GetText("editUserView.default"));
        editUserLabel.setStyle(PropertiesLoader.GetText("editUserView.defaultStyle"));

        toggleCredentialsFields();
        getUserRoleList();
    }

    @FXML
    private void searchUser() {
        SearchUserRequest request = new SearchUserRequest(
                nameField.getText(),
                emailField.getText()
        );
        interactor.searchUser(request);
    }

    @FXML
    private void toggleCredentialsFields() {
        newLoginNameField.setDisable(!cBoxCredentials.isSelected());
        newPasswordField.setDisable(!cBoxCredentials.isSelected());
        confPasswordField.setDisable(!cBoxCredentials.isSelected());
    }

    @FXML
    private void setNewSearch() {
        nameField.setText("");
        emailField.setText("");
        setDefaultView();
    }

    @FXML
    private void deleteCredential() {
    }

    @FXML
    private void deleteUser() {
    }

    @FXML
    private void updateUser() {
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
        newLoginNameField.setText(loginName);
    }

    @Override
    public void setNewPassword(String password) {
        newPasswordField.setText(password);
    }


    @Override
    public void activateCredentials() {
        if (!cBoxCredentials.isSelected()){
            cBoxCredentials.fire();
        }
    }

    @Override
    public void deactivateCredentials() {
        if (cBoxCredentials.isSelected()){
            cBoxCredentials.fire();
            newLoginNameField.setText("");
            newPasswordField.setText("");
            confPasswordField.setText("");
        }
    }
}
