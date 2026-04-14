package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;
import com.workorderhub.provider.models.LotoProcedureModel;
import com.workorderhub.provider.models.WorkPermitModel;
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
    private boolean isLotoProcedureSelected;
    private LotoProcedureModel selectedLotoProcedure;
    private WorkPermitModel newWorkPermit;

    public void initialize() {

        // "LOTO Procedures" tab content

        toggleLoto();

        this.isLotoProcedureSelected = false;
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

    @Override
    public void setLotoProcedureList(List<LotoProcedureModel> lotoProcedureList) {
        List<LotoProcedureModel> list = FXCollections.observableList(lotoProcedureList);
        this.lotoProcedureFilList = new FilteredList<>(FXCollections.observableList(list));
        this.lotoProcedureTable.setItems(this.lotoProcedureFilList);
    }

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

                    this.isLotoProcedureSelected = true;
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

    /**
     * Sets the function to filter the LOTO procedure table according to the selected criteria, ID, document code or name.
     */
    private void setLotoProcedureSearchFunction() {
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

    /**
     * Indicates whether the work permit includes a LOTO procedure.
     *
     * @return true if the work permit includes a LOTO procedure, false otherwise.
     */
    public boolean isLoto() {
        return this.cBoxLoto.isSelected();
    }

    /**
     * Retrieves the LOTO procedure selected for the work permit.
     *
     * @return the LOTO procedure selected for the work permit, null if no LOTO procedure has been selected.
     */
    public String getPermitDescription() {
        return this.workPermitDescriptionArea.getText();
    }

    /**
     * Gets the lock devices specified for the LOTO procedure of the work permit.
     *
     * @return the lock devices specified for the LOTO procedure of the work permit,
     * null if no lock devices have been specified.
     */
    public String getLockDevices() {
        if (!isLoto()) {
            return null;
        }
        return this.lockDevicesField.getText();
    }

    /**
     * Indicates whether a LOTO procedure has been selected for the work permit.
     *
     * @return true if a LOTO procedure has been selected, false otherwise.
     */
    public boolean isLotoProcedureSelected() {
        return this.isLotoProcedureSelected;
    }

    /**
     * Gets the selected LOTO procedure for the work permit.
     *
     * @return the selected LOTO procedure for the work permit, null if no LOTO procedure has been selected.
     */
    public LotoProcedureModel getSelectedLotoProcedure() {
        return this.selectedLotoProcedure;
    }
}
