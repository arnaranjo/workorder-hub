package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;
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
public class WorkOrderPermitController implements WorkOrderPermitView {

    private WorkOrderInput interactor;

    //"Work permit" tab content
    @FXML
    protected TextArea workPermitDescriptionArea;
    @FXML
    protected CheckBox cBoxLoto;
    @FXML
    protected TextField lockDevicesField;
    @FXML
    protected ChoiceBox<String> lotoProcedureSearch;
    @FXML
    protected TextField lotoProcedureField;
    @FXML
    protected TableView<LotoProcedureModel> lotoProcedureTable;
    @FXML
    protected Button selectLotoButton;
    @FXML
    protected Label lotoProcedureCode;

    private TableColumn<LotoProcedureModel, Integer> lotoProcedureIdColumn;
    private TableColumn<LotoProcedureModel, String> lotoProcedureCodeColumn;
    private TableColumn<LotoProcedureModel, String> lotoProcedureNameColumn;
    private FilteredList<LotoProcedureModel> lotoProcedureFilList;
    private List<LotoProcedureModel> lotoProcedureList;
    private boolean isLotoProcedureSelected;
    private LotoProcedureModel selectedLotoProcedure;
    private WorkPermitModel newWorkPermit;

    public void initialize() {

        // "LOTO Procedures" tab content
        //ToggleLoto();
        isLotoProcedureSelected = false;
        lotoProcedureIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.lotoProcedure.wProcedureId"));
        lotoProcedureIdColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureId"));
        lotoProcedureCodeColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.lotoProcedure.code"));
        lotoProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        lotoProcedureNameColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.lotoProcedure.name"));
        lotoProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureName"));
        lotoProcedureTable.getColumns().add(lotoProcedureIdColumn);
        lotoProcedureTable.getColumns().add(lotoProcedureCodeColumn);
        lotoProcedureTable.getColumns().add(lotoProcedureNameColumn);

        //PopulateLotoProcedureTable();
        //SetLotoProcedureSearchFunction();
    }

    public WorkOrderPermitController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //Work permit tab method

    @FXML
    private void SelectLotoProcedure(ActionEvent actionEvent) {
    }

    @FXML
    private void ToggleLoto(ActionEvent actionEvent) {
    }
}
