package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginInteractor;
import com.workorderhub.core.caseuse.login.LoginRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements LoginView{


    private LoginInteractor interactor;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;


    public TextField getUserTextField() {
        return userTextField;
    }

    public Label getLoginLabel() {
        return loginLabel;
    }

    public PasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public void setLoginInteractor(LoginInteractor interactor){
        this.interactor = interactor;
    }


    @FXML
    protected void GrantAccess() {
        LoginRequest request = new LoginRequest(
                userTextField.getText(),
                passwordTextField.getText()
        );

        interactor.grantAccess(request);
    }

    @Override
    public void setTopDisplay(String message, String style) {
        loginLabel.setText(message);
        loginLabel.setStyle(style);
    }
}
