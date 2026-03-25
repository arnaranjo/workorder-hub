package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderProcedureView;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.models.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Controller associated with the creation of a new work order form.
 * CODE REFERENCES
 * 1 - <a href="https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#sum">Allows only digits.<a/>
 * 2 - <a href="https://stackoverflow.com/questions/62513192/javafx-datepicker-disable-future-dates">Disables date picker days.<a/>
 * 3 - <a href="https://stackoverflow.com/questions/77576921/java-fx-tableview-access-cell-contents-for-searching">Searches field for a TableView.<a/>
 */
public class WorkOrderProcedureController implements WorkOrderProcedureView {

    private WorkOrderInput interactor;

    //"Work procedure" tab content
    @FXML
    private Tab workProcedureTab;
    @FXML
    protected ChoiceBox<String> workProcedureSearch;
    @FXML
    protected TextField workProcedureField;
    @FXML
    protected TableView<WorkProcedureModel> workProcedureTable;
    @FXML
    protected Label workProcedureCode;

    private TableColumn<WorkProcedureModel, Integer> workProcedureIdColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureCodeColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureNameColumn;
    private FilteredList<WorkProcedureModel> workProcedureFilList;
    private List<WorkProcedureModel> workProcedureList;
    private int workProcedureId;
    private boolean isWorkProcedureSelected;
    private WorkProcedureModel selectedWorkProcedure;

    public void initialize() {

        // "Work Procedures" tab content
        workProcedureIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.wProcedure.wProcedureId"));
        workProcedureIdColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureId"));
        workProcedureCodeColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.wProcedure.code"));
        workProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        workProcedureNameColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.wProcedure.name"));
        workProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureName"));
        workProcedureTable.getColumns().add(workProcedureIdColumn);
        workProcedureTable.getColumns().add(workProcedureCodeColumn);
        workProcedureTable.getColumns().add(workProcedureNameColumn);

        //isWProcedureSelected = false;
        //PopulateWorkProcedureTable();
        //SetWorkProcedureSearchFunction();

    }

    public WorkOrderProcedureController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //Work procedure tab method

    @FXML
    private void SelectWorkProcedure(ActionEvent actionEvent) {
    }
}
