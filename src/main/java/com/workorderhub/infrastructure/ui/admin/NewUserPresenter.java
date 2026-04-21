package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.newuser.NewUserOutput;
import com.workorderhub.core.caseuse.newuser.NewUserView;
import com.workorderhub.infrastructure.common.Util;

public class NewUserPresenter implements NewUserOutput {

    NewUserView view;

    public NewUserPresenter(){}

    public void setView(NewUserView view){
        this.view = view;
    }

    @Override
    public void displayConfirmation() {
        String title = "addUserView.acceptance.title";
        String message = "addUserView.acceptance.content";
        Util.ShowMessage(title, message);

        view.setDefaultView();
    }
}
