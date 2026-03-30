package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodView;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.models.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
    protected TableView<WorkOrderModel> workFrontTable;
    @FXML
    protected DatePicker startDatePicker;
    @FXML
    protected DatePicker endDatePicker;
    @FXML
    protected Label confirmationLabel;

    private TableColumn<WorkOrderModel, Long> workOrderIdColumn;
    private TableColumn<WorkOrderModel, LocalDate> workOrderStartDateColumn;
    private TableColumn<WorkOrderModel, LocalDate> workOrderEndDateColumn;
    private TableColumn<WorkOrderModel, String> workOrderDescriptionColumn;
    private ObservableList<WorkOrderModel> workOrderInfoObsList;
    private boolean isPeriodSelected;

    public void initialize() {

        // "Valid Period" tab content
        workOrderIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.workOrderId"));
        workOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        workOrderStartDateColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.startDate"));
        workOrderStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        workOrderEndDateColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.endDate"));
        workOrderEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        workOrderDescriptionColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.description"));
        workOrderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        workFrontTable.setPlaceholder(new Label(PropertiesLoader.GetText("workOrder.validPeriod.placeHolder")));
        workFrontTable.getColumns().add(workOrderIdColumn);
        workFrontTable.getColumns().add(workOrderStartDateColumn);
        workFrontTable.getColumns().add(workOrderEndDateColumn);
        workFrontTable.getColumns().add(workOrderDescriptionColumn);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        //PopulateWorkOrderTable();
        //SetDatesRestrictions();

    }

    public WorkOrderPeriodController (WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //Valid period tab method

    @FXML
    private void ConfirmDates(ActionEvent actionEvent) {
    }

}
