package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.workorder.*;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.models.CategoryModel;
import com.workorderhub.infrastructure.models.ParticipantModel;
import com.workorderhub.infrastructure.models.UsedSparePartModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller associated with the creation of a new work order form.
 * CODE REFERENCES
 * 1 - <a href="https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#sum">Allows only digits.<a/>
 * 2 - <a href="https://stackoverflow.com/questions/62513192/javafx-datepicker-disable-future-dates">Disables date picker days.<a/>
 * 3 - <a href="https://stackoverflow.com/questions/77576921/java-fx-tableview-access-cell-contents-for-searching">Searches field for a TableView.<a/>
 */
public class WorkOrderMainController implements WorkOrderMainView {

    private final WorkOrderInput interactor;

    private List<RequestAssignCategory> requestAssignCategories;
    private List<RequestParticipants> requestParticipants;
    private List<RequestUseSpareParts> requestSpareParts;

    private long workOrderId;

    @FXML
    protected WorkOrderDataView dataViewController;
    @FXML
    protected WorkOrderPeriodView validPeriodViewController;
    @FXML
    protected WorkOrderProcedureView workProcedureViewController;
    @FXML
    protected WorkOrderPermitView workPermitViewController;

    //General

    @FXML
    private Label mainLabel;
    @FXML
    private Button updateButton;
    @FXML
    private Button createButton;

    //"Valid period" tab content

    @FXML
    protected Tab validPeriodTab;

    //"Work procedure" tab content

    @FXML
    protected Tab workProcedureTab;

    //"Work permit" tab content

    @FXML
    protected Tab workPermitTab;

    public void initialize() {

        // Disable the "Valid period", "Work procedure" and "Work permit" tabs by default, as they are optional.
        validPeriodTab.setDisable(true);
        workProcedureTab.setDisable(true);
        workPermitTab.setDisable(true);

        this.requestAssignCategories = new ArrayList<>();
        this.requestParticipants = new ArrayList<>();
        this.requestSpareParts = new ArrayList<>();

        this.workOrderId = AppState.getInstance().getWorkOrderId();

        // In supervisor mode the form is read-only.
        if (AppState.getInstance().isSupervisorMode()) {
            createButton.setDisable(true);
            updateButton.setDisable(true);
            mainLabel.setStyle(PropertiesLoader.GetText("workOrder.defaultStyle"));
            mainLabel.setText(
                    PropertiesLoader.GetText("workOrder.defaultEdit")
                            + ": " + workOrderId
            );
            interactor.loadWorkOrderElement(new RequestLoadWorkOrder(workOrderId));

        } else if (AppState.getInstance().isAdminMode()) {

            // Check if we are creating a new work order or editing an existing one.
            mainLabel.setStyle(PropertiesLoader.GetText("workOrder.defaultStyle"));
            if (workOrderId == 0) {
                mainLabel.setText(PropertiesLoader.GetText("workOrder.defaultNew"));
                createButton.setDisable(false);
                updateButton.setDisable(true);

            } else {
                mainLabel.setText(
                        PropertiesLoader.GetText("workOrder.defaultEdit")
                        + ": " + workOrderId
                );

                createButton.setDisable(true);
                updateButton.setDisable(false);
                interactor.loadWorkOrderElement(new RequestLoadWorkOrder(workOrderId));

            }
        }
    }

    public WorkOrderMainController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    // General methods

    @FXML
    protected void createNewWorkOrder() {

        boolean hasValidPeriod = validPeriodViewController.isPeriodSelected();
        LocalDate startDate = hasValidPeriod ? validPeriodViewController.getStartDate() : null;
        LocalDate endDate = hasValidPeriod ? validPeriodViewController.getEndDate() : null;

        Integer workProcedureId = !workProcedureTab.isDisabled()
                ? workProcedureViewController.getSelectedWorkProcedure()
                : null;

        boolean hasWorkPermit = !workPermitTab.isDisabled();
        String permitDescription = hasWorkPermit ? workPermitViewController.getPermitDescription() : null;
        String lockDevices = hasWorkPermit ? workPermitViewController.getLockDevices() : null;
        Integer lotoProcedureId = hasWorkPermit ? workPermitViewController.getSelectedLotoProcedureId() : null;

        RequestNewWorkOrder request = new RequestNewWorkOrder.Builder()
                .withDescription(dataViewController.getWorkOrderDescription())
                .withPlantElementId(dataViewController.getPlantElementId())
                .withHolderId(dataViewController.getHolderId())
                .withValidPeriod(startDate, endDate)
                .withSelectedProcedure(workProcedureId)
                .withWorkPermit(permitDescription, lockDevices, lotoProcedureId)
                .build(
                );

        getAssociatedData();
        interactor.createWorkOrder(request, requestAssignCategories, requestParticipants, requestSpareParts);
    }

    @FXML
    protected void UpdateWorkOrder() {
        if (AppState.getInstance().getWorkOrderId() != 0) {

            boolean hasValidPeriod = !validPeriodTab.isDisabled() && validPeriodViewController.isPeriodSelected();
            LocalDate startDate = hasValidPeriod ? validPeriodViewController.getStartDate() : null;
            LocalDate endDate = hasValidPeriod ? validPeriodViewController.getEndDate() : null;

            Integer workProcedureId = !workProcedureTab.isDisabled()
                    ? workProcedureViewController.getSelectedWorkProcedure()
                    : null;

            boolean hasWorkPermit = !workPermitTab.isDisabled();
            String permitDescription = hasWorkPermit ? workPermitViewController.getPermitDescription() : null;
            String lockDevices = hasWorkPermit ? workPermitViewController.getLockDevices() : null;
            Integer lotoProcedureId = hasWorkPermit ? workPermitViewController.getSelectedLotoProcedureId() : null;

            RequestUpdateWorkOrder request = new RequestUpdateWorkOrder.Builder()
                    .withDescription(dataViewController.getWorkOrderDescription())
                    .withPlantElementId(dataViewController.getPlantElementId())
                    .withHolderId(dataViewController.getHolderId())
                    .withValidPeriod(startDate, endDate)
                    .withSelectedProcedure(workProcedureId)
                    .withWorkPermit(permitDescription, lockDevices, lotoProcedureId)
                    .build(
                    );

            getAssociatedData();
            interactor.updateWorkOrder(request, requestAssignCategories, requestParticipants, requestSpareParts);
        }
    }

    // Interface methods

    @Override
    public void toggleValidPeriodContent() {
        validPeriodTab.setDisable(!dataViewController.isValidPeriodRequired());
    }

    @Override
    public void toggleWorkProcedureContent() {
        workProcedureTab.setDisable(!dataViewController.isWorkProcedureRequired());
    }

    @Override
    public void toggleWorkPermitContent() {
        workPermitTab.setDisable(!dataViewController.isWorkPermitRequired());
    }

    // Internal methods

    /**
     * Retrieves the associated data from the data view and prepares the lists of request objects for categories,
     * participants, and spare parts to be sent to the interactor.
     */
    private void getAssociatedData() {
        if (requestAssignCategories == null) {
            requestAssignCategories = new ArrayList<>();
        } else {
            requestAssignCategories.clear();
        }
        for (CategoryModel category : dataViewController.getAssignedCategories()) {
            requestAssignCategories.add(new RequestAssignCategory(
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            ));
        }

        if (requestParticipants == null) {
            requestParticipants = new ArrayList<>();
        } else {
            requestParticipants.clear();
        }
        for (ParticipantModel participant : dataViewController.getParticipantsList()) {
            requestParticipants.add(new RequestParticipants(
                    participant.getUserId(),
                    participant.getUserName(),
                    participant.getUserEmail(),
                    participant.getUserPhoneNumber()
            ));
        }

        if (requestSpareParts == null) {
            requestSpareParts = new ArrayList<>();
        } else {
            requestSpareParts.clear();
        }
        for (UsedSparePartModel sparePart : dataViewController.getSparePartsList()) {
            requestSpareParts.add(new RequestUseSpareParts(
                    sparePart.getSparePartId(),
                    sparePart.getSelectedNumber(),
                    sparePart.getCurrentStock(),
                    sparePart.getSpareName(),
                    sparePart.getSpareNumber()
            ));
        }
    }
}
