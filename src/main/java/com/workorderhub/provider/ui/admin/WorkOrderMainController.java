package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.*;
import com.workorderhub.provider.models.CategoryModel;
import com.workorderhub.provider.models.ParticipantModel;
import com.workorderhub.provider.models.UsedSparePartModel;
import javafx.fxml.FXML;
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

    private WorkOrderInput interactor;

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
    protected Label mainLabel;

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

    }

    public WorkOrderMainController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    // General methods

    @FXML
    protected void createNewWorkOrder() {

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

        RequestNewWorkOrder request = new RequestNewWorkOrder.Builder()
                .withDescription(dataViewController.getWorkOrderDescription())
                .withPlantElementId(dataViewController.getPlantElementId())
                .withHolderId(dataViewController.getHolderId())
                .withValidPeriod(startDate, endDate)
                .withSelectedProcedure(workProcedureId)
                .withWorkPermit(permitDescription, lockDevices, lotoProcedureId)
                .build(
        );

        List<RequestAssignCategory> requestAssignCategories = new ArrayList<>();
        for (CategoryModel category : dataViewController.getAssignedCategories()) {
            requestAssignCategories.add(new RequestAssignCategory(
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            ));
        }

        List<RequestParticipants> requestParticipants = new ArrayList<>();
        for (ParticipantModel participant : dataViewController.getParticipantsList()) {
            requestParticipants.add(new RequestParticipants(
                    participant.getUserId(),
                    participant.getUserName(),
                    participant.getUserEmail(),
                    participant.getUserPhoneNumber()
            ));
        }

        List<RequestUseSpareParts> requestSpareParts = new ArrayList<>();
        for (UsedSparePartModel sparePart : dataViewController.getSparePartsList()) {
            requestSpareParts.add(new RequestUseSpareParts(
                    sparePart.getSparePartId(),
                    sparePart.getSelectedNumber(),
                    sparePart.getSpareName(),
                    sparePart.getSpareNumber()
            ));
        }
        interactor.createWorkOrder(
                request,
                requestAssignCategories,
                requestParticipants,
                requestSpareParts
        );
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
}
