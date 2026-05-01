package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.workorder.RequestPlantElement;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;
import com.workorderhub.core.caseuse.workorder.WorkOrderInput;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.CategoryModel;
import com.workorderhub.infrastructure.models.ParticipantModel;
import com.workorderhub.infrastructure.models.SparePartModel;
import com.workorderhub.infrastructure.models.UsedSparePartModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller associated with the creation of a new work order form.
 * CODE REFERENCES
 * 1 - <a href="https://stackoverflow.com/questions/77576921/java-fx-tableview-access-cell-contents-for-searching">Searches field for a TableView.<a/>
 * 2 - <a href="https://stackoverflow.com/questions/62513192/javafx-datepicker-disable-future-dates">Disables date picker days.<a/>
 */
public class WorkOrderDataController implements WorkOrderDataView {

    private final WorkOrderInput interactor;


    //"Work Order" tab content - Options
    @FXML
    protected CheckBox cBoxValidPeriod;
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

    private int plantElementId;
    private boolean isPElementSelected;

    //"Work Order" tab content - Category selection
    @FXML
    protected ChoiceBox<CategoryModel> workOrderCategorySelector = new ChoiceBox<>();
    @FXML
    protected ListView<String> workOrderCategoryView = new ListView<>();

    private List<CategoryModel> assignedCategoryList;

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
    private List<ParticipantModel> participantList;

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
    private TableColumn<SparePartModel, Integer> stockColumn;

    private FilteredList<SparePartModel> sparePartFilList;
    private List<UsedSparePartModel> usedSparePartList;


    public void initialize() {

        this.interactor.retrieveWorkOrderCategories();
        this.interactor.retrieveHoldersList();
        this.interactor.retrieveTechnicianList();
        this.interactor.retrieveSparePartsList();

        this.isPElementSelected = false;
        this.isHolderSelected = false;

        /**
         * Initialises the lists that will contain the information of the categories, participants
         * and spare parts selected to be associated with the work order. Those lists can be optional when the
         * work order is created.
         */
        this.assignedCategoryList = new ArrayList<>();
        this.participantList = new ArrayList<>();
        this.usedSparePartList = new ArrayList<>();

        // "Spare Part" content
        this.nameColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemName"));
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("spareName"));
        this.partNumberColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.partNumber"));
        this.partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("spareNumber"));
        this.descriptionColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemDescription"));
        this.descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("spareDescription"));
        this.stockColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemStock"));
        this.stockColumn.setCellValueFactory(new PropertyValueFactory<>("spareStock"));
        this.sparePartTable.getColumns().add(nameColumn);
        this.sparePartTable.getColumns().add(partNumberColumn);
        this.sparePartTable.getColumns().add(descriptionColumn);
        this.sparePartTable.getColumns().add(stockColumn);

        setSparePartSearchFunction();
        setSparePartSelector();
    }

    public WorkOrderDataController(WorkOrderInput interactor) {
        this.interactor = interactor;
    }

    // FXML methods for "Work Order" tab content

    @FXML
    protected void addWorkOrderCategory() {
        CategoryModel selected = this.workOrderCategorySelector.getValue();
        if (selected == null) return;

        boolean alreadyAssigned = assignedCategoryList.stream()
                .anyMatch(c -> c.getId() == selected.getId());

        if (!alreadyAssigned) {
            assignedCategoryList.add(selected);
            workOrderCategoryView.getItems().add(selected.getName() + ": " + selected.getDescription());
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
                for (ParticipantModel pt : participantList) {
                    if (selectedId == pt.getUserId()) {
                        isParticipant = true;

                        String title = "workOrder.assignment.errorTitle";
                        String message = "workOrder.assignment.errorMessage";
                        Util.ShowMessage(title, message);

                        break;
                    }
                }

                //Add the technician as a participant if not already added
                if (!isParticipant) {
                    this.participantList.add(technician);
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
        int empty = 0;

        if (sparePartTable.getSelectionModel().isEmpty()) {
            Util.ShowMessage("workOrder.defaultNew", "workOrder.sparePart.noSelected");

        } else {
            SparePartModel selectedSparePart = sparePartTable.getSelectionModel().getSelectedItem();

            if (selectedSparePart.getSpareStock() != empty) {
                int numberSelected;

                //Check if the selected number of spare parts is available in stock, if not, select the remaining stock
                if ((selectedSparePart.getSpareStock() - numSelected.getValue()) > empty) {
                    numberSelected = numSelected.getValue();
                    selectedSparePart.setSpareStock(selectedSparePart.getSpareStock() - numSelected.getValue());
                } else {
                    numberSelected = selectedSparePart.getSpareStock();
                    selectedSparePart.setSpareStock(empty);
                }

                UsedSparePartModel usedSparePart = new UsedSparePartModel();
                usedSparePart.setSparePartId(selectedSparePart.getSparePartId());
                usedSparePart.setSelectedNumber(numberSelected);
                usedSparePart.setCurrentStock(selectedSparePart.getSpareStock());
                usedSparePart.setSpareName(selectedSparePart.getSpareName());
                usedSparePart.setSpareNumber(selectedSparePart.getSpareNumber());

                setSparePartSelectionView(
                        selectedSparePart.getSpareStock(),
                        selectedSparePart.getSpareName(),
                        selectedSparePart.getSpareNumber(),
                        numberSelected
                );

                usedSparePartList.add(usedSparePart);
                updateFilter();

            } else {
                Util.ShowMessage("workOrder.defaultNew", "workOrder.sparePart.outOfStock");

            }
        }
    }

    @FXML
    protected void cleanSparePartList() {
        this.usedSparePartList.clear();
        this.sparePartFilList.setPredicate(_ -> true);
        this.sparePartSelectedView.getItems().clear();
        this.sparePartSelectedView.refresh();
        this.interactor.retrieveSparePartsList();
    }

    @FXML
    protected void clearCategoryList() {
        this.assignedCategoryList.clear();
        this.workOrderCategoryView.getItems().clear();
    }

    @FXML
    protected void searchPlantElement() {
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


    // Interface methods

    @Override
    public void setCategoryList(List<CategoryModel> categoryList) {
        this.workOrderCategorySelector.setItems(FXCollections.observableArrayList(categoryList));
        this.workOrderCategorySelector.getSelectionModel().selectFirst();

        this.workOrderCategorySelector.setConverter(new StringConverter<>() {
            @Override
            public String toString(CategoryModel c) {
                return c == null ? "" : c.getName();
            }

            @Override
            public CategoryModel fromString(String s) {
                return null;
            }
        });
    }


    @Override
    public void displayAssignedCategoryList(CategoryModel model) {
        boolean alreadyAssigned = assignedCategoryList.stream()
                .anyMatch(c -> c.getId() == model.getId());

        if (!alreadyAssigned) {
            assignedCategoryList.add(model);
            workOrderCategoryView.getItems().add(model.getName() + ": " + model.getDescription());
        }
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
    public void setParticipantList(List<ParticipantModel> participantList) {
        this.participantList.addAll(participantList);
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
    public void setHolderId(int holderId) {
        this.holderId = holderId;
    }

    @Override
    public void displayPlantElementInfo(String elementTag, String elementDescription, String elementLocation) {
        this.tagLabel.setText(elementTag);
        this.descriptionLabel.setText(elementDescription);
        this.locationLabel.setText(elementLocation);
    }

    @Override
    public void setWorkOrderDescription(String description) {
        this.workOrderDescriptionArea.setText(description);
    }

    @Override
    public boolean isValidPeriodRequired() {
        return this.cBoxValidPeriod.isSelected();
    }

    @Override
    public boolean isWorkProcedureRequired() {
        return this.cBoxWorkProcedure.isSelected();
    }

    @Override
    public boolean isWorkPermitRequired() {
        return this.cBoxWorkPermit.isSelected();
    }

    @Override
    public void setRequirements(boolean validPeriodRequired, boolean workProcedureRequired, boolean workPermitRequired) {
        this.cBoxValidPeriod.setSelected(validPeriodRequired);
        interactor.toggleValidPeriodDisable();

        this.cBoxWorkProcedure.setSelected(workProcedureRequired);
        interactor.toggleProcedureSelectionDisable();

        this.cBoxWorkPermit.setSelected(workPermitRequired);
        interactor.togglePermitSelectionDisable();
    }

    @Override
    public String getWorkOrderDescription() {
        return this.workOrderDescriptionArea.getText();
    }

    @Override
    public void confirmPlantElement(int elementId) {
        this.isPElementSelected = true;
        this.plantElementId = elementId;
    }

    @Override
    public void confirmHolder(int holderId) {
        this.isHolderSelected = true;
        this.holderId = holderId;
    }

    @Override
    public void setSparePartTableItems(List<SparePartModel> sparePartList) {
        ObservableList<SparePartModel> sparePartObsList = FXCollections.observableList(sparePartList);
        this.sparePartFilList = new FilteredList<>(sparePartObsList);
        this.sparePartTable.setItems(sparePartFilList);
    }

    @Override
    public void setUsedSparePartList(List<UsedSparePartModel> usedSparePartList) {
        this.usedSparePartList.addAll(usedSparePartList);
    }

    @Override
    public void setSparePartSelectionView(int stock, String spareName, String spareNumber, int quantity) {
        if (stock == 0) {
            this.sparePartSelectedView.getItems().add(
                    spareName + " (" + spareNumber + ") Selected number: " + quantity + " (Last items in stock)"
            );
        } else {
            this.sparePartSelectedView.getItems().add(
                    spareName + " (" + spareNumber + ") Selected number: " + quantity
            );
        }
    }

    @Override
    public Integer getPlantElementId() {
        if (!isPElementSelected) {
            return null;
        }
        return this.plantElementId;
    }

    @Override
    public List<CategoryModel> getAssignedCategories() {
        return this.assignedCategoryList;
    }

    @Override
    public Integer getHolderId() {
        if (!isHolderSelected) {
            return null;
        }
        return this.holderId;
    }

    @Override
    public List<ParticipantModel> getParticipantsList() {
        return this.participantList;
    }

    @Override
    public List<UsedSparePartModel> getSparePartsList() {
        return this.usedSparePartList;
    }

    // Auxiliary methods

    /**
     * Sets the function to filter the spare part table according to the selected criteria, name or spare part number.
     * Ref. [1]
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
     * Ref. [2]
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

    /**
     * Updates the filter of the spare part table to not show the spare parts that have already been selected.
     */
    private void updateFilter() {
        sparePartFilList.setPredicate(sparePart -> usedSparePartList.stream().noneMatch(
                usedSparePart -> usedSparePart.getSparePartId() == sparePart.getSparePartId()
        ));
    }
}
