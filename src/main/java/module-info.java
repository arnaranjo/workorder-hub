module com.workorderhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;


    exports com.workorderhub.main;
    opens com.workorderhub.main to javafx.fxml;
    exports com.workorderhub.provider.ui.login;
    opens com.workorderhub.provider.ui.login to javafx.fxml;
    exports com.workorderhub.provider.ui.admin;
    opens com.workorderhub.provider.ui.admin to javafx.fxml;
}