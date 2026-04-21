package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.adminpanel.AdminMainInteractor;
import com.workorderhub.core.caseuse.adminpanel.AdminMainView;
import com.workorderhub.core.caseuse.adminpanel.RequestClosedOrders;
import com.workorderhub.core.caseuse.adminpanel.RequestWorkLogs;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import com.workorderhub.infrastructure.models.WorkLogModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminMainController implements AdminMainView {

    private AdminMainInteractor interactor;

    // Menu Controller reference to allow opening the edit work order window with the selected work order.
    @FXML
    private AdminMenuController adminMenuController;

    @FXML
    private TableView<WorkFrontModel> workFrontTable;
    @FXML
    private TableView<WorkFrontModel> closedOrdersTable;
    @FXML
    private DatePicker startDateOrder;
    @FXML
    private Button loadClosedOrderButton;
    @FXML
    private TableView<WorkLogModel> workLogTable;
    @FXML
    private DatePicker startDateLog;
    @FXML
    private Button loadLogButton;
    @FXML
    private Label statusLabel;

    // Work Front (WF) table columns.
    private TableColumn<WorkFrontModel, Long> workOrderIdColumnWF;
    private TableColumn<WorkFrontModel, LocalDate> startDateColumnWF;
    private TableColumn<WorkFrontModel, LocalDate> endDateColumnWF;
    private TableColumn<WorkFrontModel, String> plantElementColumnWF;
    private TableColumn<WorkFrontModel, String> workProcedureColumnWF;
    private TableColumn<WorkFrontModel, String> lockOutDeviceIdColumnWF;
    private TableColumn<WorkFrontModel, String> lotoProcedureCodeColumnWF;
    private TableColumn<WorkFrontModel, String> holderColumnWF;
    private TableColumn<WorkFrontModel, String> statusColumnWF;

    // Closed Work (CW) table columns.
    private TableColumn<WorkFrontModel, Long> workOrderIdColumnCW;
    private TableColumn<WorkFrontModel, LocalDate> startDateColumnCW;
    private TableColumn<WorkFrontModel, LocalDate> endDateColumnCW;
    private TableColumn<WorkFrontModel, String> plantElementColumnCW;
    private TableColumn<WorkFrontModel, String> workProcedureColumnCW;
    private TableColumn<WorkFrontModel, String> lockOutDeviceIdColumnCW;
    private TableColumn<WorkFrontModel, String> lotoProcedureCodeColumnCW;
    private TableColumn<WorkFrontModel, String> holderColumnCW;
    private TableColumn<WorkFrontModel, String> statusColumnCW;

    // Work Log (WL) table columns.
    private TableColumn<WorkLogModel, Integer> workLogIdColumnWL;
    private TableColumn<WorkLogModel, LocalDateTime> workLogDateColumnWL;
    private TableColumn<WorkLogModel, String> workLogCommentColumnWL;
    private TableColumn<WorkLogModel, Long> workOrderIdColumnWL;
    private TableColumn<WorkLogModel, String> employeeNameColumnWL;
    private TableColumn<WorkLogModel, LocalDate> startDateColumnWL;
    private TableColumn<WorkLogModel, LocalDate> endDateColumnWL;
    private TableColumn<WorkLogModel, String> holderColumnWL;
    private TableColumn<WorkLogModel, Integer> workPermitIdColumnWL;

    private ObservableList<WorkFrontModel> workFrontObsList;

    private List<WorkFrontModel> closedOrdersList;
    private ObservableList<WorkFrontModel> closedOrdersObsList;

    private List<WorkLogModel> workLogElementList;
    private ObservableList<WorkLogModel> workLogElementObsList;


    public AdminMainController(AdminMainInteractor interactor) {
        this.interactor = interactor;
    }

    public void initialize(){

        if (adminMenuController != null) {
            this.adminMenuController.setAdminController(this);
        }

        statusLabel.setText(PropertiesLoader.GetText("adminView.loggedUser") + " " +
                AppState.getInstance().getLoggedUser()
        );

        workOrderIdColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.workOrderId"));
        workOrderIdColumnWF.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        startDateColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.startDate"));
        startDateColumnWF.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.endDate"));
        endDateColumnWF.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        plantElementColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.plantElement"));
        plantElementColumnWF.setCellValueFactory(new PropertyValueFactory<>("plantElementTag"));
        workProcedureColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.workProcedureCode"));
        workProcedureColumnWF.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        lockOutDeviceIdColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.lockDeviceId"));
        lockOutDeviceIdColumnWF.setCellValueFactory(new PropertyValueFactory<>("lockDeviceId"));
        lotoProcedureCodeColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.lotoProcedureCode"));
        lotoProcedureCodeColumnWF.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        holderColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.holderName"));
        holderColumnWF.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        statusColumnWF = new TableColumn<>(PropertiesLoader.GetText("adminView.status"));
        statusColumnWF.setCellValueFactory(new PropertyValueFactory<>("status"));

        workOrderIdColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.workOrderId"));
        workOrderIdColumnCW.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        startDateColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.startDate"));
        startDateColumnCW.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.endDate"));
        endDateColumnCW.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        plantElementColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.plantElement"));
        plantElementColumnCW.setCellValueFactory(new PropertyValueFactory<>("plantElementTag"));
        workProcedureColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.workProcedureCode"));
        workProcedureColumnCW.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        lockOutDeviceIdColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.lockDeviceId"));
        lockOutDeviceIdColumnCW.setCellValueFactory(new PropertyValueFactory<>("lockDeviceId"));
        lotoProcedureCodeColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.lotoProcedureCode"));
        lotoProcedureCodeColumnCW.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        holderColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.holderName"));
        holderColumnCW.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        statusColumnCW = new TableColumn<>(PropertiesLoader.GetText("adminView.status"));
        statusColumnCW.setCellValueFactory(new PropertyValueFactory<>("status"));

        workLogIdColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.workLogId"));
        workLogIdColumnWL.setCellValueFactory(new PropertyValueFactory<>("logId"));
        workLogDateColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.workLogDate"));
        workLogDateColumnWL.setCellValueFactory(new PropertyValueFactory<>("logDate"));
        workLogCommentColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.workLogComment"));
        workLogCommentColumnWL.setCellValueFactory(new PropertyValueFactory<>("logComment"));
        workOrderIdColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.workOrderId"));
        workOrderIdColumnWL.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        employeeNameColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.employeeName"));
        employeeNameColumnWL.setCellValueFactory(new PropertyValueFactory<>("userName"));
        startDateColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.startDate"));
        startDateColumnWL.setCellValueFactory(new PropertyValueFactory<>("workOrderStartDate"));
        endDateColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.endDate"));
        endDateColumnWL.setCellValueFactory(new PropertyValueFactory<>("workOrderEndDate"));
        holderColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.holderName"));
        holderColumnWL.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        workPermitIdColumnWL = new TableColumn<>(PropertiesLoader.GetText("adminView.workPermit"));
        workPermitIdColumnWL.setCellValueFactory(new PropertyValueFactory<>("workPermitId"));
        workPermitIdColumnWL.setCellFactory(_ -> new WorkPermitCell());

        closedOrdersList = new ArrayList<>();
        workLogElementList = new ArrayList<>();

        buildWorkFrontTable();
        buildClosedOrdersTable();
        buildWorkLogTable();

        retrieveWorkFront();

    }

    // Interface methods

    @Override
    public void setWorkFrontList(List<WorkFrontModel> workFrontModelList) {
        workFrontObsList = FXCollections.observableArrayList(workFrontModelList);
        workFrontTable.setItems(workFrontObsList);
    }

    @Override
    public void setClosedOrdersList(List<WorkFrontModel> closedOrdersList) {
        closedOrdersObsList = FXCollections.observableArrayList(closedOrdersList);
        closedOrdersTable.setItems(closedOrdersObsList);
    }

    @Override
    public void setWorkLogList(List<WorkLogModel> workLogList) {
        workLogElementObsList = FXCollections.observableArrayList(workLogList);
        workLogTable.setItems(workLogElementObsList);
    }

    // Event handlers

    @FXML
    private void searchClosedWorkOrder() {
        if (startDateOrder.getValue() != null) {
            RequestClosedOrders request = new RequestClosedOrders(startDateOrder.getValue());
            interactor.retrieveClosedOrders(request);

        } else {
            String errTitle = "adminView.closedOrders.noSelectedTitle";
            String errMessage = "adminView.closedOrders.noSelectedMessage";

            Util.ShowMessage(errTitle, errMessage);

        }
    }

    @FXML
    private void searchRecords() {
        if (startDateLog.getValue() != null) {
            RequestWorkLogs request = new RequestWorkLogs(startDateLog.getValue());
            interactor.retrieveWorkLogs(request);

        } else {
            String errTitle = "adminView.logDate.noSelectedTitle";
            String errMessage = "adminView.logDate.noSelectedMessage";

            Util.ShowMessage(errTitle, errMessage);

        }
    }

    // Internal methods

    /**
     * Adds the columns to the work front table.
     */
    private void buildWorkFrontTable() {
        workFrontTable.getColumns().add(workOrderIdColumnWF);
        workFrontTable.getColumns().add(startDateColumnWF);
        workFrontTable.getColumns().add(endDateColumnWF);
        workFrontTable.getColumns().add(plantElementColumnWF);
        workFrontTable.getColumns().add(workProcedureColumnWF);
        workFrontTable.getColumns().add(lockOutDeviceIdColumnWF);
        workFrontTable.getColumns().add(lotoProcedureCodeColumnWF);
        workFrontTable.getColumns().add(holderColumnWF);
        workFrontTable.getColumns().add(statusColumnWF);
    }

    /**
     * Adds the columns to the closed work table.
     */
    private void buildClosedOrdersTable() {
        closedOrdersTable.getColumns().add(workOrderIdColumnCW);
        closedOrdersTable.getColumns().add(startDateColumnCW);
        closedOrdersTable.getColumns().add(endDateColumnCW);
        closedOrdersTable.getColumns().add(plantElementColumnCW);
        closedOrdersTable.getColumns().add(workProcedureColumnCW);
        closedOrdersTable.getColumns().add(lockOutDeviceIdColumnCW);
        closedOrdersTable.getColumns().add(lotoProcedureCodeColumnCW);
        closedOrdersTable.getColumns().add(holderColumnCW);
        closedOrdersTable.getColumns().add(statusColumnCW);

    }

    /**
     * Adds the columns to the work log table.
     */
    private void buildWorkLogTable() {
        workLogTable.getColumns().add(workLogIdColumnWL);
        workLogTable.getColumns().add(workLogDateColumnWL);
        workLogTable.getColumns().add(workLogCommentColumnWL);
        workLogTable.getColumns().add(workOrderIdColumnWL);
        workLogTable.getColumns().add(employeeNameColumnWL);
        workLogTable.getColumns().add(startDateColumnWL);
        workLogTable.getColumns().add(endDateColumnWL);
        workLogTable.getColumns().add(holderColumnWL);
        workLogTable.getColumns().add(workPermitIdColumnWL);
    }

    /**
     * Gets the WorkFrontModel selected from the table. It is used by the admin menu to open the window
     * to edit the work order.
     * @return Selected Work order or Null when is empty.
     */
    public WorkFrontModel getSelectedWorkOrder(){
        if (!workFrontTable.getSelectionModel().isEmpty()){
            return workFrontTable.getSelectionModel().getSelectedItem();

        }
        else {
            return null;
        }
    }

    /**
     * Retrieves the work fronts to update the table.
     * It is called by the admin menu when the user closes the edit work order window.
     */
    public void retrieveWorkFront(){
        interactor.retrieveWorkFronts();
    }

    /**
     * Custom table cell to show whether a work permit is included or no.
     */
    private static class WorkPermitCell extends TableCell<WorkLogModel, Integer> {

        private WorkPermitCell() {
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (item == 0) {
                    setText("No");
                } else {
                    setText("Yes");
                }
            }
        }
    }
}
