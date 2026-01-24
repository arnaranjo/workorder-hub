package com.workorderhub.provider.ui.admin;

import com.workorderhub.provider.common.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class AdminMainController {

    @FXML
    private DatePicker startDateOrder;
    @FXML
    private Button loadOrdersButton;
    @FXML
    private TableView closedOrdersTable;
    @FXML
    private DatePicker startDateLog;
    @FXML
    private Button loadLogButton;
    @FXML
    private TableView workLogTable;
    @FXML
    private Label statusLabel;
    @FXML
    private TableView workFrontTable;

    public void initialize(){
        statusLabel.setText(AppState.getInstance().getLoggedUser());
    }
}
