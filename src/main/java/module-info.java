module com.workorderhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;

    exports com.workorderhub.main;
    opens com.workorderhub.main to javafx.fxml;
    exports com.workorderhub.provider.ui.login;
    opens com.workorderhub.provider.ui.login to javafx.fxml;
    exports com.workorderhub.provider.ui.admin;
    opens com.workorderhub.provider.ui.admin to javafx.fxml;
    exports com.workorderhub.provider.tablemodels;
    opens com.workorderhub.provider.tablemodels to javafx.fxml;

    exports com.workorderhub.core.caseuse.edituser;
    exports com.workorderhub.core.caseuse.newuser;
    exports com.workorderhub.core.caseuse.procedures;
    exports com.workorderhub.core.caseuse.login;
    exports com.workorderhub.core.gateway;
    exports com.workorderhub.core.entity;
}