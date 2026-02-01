package com.workorderhub.provider.ui.login;

import com.workorderhub.core.caseuse.login.LoginInteractor;
import com.workorderhub.core.caseuse.login.RequestLogin;
import com.workorderhub.core.caseuse.login.LoginView;
import com.workorderhub.provider.common.PropertiesLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements LoginView {

    private LoginInteractor interactor;

    @FXML
    private Label loginLabel;
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passwordTextField;

    public void initialize() {
        loginLabel.setText(PropertiesLoader.GetText("login.default"));
        loginLabel.setStyle(PropertiesLoader.GetText("login.defaultStyle"));
    }

    public LoginController(LoginInteractor interactor) {
        this.interactor = interactor;
    }

    @FXML
    private void grantAccess() {
        RequestLogin request = new RequestLogin(
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
