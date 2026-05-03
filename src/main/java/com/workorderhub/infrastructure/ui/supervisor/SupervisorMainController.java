package com.workorderhub.infrastructure.ui.supervisor;

import com.workorderhub.core.caseuse.supervisor.SupervisorMainInput;
import com.workorderhub.core.caseuse.supervisor.SupervisorMainView;
import com.workorderhub.core.entity.WorkOrderElement;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SupervisorMainController implements SupervisorMainView {

    @FXML
    private Label mainLabel;
    @FXML
    private TableView<WorkFrontModel> workFrontTable;
    @FXML
    private TextArea workFrontDescArea;
    @FXML
    private ChoiceBox<String> searchCriteriaSelector;
    @FXML
    private TextField workOrderSearchField;
    @FXML
    private TableView<WorkFrontModel> closedWorkTable;
    @FXML
    private TextArea closedWorkDescArea;
    @FXML
    private DatePicker startDatePicker;

    private SupervisorMainInput interactor;

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

    private List<WorkFrontModel> workFrontList;
    private ObservableList<WorkFrontModel> workFrontObsList;
    private FilteredList<WorkFrontModel> workFrontFilList;

    private List<WorkFrontModel> closedWorkList;
    private ObservableList<WorkOrderElement> closedWorkObsList;

    public SupervisorMainController(SupervisorMainInput interactor) {
        this.interactor = interactor;
    }

    public void initialize() {
        mainLabel.setText(PropertiesLoader.GetText("SupervisorView.default"));
        mainLabel.setStyle(PropertiesLoader.GetText("SupervisorView.defaultStyle"));

        // Initialize WF table columns.
        workOrderIdColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.workOrderId"));
        workOrderIdColumnWF.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        startDateColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.startDate"));
        startDateColumnWF.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.endDate"));
        endDateColumnWF.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        plantElementColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.plantElement"));
        plantElementColumnWF.setCellValueFactory(new PropertyValueFactory<>("plantElementTag"));
        workProcedureColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.workProcedureCode"));
        workProcedureColumnWF.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        lockOutDeviceIdColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.lockOutDeviceId"));
        lockOutDeviceIdColumnWF.setCellValueFactory(new PropertyValueFactory<>("lockoutDeviceId"));
        lotoProcedureCodeColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.lotoProcedureCode"));
        lotoProcedureCodeColumnWF.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        holderColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.holderName"));
        holderColumnWF.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        statusColumnWF = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.status"));
        statusColumnWF.setCellValueFactory(new PropertyValueFactory<>("status"));

        workOrderIdColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.workOrderId"));
        workOrderIdColumnCW.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        startDateColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.startDate"));
        startDateColumnCW.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.endDate"));
        endDateColumnCW.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        plantElementColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.plantElement"));
        plantElementColumnCW.setCellValueFactory(new PropertyValueFactory<>("plantElementTag"));
        workProcedureColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.workProcedureCode"));
        workProcedureColumnCW.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        lockOutDeviceIdColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.lockOutDeviceId"));
        lockOutDeviceIdColumnCW.setCellValueFactory(new PropertyValueFactory<>("lockoutDeviceId"));
        lotoProcedureCodeColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.lotoProcedureCode"));
        lotoProcedureCodeColumnCW.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        holderColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.holderName"));
        holderColumnCW.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        statusColumnCW = new TableColumn<>(PropertiesLoader.GetText("SupervisorView.status"));
        statusColumnCW.setCellValueFactory(new PropertyValueFactory<>("status"));

        workFrontList = new ArrayList<>();
        closedWorkList = new ArrayList<>();

        setSearchFunction();
    }

    /**
     * Sets the function to filter the list and display the work order  searched for.
     */
    private void setSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("SupervisorView.searchList");
        searchCriteriaSelector.getItems().addAll(list);
        searchCriteriaSelector.setValue(list[0]);

        workOrderSearchField.setPromptText(PropertiesLoader.GetText("SupervisorView.searchTag"));
        workOrderSearchField.textProperty().addListener((_, _, newValue) -> {
            switch (searchCriteriaSelector.getValue()) {
                case "ID":
                    workFrontFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }
                        return String.valueOf(p.getWorkOrderId()).contains(newValue);
                    });
                    break;

                case "Tag":
                    workFrontFilList.setPredicate(p ->
                            p.getPlantElementTag().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;

                case "Holder":
                    workFrontFilList.setPredicate(p ->
                            p.getHolderName().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;

                case "Start Date":
                    workFrontFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        String dateString = p.getStartDate() != null ?
                                p.getStartDate().format(formatter) : "";

                        return dateString.contains(newValue.trim());

                    });
                    break;

                case "End Date":
                    workFrontFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        String dateString = p.getEndDate() != null ?
                                p.getEndDate().format(formatter) : "";

                        return dateString.contains(newValue.trim());
                    });
                    break;
            }

        });
    }
}
