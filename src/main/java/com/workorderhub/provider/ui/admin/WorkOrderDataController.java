package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;
import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
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
public class WorkOrderDataController implements WorkOrderDataView {

    private WorkOrderInput interactor;


    //"Work Order" tab content - Options
    @FXML
    protected CheckBox cBoxSchedule;
    @FXML
    protected CheckBox cBoxWorkProcedure;
    @FXML
    protected CheckBox cBoxWorkPermit;

    //"Work Order" tab content - Plant element selection
    @FXML
    protected TextField plantElementField;
    @FXML
    protected Label pElementTag;
    @FXML
    protected Label pElementLocation;
    @FXML
    protected Label pElementDescription;

    //"Work Order" tab content - Category selection
    @FXML
    protected ChoiceBox<String> workOrderCategorySelector = new ChoiceBox<>();
    @FXML
    protected ListView<String> workOrderCategoryView = new ListView<>();

    //"Work Order" tab content - Task description
    @FXML
    protected TextArea workOrderDescriptionArea;

    //"Work Order" tab content - Holder selection
    @FXML
    protected ChoiceBox<String> holderSelector  = new ChoiceBox<>();;
    @FXML
    protected Label holderName;
    @FXML
    protected Label holderContact;

    private List<UserModel> holderList;
    private boolean isHolderSelected;

    //"Work Order" tab content - Participant selection
    @FXML
    protected ChoiceBox<String> technicianSelector = new ChoiceBox<>();;
    @FXML
    protected ListView<String> participantView;

    private List<UserModel> technicianList;
    private List<ParticipantModel> participantList;

    //"Work Order" tab content - Spare parts selection
    @FXML
    protected ChoiceBox<String> sparePartSearch  = new ChoiceBox<>();
    @FXML
    protected TextField sparePartField;
    @FXML
    protected Spinner<Integer> numSelected;
    @FXML
    protected ListView<String> sparePartSelectedView = new ListView<>();
    @FXML
    protected TableView<SparePartModel> sparePartTable;

    private TableColumn<SparePartModel, String> nameColumn;
    private TableColumn<SparePartModel, String> partNumberColumn;
    private TableColumn<SparePartModel, String> descriptionColumn;

    private FilteredList<SparePartModel> sparePartFilList;
    private List<SparePartModel> usedSparePartList;


    public void initialize() {

        //isPElementSelected = false;
        //assignedCategoryList = new ArrayList<>();

        //SetTexts();
        //SetCategoryList();

        // "Spare Part" tab content
        nameColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("spareName"));
        partNumberColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.partNumber"));
        partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("spareNumber"));
        descriptionColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemDescription"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("spareDescription"));
        sparePartTable.getColumns().add(nameColumn);
        sparePartTable.getColumns().add(partNumberColumn);
        sparePartTable.getColumns().add(descriptionColumn);


        //PopulateSparePartTable();
        //SetSparePartSearchFunction();
        //SparePartSelector();

        // "Assignment" tab content

    }

    public WorkOrderDataController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    @FXML
    protected void AddWorkOrderCategory(ActionEvent actionEvent) {
    }

    @FXML
    protected void ToggleWorkPermitTab(ActionEvent actionEvent) {
    }

    @FXML
    protected void ToggleWorkProcedureTab(ActionEvent actionEvent) {
    }

    @FXML
    protected void ToggleValidPeriodTab(ActionEvent actionEvent) {
    }

    @FXML
    protected void AddTechnician(ActionEvent actionEvent) {
    }

    @FXML
    protected void ClearTechnicianList(ActionEvent actionEvent) {
    }

    @FXML
    protected void AddSparePart(ActionEvent actionEvent) {
    }

    @FXML
    protected void CleanSparePartList(ActionEvent actionEvent) {
    }

    @FXML
    protected void ClearCategoryList(ActionEvent actionEvent) {
    }

    @FXML
    protected void SearchPlantElement(ActionEvent actionEvent) {
    }

    @FXML
    protected void SelectHolder(ActionEvent actionEvent) {
    }
}
