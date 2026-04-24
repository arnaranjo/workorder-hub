package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.LotoProcedureModel;
import com.workorderhub.infrastructure.models.WorkPermitModel;
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
    private LotoProcedureModel selectedLotoProcedure;
    private WorkPermitModel newWorkPermit;

    public void initialize() {

        // "LOTO Procedures" tab content

        toggleLoto();

        this.selectedLotoProcedure = null;

        this.lotoProcedureIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.lotoProcedure.wProcedureId"));
        this.lotoProcedureIdColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureId"));
        this.lotoProcedureCodeColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.lotoProcedure.code"));
        this.lotoProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        this.lotoProcedureNameColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.lotoProcedure.name"));
        this.lotoProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureName"));
        this.lotoProcedureTable.getColumns().add(lotoProcedureIdColumn);
        this.lotoProcedureTable.getColumns().add(lotoProcedureCodeColumn);
        this.lotoProcedureTable.getColumns().add(lotoProcedureNameColumn);

        interactor.retrieveLotoProcedureList();
        setLotoProcedureSearchFunction();
    }

    public WorkOrderPermitController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //Work permit tab method

    @FXML
    private void selectLotoProcedure() {
        if (!lotoProcedureTable.getSelectionModel().isEmpty()) {
            if (selectedLotoProcedure == null ||
                    this.selectedLotoProcedure.getLotoProcedureId() !=
                            this.lotoProcedureTable.getSelectionModel().getSelectedItem().getLotoProcedureId()
            ) {
                String ConfTitle = "workOrder.lotoProcedure.confLotoProcedureTitle";
                String ConfMessage = "workOrder.lotoProcedure.confLotoProcedureMessage";

                if (Util.RequestConfirmation(ConfTitle, ConfMessage)) {

                    this.selectedLotoProcedure = lotoProcedureTable.getSelectionModel().getSelectedItem();

                    this.lotoProcedureCode.setText(
                            PropertiesLoader.GetText("workOrder.lotoProcedure.codeDefault") + " " +
                                    selectedLotoProcedure.getLotoProcedureCode()
                    );
                }

            } else {
                String title = "workOrder.lotoProcedure.lotoProcedureErrorTitle";
                String messageError = "workOrder.lotoProcedure.lotoProcedureErrorMessage";

                Util.ShowMessage(title, messageError);

            }
        } else {
            String title = "workOrder.lotoProcedure.lotoProcedureErrorTitle";
            String messageError = "workOrder.lotoProcedure.noSelectedErrorMessage";

            Util.ShowMessage(title, messageError);

        }
    }

    @FXML
    private void toggleLoto() {
        this.lockDevicesField.setDisable(!cBoxLoto.isSelected());
        this.selectLotoButton.setDisable(!cBoxLoto.isSelected());
        this.lotoProcedureSearch.setDisable(!cBoxLoto.isSelected());
        this.lotoProcedureField.setDisable(!cBoxLoto.isSelected());
    }

    // Interface methods

    @Override
    public void setLotoProcedureList(List<LotoProcedureModel> lotoProcedureList) {
        List<LotoProcedureModel> list = FXCollections.observableList(lotoProcedureList);
        this.lotoProcedureFilList = new FilteredList<>(FXCollections.observableList(list));
        this.lotoProcedureTable.setItems(this.lotoProcedureFilList);
    }

    @Override
    public String getPermitDescription() {
        return this.workPermitDescriptionArea.getText();
    }

    @Override
    public String getLockDevices() {
        if (!cBoxLoto.isSelected()) {
            return null;
        }
        return this.lockDevicesField.getText();
    }

    @Override
    public Integer getSelectedLotoProcedureId() {
        if (!cBoxLoto.isSelected() || selectedLotoProcedure == null) {
            return null;
        }
        return this.selectedLotoProcedure.getLotoProcedureId();
    }

    @Override
    public void setWorkPermitInfo(String description, String lockDevices, LotoProcedureModel model) {
        this.workPermitDescriptionArea.setText(description);


        if (lockDevices != null) {
            cBoxLoto.setSelected(true);
            toggleLoto();
            this.lockDevicesField.setText(lockDevices);
        }
        if (model != null) {
            this.selectedLotoProcedure = model;
            this.lotoProcedureCode.setText(
                    PropertiesLoader.GetText("workOrder.lotoProcedure.codeDefault") + " " +
                            selectedLotoProcedure.getLotoProcedureCode()
            );
        }
    }

    // Auxiliary methods

    /**
     * Sets the function to filter the LOTO procedure table according to the selected criteria, ID, document code or name.
     */
    public void setLotoProcedureSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("workOrder.lotoProcedure.searchList");
        this.lotoProcedureSearch.getItems().addAll(list);
        this.lotoProcedureSearch.setValue(list[0]);

        this.lotoProcedureField.setPromptText(PropertiesLoader.GetText("workOrder.lotoProcedure.searchTag"));
        this.lotoProcedureField.textProperty().addListener((_, _, newValue) -> {
            switch (lotoProcedureSearch.getValue()) {
                case "ID":
                    this.lotoProcedureFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }
                        return String.valueOf(p.getLotoProcedureId()).contains(newValue);
                    });
                    break;

                case "Code":
                    this.lotoProcedureFilList.setPredicate(p ->
                            p.getLotoProcedureCode().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;

                case "Name":
                    this.lotoProcedureFilList.setPredicate(p ->
                            p.getLotoProcedureName().toLowerCase().contains(newValue.toLowerCase().trim()));
            }
        });
    }
}
