package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderProcedureView;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;
import com.workorderhub.provider.models.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
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
    protected Label workProcedureLabel;

    private TableColumn<WorkProcedureModel, Integer> workProcedureIdColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureCodeColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureNameColumn;
    private FilteredList<WorkProcedureModel> workProcedureFilList;
    private int workProcedureId;
    private boolean isWorkProcedureSelected;
    private WorkProcedureModel selectedWorkProcedure;

    public void initialize() {

        // "Work Procedures" tab content
        workProcedureIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.workProcedure.workProcedureId"));
        workProcedureIdColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureId"));
        workProcedureCodeColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.workProcedure.code"));
        workProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        workProcedureNameColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.workProcedure.name"));
        workProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureName"));
        workProcedureTable.getColumns().add(workProcedureIdColumn);
        workProcedureTable.getColumns().add(workProcedureCodeColumn);
        workProcedureTable.getColumns().add(workProcedureNameColumn);

        interactor.retrieveWorkProcedureList();
        this.isWorkProcedureSelected = false;
        setWorkProcedureSearchFunction();

    }

    public WorkOrderProcedureController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //Work procedure tab method

    @FXML
    private void selectWorkProcedure() {
        if (!workProcedureTable.getSelectionModel().isEmpty()) {
            if (selectedWorkProcedure == null ||
                    this.selectedWorkProcedure.getWorkProcedureId() !=
                            this.workProcedureTable.getSelectionModel().getSelectedItem().getWorkProcedureId()
            ) {

                String ConfTitle = "workOrder.workProcedure.confWorkProcedureTitle";
                String ConfMessage = "workOrder.workProcedure.confWorkProcedureMessage";

                if (Util.RequestConfirmation(ConfTitle, ConfMessage)) {
                    selectedWorkProcedure = workProcedureTable.getSelectionModel().getSelectedItem();

                    this.isWorkProcedureSelected = true;

                    this.workProcedureId = selectedWorkProcedure.getWorkProcedureId();

                    workProcedureLabel.setText(
                            PropertiesLoader.GetText("workOrder.workProcedure.codeDefault") + " " +
                                    selectedWorkProcedure.getWorkProcedureCode()
                    );
                }

            } else {
                String title = "workOrder.workProcedure.workProcedureErrorTitle";
                String errorMessage = "workOrder.workProcedure.workProcedureErrorMessage";

                Util.ShowMessage(title, errorMessage);

            }
        } else {
            String title = "workOrder.workProcedure.workProcedureErrorTitle";
            String errorMessage = "workOrder.workProcedure.noSelectedErrorMessage";

            Util.ShowMessage(title, errorMessage);

        }
    }

    @Override
    public void setWorkProcedureList(List<WorkProcedureModel> workProcedureList) {
        List<WorkProcedureModel> list = FXCollections.observableList(workProcedureList);
        workProcedureFilList = new FilteredList<>(FXCollections.observableList(list));
        workProcedureTable.setItems(workProcedureFilList);
    }

    /**
     * Sets the function to filter the work procedure table according to the selected criteria, ID, document code or name.
     * Ref. [3]
     */
    private void setWorkProcedureSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("workOrder.workProcedure.searchList");
        workProcedureSearch.getItems().addAll(list);
        workProcedureSearch.setValue(list[0]);

        workProcedureField.setPromptText(PropertiesLoader.GetText("workOrder.workProcedure.searchTag"));
        workProcedureField.textProperty().addListener((_, _, newValue) -> {
            switch (workProcedureSearch.getValue()) {
                case "ID":
                    workProcedureFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }
                        return String.valueOf(p.getWorkProcedureId()).contains(newValue);
                    });
                    break;

                case "Code":
                    workProcedureFilList.setPredicate(p ->
                            p.getWorkProcedureCode().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;

                case "Name":
                    workProcedureFilList.setPredicate(p ->
                            p.getWorkProcedureName().toLowerCase().contains(newValue.toLowerCase().trim()));
            }
        });
    }

    /**
     * Check if a work procedure is selected.
     * @return true if a work procedure is selected, false otherwise.
     */
    public boolean isWorkProcedureSelected() {
        return this.isWorkProcedureSelected;
    }

    public int getWorkProcedureId() {
        return this.workProcedureId;
    }
}