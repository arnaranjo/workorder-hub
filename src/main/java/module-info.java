module com.workorderhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;

    exports com.workorderhub.main;
    opens com.workorderhub.main to javafx.fxml;
    exports com.workorderhub.infrastructure.ui.login;
    opens com.workorderhub.infrastructure.ui.login to javafx.fxml;
    exports com.workorderhub.infrastructure.ui.admin;
    opens com.workorderhub.infrastructure.ui.admin to javafx.fxml;
    exports com.workorderhub.infrastructure.ui.technician;
    opens com.workorderhub.infrastructure.ui.technician to javafx.fxml;
    exports com.workorderhub.infrastructure.ui.supervisor;
    opens com.workorderhub.infrastructure.ui.supervisor to javafx.fxml;
    exports com.workorderhub.infrastructure.models;
    opens com.workorderhub.infrastructure.models to javafx.fxml;

    exports com.workorderhub.core.caseuse.edituser;
    exports com.workorderhub.core.caseuse.newuser;
    exports com.workorderhub.core.caseuse.procedures;
    exports com.workorderhub.core.caseuse.plantelement;
    exports com.workorderhub.core.caseuse.spareparts;
    exports com.workorderhub.core.caseuse.workorder;
    exports com.workorderhub.core.caseuse.login;
    exports com.workorderhub.core.gateway;
    exports com.workorderhub.core.entity;
    exports com.workorderhub.core.caseuse.adminpanel;
    exports com.workorderhub.core.caseuse.technician;
    exports com.workorderhub.core.caseuse.supervisor;
}