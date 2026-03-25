package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.core.caseuse.workorder.WorkOrderMainView;
import com.workorderhub.core.caseuse.workorder.WorkOrderPeriodView;
import com.workorderhub.core.caseuse.workorder.WorkOrderPermitView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

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
    protected WorkOrderDataController DataViewController;
    @FXML
    protected WorkOrderPeriodView validPeriodViewController;
    @FXML
    protected WorkOrderProcedureController workProcedureViewController;
    @FXML
    protected WorkOrderPermitController workPermitViewController;

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
    }

    public WorkOrderMainController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    //General
    @FXML
    protected void CreateNewWorkOrder() {
    }
}
