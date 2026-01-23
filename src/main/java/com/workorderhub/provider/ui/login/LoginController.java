package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginInteractor;
import com.workorderhub.core.caseuse.login.LoginRequest;
import com.workorderhub.provider.common.Util;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements LoginView{

    private LoginInteractor interactor;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;

    public void initialize() {
        loginLabel.setText(Util.GetText("login.default"));
        loginLabel.setStyle(Util.GetText("login.defaultStyle"));
    }

    public void setLoginInteractor(LoginInteractor interactor){
        this.interactor = interactor;
    }


    @FXML
    private void grantAccess() {
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

    /**
     * Closes the login screen.
     */
    @Override
    public void closedScreen() {
        ((Stage) userTextField.getScene().getWindow()).close();
    }
}
