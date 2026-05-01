package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodView;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller associated with the creation of a new work order form.
 * CODE REFERENCES
 * 1 - <a href="https://stackoverflow.com/questions/62513192/javafx-datepicker-disable-future-dates">Disables date picker days.<a/>
 */
public class WorkOrderPeriodController implements WorkOrderPeriodView {

    private WorkOrderInput interactor;

    //"Valid period" tab content

    @FXML
    protected TableView<WorkFrontModel> workFrontTable;
    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DatePicker endDatePicker;
    @FXML
    protected Label confirmationLabel;

    private TableColumn<WorkFrontModel, Long> workOrderIdColumn;
    private TableColumn<WorkFrontModel, LocalDate> workOrderStartDateColumn;
    private TableColumn<WorkFrontModel, LocalDate> workOrderEndDateColumn;
    private TableColumn<WorkFrontModel, String> workOrderPlantElementColumn;

    private boolean isPeriodSelected;
    private LocalDate selectedStartDate;
    private LocalDate selectedEndDate;

    public void initialize() {

        // "Valid Period" tab content
        workOrderIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.workOrderId"));
        workOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        workOrderStartDateColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.startDate"));
        workOrderStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        workOrderEndDateColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.endDate"));
        workOrderEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        workOrderPlantElementColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.plantElementTag"));
        workOrderPlantElementColumn.setCellValueFactory(new PropertyValueFactory<>("plantElementTag"));
        workFrontTable.setPlaceholder(new Label(PropertiesLoader.GetText("workOrder.validPeriod.placeHolder")));
        workFrontTable.getColumns().add(workOrderIdColumn);
        workFrontTable.getColumns().add(workOrderStartDateColumn);
        workFrontTable.getColumns().add(workOrderEndDateColumn);
        workFrontTable.getColumns().add(workOrderPlantElementColumn);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        interactor.retrieveWorkFrontList();
        setDatesRestrictions();

    }

    public WorkOrderPeriodController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //Valid period tab method

    @FXML
    private void confirmDates() {
        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null) {

            String confTitle = "workOrder.validPeriod.confPeriodTitle";
            String confMessage = "workOrder.validPeriod.confPeriodMessage";

            this.isPeriodSelected = Util.RequestConfirmation(confTitle, confMessage);

            if (isPeriodSelected) {
                this.confirmationLabel.setText(PropertiesLoader.GetText("workOrder.validPeriod.confDates"));
                this.selectedStartDate = startDatePicker.getValue();
                this.selectedEndDate = endDatePicker.getValue();

            } else {
                this.selectedStartDate = this.selectedEndDate = null;
            }
        }
    }

    @Override
    public void setWorkFrontList(List<WorkFrontModel> workFrontModelList) {
        ObservableList<WorkFrontModel> workOrderInfoObsList = FXCollections.observableList(workFrontModelList);
        workFrontTable.setItems(workOrderInfoObsList);
    }

    /**
     * Controls the date inputs to avoid select passed dates and no logic end dates.
     * Ref. [1]
     */
    private void setDatesRestrictions() {
        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);

        startDatePicker.setDayCellFactory(_ -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }

        });

        endDatePicker.setDayCellFactory(_ -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(startDatePicker.getValue()));
            }

        });

        startDatePicker.valueProperty().addListener((_, _, newValue) -> {
            if (newValue.isAfter(endDatePicker.getValue())) {
                endDatePicker.setValue(startDatePicker.getValue());
            }
        });
    }

    /**
     * Checks if the user has selected and confirmed a valid period for the work order.
     *
     * @return true if the user has selected and confirmed a valid period, false otherwise.
     */
    public boolean isPeriodSelected() {
        return isPeriodSelected;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDatePicker.getValue();
    }

    @Override
    public LocalDate getEndDate() {
        return this.endDatePicker.getValue();
    }

    @Override
    public void setValidPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDatePicker.setValue(startDate);
        this.endDatePicker.setValue(endDate);
        this.confirmationLabel.setText(PropertiesLoader.GetText("workOrder.validPeriod.confDates"));
        this.isPeriodSelected = true;
    }
}
