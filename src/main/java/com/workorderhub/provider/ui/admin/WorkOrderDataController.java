package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.RequestPlantElement;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;
import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;
import com.workorderhub.provider.models.CategoryModel;
import com.workorderhub.provider.models.ParticipantModel;
import com.workorderhub.provider.models.SparePartModel;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
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
    protected Label tagLabel;
    @FXML
    protected Label locationLabel;
    @FXML
    protected Label descriptionLabel;

    private boolean isPElementSelected;

    //"Work Order" tab content - Category selection
    @FXML
    protected ChoiceBox<String> workOrderCategorySelector = new ChoiceBox<>();
    @FXML
    protected ListView<String> workOrderCategoryView = new ListView<>();

    private List<CategoryModel> categoryList;
    private List<Integer> assignedCategoryList;

    //"Work Order" tab content - Task description
    @FXML
    protected TextArea workOrderDescriptionArea;

    //"Work Order" tab content - Holder selection
    @FXML
    protected ChoiceBox<String> holderSelector = new ChoiceBox<>();
    @FXML
    protected Label holderName;
    @FXML
    protected Label holderContact;

    private List<ParticipantModel> holderList;
    private int holderId;
    private boolean isHolderSelected;

    //"Work Order" tab content - Participant selection
    @FXML
    protected ChoiceBox<String> technicianSelector = new ChoiceBox<>();
    @FXML
    protected ListView<String> participantView;

    private List<ParticipantModel> technicianList;
    private List<Integer> participantList;

    //"Work Order" tab content - Spare parts selection
    @FXML
    protected ChoiceBox<String> sparePartSearch = new ChoiceBox<>();
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

        this.interactor.retrieveWorkOrderCategories();
        this.interactor.retrieveHoldersList();
        this.interactor.retrieveTechnicianList();

        this.assignedCategoryList = new ArrayList<>();

        // "Spare Part" content
        this.nameColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemName"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("spareName"));
        this.partNumberColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.partNumber"));
        this.partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("spareNumber"));
        this.descriptionColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemDescription"));
        this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("spareDescription"));
        this.sparePartTable.getColumns().add(nameColumn);
        this.sparePartTable.getColumns().add(partNumberColumn);
        this.sparePartTable.getColumns().add(descriptionColumn);

        //PopulateSparePartTable();
        setSparePartSearchFunction();
        setSparePartSelector();
    }

    public WorkOrderDataController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    // FXML methods for "Work Order" tab content

    @FXML
    protected void addWorkOrderCategory() {
        if (!categoryList.isEmpty()) {
            if (!workOrderCategoryView.getItems().contains(this.workOrderCategorySelector.getValue())) {

                //Add to the ListView
                this.workOrderCategoryView.getItems().add(this.workOrderCategorySelector.getValue());

                for (CategoryModel category : categoryList) {
                    if (category.getName().equals(this.workOrderCategorySelector.getValue())) {

                        //Add to the AssignedList
                        assignedCategoryList.add(category.getId());

                    }
                }
            }
        }
    }

    @FXML
    protected void toggleWorkPermitTab() {
        this.interactor.togglePermitSelectionDisable();
    }

    @FXML
    protected void toggleWorkProcedureTab() {
        this.interactor.toggleProcedureSelectionDisable();
    }

    @FXML
    protected void toggleValidPeriodTab() {
        this.interactor.toggleValidPeriodDisable();
    }

    @FXML
    protected void addTechnician() {
        if (participantList == null){
            this.participantList = new ArrayList<>();
        }

        boolean isParticipant = false;

        for (ParticipantModel technician : technicianList) {
            if (technician.getUserName().equals(technicianSelector.getValue())) {

                //Check if the technician is already added as a participant
                int selectedId = technician.getUserId();
                for (Integer userId : participantList) {
                    if (selectedId == userId) {
                        isParticipant = true;

                        String title = "workOrder.assignment.errorTitle";
                        String message = "workOrder.assignment.errorMessage";
                        Util.ShowMessage(title, message);

                        break;
                    }
                }

                //Add the technician as a participant if not already added
                if (!isParticipant) {
                    this.participantList.add(selectedId);
                    displayTechnicianInfo(
                            technician.getUserName(),
                            technician.getUserPhoneNumber(),
                            technician.getUserEmail()
                    );
                }
            }
        }
    }

    @FXML
    protected void clearTechnicianList() {
        this.participantList.clear();
        this.participantView.getItems().clear();
    }

    @FXML
    protected void addSparePart() {

    }

    @FXML
    protected void cleanSparePartList() {
        this.usedSparePartList.clear();
        this.sparePartFilList.setPredicate(_ -> true);
        this.sparePartSelectedView.getItems().clear();
        this.sparePartSelectedView.refresh();
    }

    @FXML
    protected void clearCategoryList() {
        this.assignedCategoryList.clear();
        this.workOrderCategoryView.getItems().clear();
    }

    @FXML
    protected void searchPlantElement(ActionEvent actionEvent) {
        RequestPlantElement requestPlantElement = new RequestPlantElement(
                this.plantElementField.getText()
        );
        this.interactor.getPlantElement(requestPlantElement);
    }

    @FXML
    protected void selectHolder() {
        for (ParticipantModel holder : holderList) {
            if (holder.getUserName().equals(holderSelector.getValue())) {
                this.holderId = holder.getUserId();
                isHolderSelected = true;

                String titleConf = "workOrder.assignment.confTitle";
                String messageConf = "workOrder.assignment.confMessage";

                Util.ShowMessage(titleConf, messageConf, holder.getUserName());

                displayHolderInfo(
                        holder.getUserName(),
                        holder.getUserPhoneNumber(),
                        holder.getUserEmail()
                );
            }
        }
    }


    // Interface methods for "Work Order" tab content

    @Override
    public void setCategoryList(List<CategoryModel> categoryList) {
        this.categoryList = categoryList;
        for (CategoryModel category : categoryList) {
            this.workOrderCategorySelector.getItems().add(category.getName());
        }
        this.workOrderCategorySelector.getSelectionModel().selectFirst();
    }

    @Override
    public void setTechnicianList(List<ParticipantModel> technicianList) {
        this.technicianList = technicianList;
        for (ParticipantModel technician : technicianList) {
            this.technicianSelector.getItems().add(technician.getUserName());
        }
        this.technicianSelector.getSelectionModel().selectFirst();
    }

    @Override
    public void displayTechnicianInfo(String technicianName, String technicianPhoneNumber, String technicianEmail) {
        this.participantView.getItems().add(
                technicianName + " (" + technicianEmail + " / " + technicianPhoneNumber + ")"
        );
    }

    @Override
    public void setHolderList(List<ParticipantModel> holderList) {
        this.holderList = holderList;
        for (ParticipantModel holder : holderList) {
            this.holderSelector.getItems().add(holder.getUserName());
        }
        this.holderSelector.getSelectionModel().selectFirst();
    }

    @Override
    public void displayHolderInfo(String holderName, String holderPhoneNumber, String holderEmail) {
        this.holderName.setText(holderName);
        this.holderContact.setText(holderPhoneNumber + " (" + holderEmail + ")");
    }

    @Override
    public void displayPlantElementInfo(String elementTag, String elementDescription, String elementLocation) {
        this.tagLabel.setText(elementTag);
        this.descriptionLabel.setText(elementDescription);
        this.locationLabel.setText(elementLocation);
    }

    @Override
    public void confirmPlantElement() {
        this.isPElementSelected = true;
    }


    // Controller methods for "Work Order" tab content

    /**
     * Sets the function to filter the spare part table according to the selected criteria, name or spare part number.
     * Ref. [3]
     */
    private void setSparePartSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("workOrder.sparePart.searchList");
        sparePartSearch.getItems().addAll(list);
        sparePartSearch.setValue(list[0]);

        sparePartField.setPromptText(PropertiesLoader.GetText("workOrder.sparePart.searchTag"));
        sparePartField.textProperty().addListener((obs, oldValue, newValue) -> {
            switch (sparePartSearch.getValue()) {
                case "Name":
                    sparePartFilList.setPredicate(p ->
                            p.getSpareName().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;

                case "Part Number":
                    sparePartFilList.setPredicate(p ->
                            p.getSpareNumber().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
            }
        });
    }

    /**
     * Initialises and restricts the input format of the selector.
     * Ref. [1]
     */
    private void setSparePartSelector() {
        numSelected.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1));
        numSelected.setEditable(true);

        TextFormatter<Integer> newStockFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
        numSelected.getEditor().setTextFormatter(newStockFormatter);
    }
}
