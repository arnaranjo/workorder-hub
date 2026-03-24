package com.workorderhub.provider.ui.admin;

import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.models.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller associated with the creation of a new work order form.
 * CODE REFERENCES
 * 1 - <a href="https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#sum">Allows only digits.<a/>
 * 2 - <a href="https://stackoverflow.com/questions/62513192/javafx-datepicker-disable-future-dates">Disables date picker days.<a/>
 * 3 - <a href="https://stackoverflow.com/questions/77576921/java-fx-tableview-access-cell-contents-for-searching">Searches field for a TableView.<a/>
 */
public class WorkOrderController {
    
    //General
    @FXML
    private Label mainLabel;

    //"Work Order" tab content - Options
    @FXML
    private CheckBox cBoxSchedule;
    @FXML
    private CheckBox cBoxWorkProcedure;
    @FXML
    private CheckBox cBoxWorkPermit;

    //"Work Order" tab content - Plant element selection
    @FXML
    private TextField plantElementField;
    @FXML
    private Label pElementTag;
    @FXML
    private Label pElementLocation;
    @FXML
    private Label pElementDescription;

    //"Work Order" tab content - Category selection
    @FXML
    private ChoiceBox<String> workOrderCategorySelector = new ChoiceBox<>();
    @FXML
    private ListView<String> workOrderCategoryView = new ListView<>();

    //"Work Order" tab content - Task description
    @FXML
    private TextArea workOrderDescriptionArea;

    //"Work Order" tab content - Holder selection
    @FXML
    private ChoiceBox<String> holderSelector  = new ChoiceBox<>();;
    @FXML
    private Label holderName;
    @FXML
    private Label holderContact;

    private List<UserModel> holderList;
    private boolean isHolderSelected;

    //"Work Order" tab content - Participant selection
    @FXML
    private ChoiceBox<String> technicianSelector = new ChoiceBox<>();;
    @FXML
    private ListView<String> participantView;

    private List<UserModel> technicianList;
    private List<ParticipantModel> participantList;

    //"Work Order" tab content - Spare parts selection
    @FXML
    private ChoiceBox<String> sparePartSearch  = new ChoiceBox<>();
    @FXML
    private TextField sparePartField;
    @FXML
    private Spinner<Integer> numSelected;
    @FXML
    private ListView<String> sparePartSelectedView = new ListView<>();
    @FXML
    private TableView<SparePartModel> sparePartTable;

    private TableColumn<SparePartModel, String> nameColumn;
    private TableColumn<SparePartModel, String> partNumberColumn;
    private TableColumn<SparePartModel, String> descriptionColumn;

    private FilteredList<SparePartModel> sparePartFilList;
    private List<SparePartModel> usedSparePartList;

    //"Valid period" tab content
    @FXML
    private Tab validPeriodTab;
    @FXML
    private TableView<WorkOrderModel> workFrontTable;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label confirmationLabel;

    private TableColumn<WorkOrderModel, Long> workOrderIdColumn;
    private TableColumn<WorkOrderModel, LocalDate> workOrderStartDateColumn;
    private TableColumn<WorkOrderModel, LocalDate> workOrderEndDateColumn;
    private TableColumn<WorkOrderModel, String> workOrderDescriptionColumn;
    private ObservableList<WorkOrderModel> workOrderInfoObsList;
    private boolean isPeriodSelected;

    //"Work procedure" tab content
    @FXML
    private Tab workProcedureTab;
    @FXML
    private ChoiceBox<String> workProcedureSearch;
    @FXML
    private TextField workProcedureField;
    @FXML
    private TableView<WorkProcedureModel> workProcedureTable;
    @FXML
    private Label workProcedureCode;

    private TableColumn<WorkProcedureModel, Integer> workProcedureIdColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureCodeColumn;
    private TableColumn<WorkProcedureModel, String> workProcedureNameColumn;
    private FilteredList<WorkProcedureModel> workProcedureFilList;
    private List<WorkProcedureModel> workProcedureList;
    private int workProcedureId;
    private boolean isWorkProcedureSelected;
    private WorkProcedureModel selectedWorkProcedure;

    //"Work permit" tab content
    @FXML
    private Tab workPermitTab;
    @FXML
    private TextArea workPermitDescriptionArea;
    @FXML
    private CheckBox cBoxLoto;
    @FXML
    private TextField lockDevicesField;
    @FXML
    private ChoiceBox<String> lotoProcedureSearch;
    @FXML
    private TextField lotoProcedureField;
    @FXML
    private TableView<LotoProcedureModel> lotoProcedureTable;
    @FXML
    private Button selectLotoButton;
    @FXML
    private Label lotoProcedureCode;

    private TableColumn<LotoProcedureModel, Integer> lotoProcedureIdColumn;
    private TableColumn<LotoProcedureModel, String> lotoProcedureCodeColumn;
    private TableColumn<LotoProcedureModel, String> lotoProcedureNameColumn;
    private FilteredList<LotoProcedureModel> lotoProcedureFilList;
    private List<LotoProcedureModel> lotoProcedureList;
    private boolean isLotoProcedureSelected;
    private LotoProcedureModel selectedLotoProcedure;
    private WorkPermitModel newWorkPermit;

    public void initialize() {
        // "Work Order" tab content
        validPeriodTab.setDisable(true);
        workProcedureTab.setDisable(true);
        workPermitTab.setDisable(true);

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

        //SetUsersList();

        // "Valid Period" tab content
        workOrderIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.wOrderId"));
        workOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        workOrderStartDateColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.startDate"));
        workOrderStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        workOrderEndDateColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.endDate"));
        workOrderEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        workOrderDescriptionColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.validPeriod.description"));
        workOrderDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        workFrontTable.setPlaceholder(new Label(PropertiesLoader.GetText("workOrder.validPeriod.placeHolder")));
        workFrontTable.getColumns().add(workOrderIdColumn);
        workFrontTable.getColumns().add(workOrderStartDateColumn);
        workFrontTable.getColumns().add(workOrderEndDateColumn);
        workFrontTable.getColumns().add(workOrderDescriptionColumn);

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        //PopulateWorkOrderTable();
        //SetDatesRestrictions();

        // "Work Procedures" tab content
        workProcedureIdColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.wProcedure.wProcedureId"));
        workProcedureIdColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureId"));
        workProcedureCodeColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.wProcedure.code"));
        workProcedureCodeColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        workProcedureNameColumn = new TableColumn<>(PropertiesLoader.GetText("workOrder.wProcedure.name"));
        workProcedureNameColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureName"));
        workProcedureTable.getColumns().add(workProcedureIdColumn);
        workProcedureTable.getColumns().add(workProcedureCodeColumn);
        workProcedureTable.getColumns().add(workProcedureNameColumn);

        //isWProcedureSelected = false;
        //PopulateWorkProcedureTable();
        //SetWorkProcedureSearchFunction();

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

    //General

    public void CreateNewWorkOrder(ActionEvent actionEvent) {
    }


    //Work Order Tab methods

    @FXML
    private void SearchPlantElement(ActionEvent actionEvent) {
    }

    @FXML
    private void AddWorkOrderCategory(ActionEvent actionEvent) {
    }

    @FXML
    private void ToggleWorkPermitTab(ActionEvent actionEvent) {
    }

    @FXML
    private void ToggleWorkProcedureTab(ActionEvent actionEvent) {
    }

    @FXML
    private void ToggleValidPeriodTab(ActionEvent actionEvent) {
    }

    @FXML
    private void SelectHolder(ActionEvent actionEvent) {
    }

    @FXML
    private void AddTechnician(ActionEvent actionEvent) {
    }

    @FXML
    private void ClearTechnicianList(ActionEvent actionEvent) {
    }

    @FXML
    private void ClearCategoryList(ActionEvent actionEvent) {
    }

    @FXML
    private void AddSparePart(ActionEvent actionEvent) {
    }

    @FXML
    private void CleanSparePartList(ActionEvent actionEvent) {
    }


    //Valid period tab method

    @FXML
    private void ConfirmDates(ActionEvent actionEvent) {
    }


    //Work procedure tab method

    @FXML
    private void SelectWorkProcedure(ActionEvent actionEvent) {
    }


    //Work permit tab method

    @FXML
    private void SelectLotoProcedure(ActionEvent actionEvent) {
    }

    @FXML
    private void ToggleLoto(ActionEvent actionEvent) {
    }
}
