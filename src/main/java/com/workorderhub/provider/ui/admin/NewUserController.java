package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.newuser.NewUserInteractor;
import com.workorderhub.core.caseuse.newuser.NewUserRequest;
import com.workorderhub.core.entity.UserRole;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;

public class NewUserController implements NewUserView{

    private NewUserInteractor interactor;
    private HashMap<String, Integer> roleIdMap = new HashMap<>();
    private NewUserRequest request;

    @FXML
    private Label newUserLabel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> rolBox;
    @FXML
    private TextField loginNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confPasswordField;

    public NewUserController(NewUserInteractor interactor){
        this.interactor = interactor;
    }

    public void initialize(){
        newUserLabel.setText(PropertiesLoader.GetText("addUserView.default"));
        newUserLabel.setStyle(PropertiesLoader.GetText("addUserView.defaultStyle"));
        getUserRoleList();
    }

    @FXML
    private void createUser() {
        String title = "addUserView.confirmation.title";
        String message = "addUserView.confirmation.content";

        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || emailField.getText().isEmpty()) {
            newUserLabel.setText(PropertiesLoader.GetText("addUserView.wrongUserInfo"));
            newUserLabel.setStyle(PropertiesLoader.GetText("addUserView.failStyle"));
        }
        else if (!loginNameField.getText().isEmpty() &&
                !passwordField.getText().isEmpty() &&
                !confPasswordField.getText().isEmpty())
        {
            if (passwordField.getText().equals(confPasswordField.getText())) {
                if (Util.RequestConfirmation(title, message)) {
                    request = new NewUserRequest(
                            nameField.getText(),
                            phoneField.getText(),
                            emailField.getText(),
                            roleIdMap.get(rolBox.getSelectionModel().getSelectedItem()),
                            loginNameField.getText(),
                            passwordField.getText()
                    );
                    interactor.createNewUser(request);
                }

            } else {
                newUserLabel.setText(PropertiesLoader.GetText("addUserView.wrongPassword"));
                newUserLabel.setStyle(PropertiesLoader.GetText("addUserView.failStyle"));
            }

        } else {
            if (Util.RequestConfirmation(title, message)) {
                request = new NewUserRequest(
                        nameField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        roleIdMap.get(rolBox.getSelectionModel().getSelectedItem()),
                        null,
                        null
                );
                interactor.createNewUser(request);
            }
        }
    }

    @FXML
    private void clearFields() {
        setDefaultView();
    }

    @Override
    public void setDefaultView() {
        newUserLabel.setText(PropertiesLoader.GetText("addUserView.default"));
        newUserLabel.setStyle(PropertiesLoader.GetText("addUserView.defaultStyle"));

        nameField.setText(null);
        phoneField.setText(null);
        emailField.setText(null);
        loginNameField.setText(null);
        passwordField.setText(null);
        confPasswordField.setText(null);
    }

    /**
     * Gets the user roles and fills the ComboBox.
     */
    private void getUserRoleList() {
        List<UserRole> userRoleList = interactor.getUserRoleList();
        for (UserRole rol : userRoleList) {
            rolBox.getItems().add(rol.getRoleName());
            roleIdMap.put(rol.getRoleName(), rol.getRoleId());
        }

        rolBox.getSelectionModel().selectLast();
    }
}
