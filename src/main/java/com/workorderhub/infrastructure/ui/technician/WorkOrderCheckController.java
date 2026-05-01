package com.workorderhub.infrastructure.ui.technician;

import com.workorderhub.core.caseuse.technician.*;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.models.UsedSparePartModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class WorkOrderCheckController implements WorkOrderCheckView {

    private WorkOrderCheckInput interactor;
    private long workOrderId;

    @FXML
    private Label mainLabel;
    @FXML
    private TextArea workOrderDescription;
    @FXML
    private Label plantElementTag;
    @FXML
    private Label planElementLocation;
    @FXML
    private Label planElementDescription;
    @FXML
    private Label holderNameLabel;
    @FXML
    private Label holderContactLabel;
    @FXML
    private Spinner<Integer> numfinallyUsed;
    @FXML
    private ListView<UsedSparePartModel> sparePartSelectedView;
    @FXML
    private ListView<String> participantView;
    @FXML
    private Label workProcedureLabel;
    @FXML
    private Label workOrderCategoryLabel;
    @FXML
    private TextArea technicianCommentArea;
    @FXML
    private TextArea workPermitDescriptionArea;
    @FXML
    private Label safetyLockLabel;
    @FXML
    private Label lotoProcedureCodeLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label statusLabel;


    public WorkOrderCheckController(WorkOrderCheckInput interactor) {
        this.interactor = interactor;
    }

    public void initialize() {

        this.workOrderId = AppState.getInstance().getWorkOrderId();

        mainLabel.setText(PropertiesLoader.GetText("workOrderReview.default") + workOrderId);
        mainLabel.setStyle(PropertiesLoader.GetText("technicianView.defaultStyle"));

        interactor.loadWorkOrderData(
                new RequestLoadWorkOrder(workOrderId)
        );

        setSparePartSelector();
    }


    // FXML methods

    @FXML
    private void addTechnicianComment() {
        RequestSetComment request = new RequestSetComment(
                workOrderId,
                technicianCommentArea.getText()
        );
        interactor.setTechnicianComment(request);
    }

    @FXML
    private void editSparePartNumber() {
        UsedSparePartModel selectedItem = sparePartSelectedView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            //Error
            return;
        }

        RequestUpdateUsedSparePart request = new RequestUpdateUsedSparePart(
                workOrderId,
                selectedItem.getSparePartId(),
                selectedItem.getSelectedNumber(),
                selectedItem.getCurrentStock(),
                numfinallyUsed.getValue()
        );

        interactor.updateUsedSparePart(request);
    }

    // View interface methods

    @Override
    public void setPeriodDate(LocalDate startDate, LocalDate endDate) {
        startDateLabel.setText(startDate.toString());
        endDateLabel.setText(endDate.toString());
    }

    @Override
    public void setWorkOrderData(String description, String status, List<String> categories) {
        workOrderDescription.setText(description);
        statusLabel.setText(status);
        workOrderCategoryLabel.setText(String.join(", ", categories));
    }

    @Override
    public void setPlantElement(String tag, String location, String description) {
        plantElementTag.setText(tag);
        planElementLocation.setText(location);
        planElementDescription.setText(description);
    }

    @Override
    public void setHolderInfo(String name, String contact) {
        holderNameLabel.setText(name);
        holderContactLabel.setText(contact);
    }

    @Override
    public void setWorkPermitInfo(
            String description,
            String safetyLock,
            String lotoProcedureName,
            String lotoProcedureCode
    ) {
        workPermitDescriptionArea.setText(description);
        safetyLockLabel.setText(safetyLock);
        lotoProcedureCodeLabel.setText(
                lotoProcedureName + " (" + lotoProcedureCode + ")"
        );
    }

    @Override
    public void setWorkProcedureInfo(String name, String code) {
        workProcedureLabel.setText(name + " (" + code + ")");
    }

    @Override
    public void setParticipants(List<String> participants) {
        participantView.getItems().clear();
        participantView.getItems().addAll(participants);
    }

    @Override
    public void setSparePartTableItems(List<UsedSparePartModel> sparePartList) {
        List<UsedSparePartModel> data = (sparePartList != null) ? sparePartList : Collections.emptyList();

        if (sparePartSelectedView.getItems() == null) {
            sparePartSelectedView.setItems(FXCollections.observableArrayList(data));
        } else {
            sparePartSelectedView.getItems().setAll(data);
        }

        if (sparePartSelectedView.getCellFactory() == null) {
            configureCellFactory();
        }
    }

    public void updateData(List<UsedSparePartModel> sparePartList) {
        if (sparePartSelectedView.getItems() == null) {
            sparePartSelectedView.setItems(FXCollections.observableArrayList(sparePartList));
        } else {
            sparePartSelectedView.getItems().setAll(sparePartList);
        }
    }

    // Auxiliary methods

    /**
     * Initialises and restricts the input format of the selector.
     */
    private void setSparePartSelector() {
        numfinallyUsed.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1));
        numfinallyUsed.setEditable(true);

        TextFormatter<Integer> newStockFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
        numfinallyUsed.getEditor().setTextFormatter(newStockFormatter);
    }

    private void configureCellFactory() {
        this.sparePartSelectedView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(UsedSparePartModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getSpareName()
                            + "( " + item.getSpareNumber()
                            + " ) - Number to use: " + item.getSelectedNumber()
                            + " - Current Stock: " + item.getCurrentStock()
                    );
                }
            }
        });
    }
}
