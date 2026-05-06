package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.procedures.*;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.LotoProcedureModel;
import com.workorderhub.infrastructure.models.WorkProcedureModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.List;

public class ProceduresController implements ProcedureView {

    private LotoProcedureInput lotoProcedureInteractor;
    private WorkProcedureInput workProcedureInteractor;

    private ObservableList<WorkProcedureModel> workProceduresObsList;
    private ObservableList<LotoProcedureModel> lotoProceduresObsList;

    @FXML
    private Label mainLabel;
    @FXML
    private TextField workPrCodeField;
    @FXML
    private TextField workPrNameField;
    @FXML
    private TableView<WorkProcedureModel> workProcedureTable;
    @FXML
    private TableView<LotoProcedureModel> lotoProcedureTable;
    @FXML
    private TextField lotoPrCodeField;
    @FXML
    private TextField lotoPrNameField;
    @FXML
    private Label infoLabel;

    private TableColumn<WorkProcedureModel, String> workProcedureCodeColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureNameColumn;
    private TableColumn<LotoProcedureModel, String> lotoProcedureCodeColumn;
    private TableColumn<LotoProcedureModel, String> lotoProcedureNameColumn;

    public ProceduresController(
            WorkProcedureInput workProcedureInteractor,
            LotoProcedureInput lotoProcedureInteractor
    ) {
        this.workProcedureInteractor = workProcedureInteractor;
        this.lotoProcedureInteractor = lotoProcedureInteractor;
    }

    public void initialize() {
        mainLabel.setText(PropertiesLoader.GetText("procedureLists.default"));
        mainLabel.setStyle(PropertiesLoader.GetText("procedureLists.defaultStyle"));

        workProcedureCodeColumn = new TableColumn<>();
        workProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        workProcedureNameColumn = new TableColumn<>();
        workProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureName"));

        workProcedureTable.getColumns().add(workProcedureCodeColumn);
        workProcedureTable.getColumns().add(workProcedureNameColumn);

        lotoProcedureCodeColumn = new TableColumn<>();
        lotoProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureCode"));
        lotoProcedureNameColumn = new TableColumn<>();
        lotoProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("lotoProcedureName"));

        lotoProcedureTable.getColumns().add(lotoProcedureCodeColumn);
        lotoProcedureTable.getColumns().add(lotoProcedureNameColumn);

        setWorkProcedureTableEdition();
        setLotoProcedureTableEdition();

        workProcedureInteractor.retrieveWorkProcedureList();
        lotoProcedureInteractor.retrieveLotoProcedureList();
    }

    @FXML
    private void AddWorkProcedure() {
        RequestNewWorkProcedure newWorkProcedure = new RequestNewWorkProcedure(
                workPrCodeField.getText(),
                workPrNameField.getText()

        );
        int documentId = workProcedureInteractor.CreateWorkProcedure(newWorkProcedure);
        if (documentId != 0) {
            WorkProcedureModel newRow = new WorkProcedureModel(
                    documentId,
                    workPrCodeField.getText(),
                    workPrNameField.getText()
            );
            workProceduresObsList.add(newRow);
            workProcedureTable.refresh();

            workPrCodeField.clear();
            workPrNameField.clear();
        }
    }

    @FXML
    private void DeleteWorkProcedureRow() {
        if (workProcedureTable.getSelectionModel().getSelectedItem() != null) {
            RequestDeleteWorkProcedure deleteWorkProcedure = new RequestDeleteWorkProcedure(
                    workProcedureTable.getSelectionModel().getSelectedItem().getWorkProcedureId(),
                    workProcedureTable.getSelectionModel().getSelectedItem().getWorkProcedureCode(),
                    workProcedureTable.getSelectionModel().getSelectedItem().getWorkProcedureName()
            );

            int row = workProcedureTable.getSelectionModel().getSelectedIndex();

            if (workProcedureInteractor.DeleteWorkProcedure(deleteWorkProcedure)) {
                workProceduresObsList.remove(row);

            }
        } else {
            String title = "workProcedure.noSelectionTitle";
            String message = "workProcedure.noSelectionMessage";

            Util.ShowMessage(title, message);

        }
    }

    @FXML
    private void AddLotoProcedure() {
        RequestNewLotoProcedure newLotoProcedure = new RequestNewLotoProcedure(
                lotoPrCodeField.getText(),
                lotoPrNameField.getText()

        );
        int documentId = lotoProcedureInteractor.CreateLotoProcedure(newLotoProcedure);
        if (documentId != 0) {
            LotoProcedureModel newRow = new LotoProcedureModel(
                    documentId,
                    lotoPrCodeField.getText(),
                    lotoPrNameField.getText()
            );
            lotoProceduresObsList.add(newRow);
            lotoProcedureTable.refresh();

            lotoPrCodeField.clear();
            lotoPrNameField.clear();
        }
    }

    @FXML
    private void DeleteLotoProcedureRow() {
        if (lotoProcedureTable.getSelectionModel().getSelectedItem() != null) {
            RequestDeleteLotoProcedure deleteLotoProcedure = new RequestDeleteLotoProcedure(
                    lotoProcedureTable.getSelectionModel().getSelectedItem().getLotoProcedureId(),
                    lotoProcedureTable.getSelectionModel().getSelectedItem().getLotoProcedureCode(),
                    lotoProcedureTable.getSelectionModel().getSelectedItem().getLotoProcedureName()
            );

            int row = lotoProcedureTable.getSelectionModel().getSelectedIndex();

            if (lotoProcedureInteractor.DeleteLotoProcedure(deleteLotoProcedure)) {
                lotoProceduresObsList.remove(row);

            }
        } else {
            String title = "lotoProcedure.noSelectionTitle";
            String message = "lotoProcedure.noSelectionMessage";

            Util.ShowMessage(title, message);

        }
    }

    @Override
    public void setInfoDisplay(String message) {
        infoLabel.setText(message);
    }

    @Override
    public void setWorkProcedureTableEdition() {
        workProcedureTable.setEditable(true);

        workProcedureCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        workProcedureCodeColumn.setOnEditCommit(cell -> {

                    WorkProcedureModel workProcedure = cell.getRowValue();
                    String oldValue = cell.getOldValue();
                    String newValue = cell.getNewValue();

                    RequestUpdateWorkProcedureCode request = new RequestUpdateWorkProcedureCode(
                            workProcedure.getWorkProcedureId(),
                            newValue
                    );
                    if (workProcedureInteractor.UpdateWorkProcedureCode(request)) {
                        workProcedure.setWorkProcedureCode(newValue);
                        workProcedureTable.refresh();

                    } else {
                        workProcedure.setWorkProcedureCode(oldValue);
                        workProcedureTable.edit(-1, null);
                        workProcedureTable.refresh();

                    }
                }
        );

        workProcedureNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        workProcedureNameColumn.setOnEditCommit(cell -> {
                    WorkProcedureModel workProcedure = cell.getRowValue();
                    String oldValue = cell.getOldValue();
                    String newValue = cell.getNewValue();

                    RequestUpdateWorkProcedureName request = new RequestUpdateWorkProcedureName(
                            workProcedure.getWorkProcedureId(),
                            newValue
                    );
                    if (workProcedureInteractor.UpdateWorkProcedureName(request)) {
                        workProcedure.setWorkProcedureName(newValue);
                        workProcedureTable.refresh();

                    } else {
                        workProcedure.setWorkProcedureName(oldValue);
                        workProcedureTable.edit(-1, null);
                        workProcedureTable.refresh();

                    }
                }
        );
    }

    @Override
    public void setLotoProcedureTableEdition() {
        lotoProcedureTable.setEditable(true);

        lotoProcedureCodeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lotoProcedureCodeColumn.setOnEditCommit(cell -> {

                    LotoProcedureModel lotoProcedure = cell.getRowValue();
                    String oldValue = cell.getOldValue();
                    String newValue = cell.getNewValue();

                    RequestUpdateLotoProcedureCode request = new RequestUpdateLotoProcedureCode(
                            lotoProcedure.getLotoProcedureId(),
                            newValue
                    );
                    if (lotoProcedureInteractor.UpdateLotoProcedureCode(request)) {
                        lotoProcedure.setLotoProcedureCode(newValue);
                        lotoProcedureTable.refresh();

                    } else {
                        lotoProcedure.setLotoProcedureCode(oldValue);
                        lotoProcedureTable.edit(-1, null);
                        lotoProcedureTable.refresh();

                    }
                }
        );

        lotoProcedureNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lotoProcedureNameColumn.setOnEditCommit(cell -> {

                    LotoProcedureModel lotoProcedure = cell.getRowValue();
                    String oldValue = cell.getOldValue();
                    String newValue = cell.getNewValue();

                    RequestUpdateLotoProcedureName request = new RequestUpdateLotoProcedureName(
                            lotoProcedure.getLotoProcedureId(),
                            newValue
                    );
                    if (lotoProcedureInteractor.UpdateLotoProcedureName(request)) {
                        lotoProcedure.setLotoProcedureName(newValue);
                        lotoProcedureTable.refresh();

                    } else {
                        lotoProcedure.setLotoProcedureName(oldValue);
                        lotoProcedureTable.edit(-1, null);
                        lotoProcedureTable.refresh();

                    }
                }
        );
    }

    @Override
    public void setWorkProcedureTableItems(List<RowWorkProcedure> workProcedureList) {
        List<WorkProcedureModel> model = workProcedureList.stream().map(
                row -> new WorkProcedureModel(row.documentId(), row.documentCode(), row.documentName())
        ).toList();

        workProceduresObsList = FXCollections.observableArrayList(model);

        workProcedureTable.setItems(workProceduresObsList);
    }

    @Override
    public void setLotoProcedureTableItems(List<RowLotoProcedure> lotoProcedureList) {
        List<LotoProcedureModel> model = lotoProcedureList.stream().map(
                row -> new LotoProcedureModel(row.documentId(), row.documentCode(), row.documentName())
        ).toList();

        lotoProceduresObsList = FXCollections.observableArrayList(model);

        lotoProcedureTable.setItems(lotoProceduresObsList);
    }

    @Override
    public void setWorkProcedureTableTitles(String documentCode, String documentName) {
        workProcedureCodeColumn.setText(documentCode);
        workProcedureNameColumn.setText(documentName);
    }

    @Override
    public void setLotoProcedureTableTitles(String documentCode, String documentName) {
        lotoProcedureCodeColumn.setText(documentCode);
        lotoProcedureNameColumn.setText(documentName);
    }
}
