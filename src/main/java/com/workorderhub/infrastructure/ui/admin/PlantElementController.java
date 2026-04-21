package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.plantelement.*;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.models.PlantElementModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlantElementController implements PlantElementView {

    private final PlantElementInput interactor;
    private ObservableList<PlantElementModel> plantElementsObsList;
    private FilteredList<PlantElementModel> plantElementFilList;

    @FXML
    private Label mainLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private TextField tagField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField locationField;
    @FXML
    private DatePicker inspectionDatePicker;
    @FXML
    private CheckBox inspectionFrcSelector;
    @FXML
    private Spinner<Integer> inspectionFrqSpinner;
    @FXML
    private ChoiceBox<String> searchCriteriaSelector;
    @FXML
    private TextField tagSearchField;
    @FXML
    private Label elementSelectedLabel;
    @FXML
    private TableView<PlantElementModel> plantElementsTable;

    private TableColumn<PlantElementModel, String> tagColumn;
    private TableColumn<PlantElementModel, String> descriptionColumn;
    private TableColumn<PlantElementModel, String> locationColumn;
    private TableColumn<PlantElementModel, LocalDate> inspectionColumn;
    private TableColumn<PlantElementModel, Integer> frequencyColumn;

    public PlantElementController(
            PlantElementInput interactor
    ) {
        this.interactor = interactor;
    }

    public void initialize() {
        mainLabel.setText(PropertiesLoader.GetText("plantElement.default"));
        mainLabel.setStyle(PropertiesLoader.GetText("plantElement.defaultStyle"));

        tagColumn = new TableColumn<>(PropertiesLoader.GetText("plantElement.elementTag"));
        tagColumn.setCellValueFactory(new PropertyValueFactory<>("elementTag"));
        descriptionColumn = new TableColumn<>(PropertiesLoader.GetText("plantElement.elementDescription"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("elementDescription"));
        locationColumn = new TableColumn<>(PropertiesLoader.GetText("plantElement.elementLocation"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("elementLocation"));
        inspectionColumn = new TableColumn<>(PropertiesLoader.GetText("plantElement.inspectionDate"));
        inspectionColumn.setCellValueFactory(new PropertyValueFactory<>("inspectionDate"));
        frequencyColumn = new TableColumn<>(PropertiesLoader.GetText("plantElement.inspectionFrequency"));
        frequencyColumn.setCellValueFactory(new PropertyValueFactory<>("inspectionFrequency"));

        plantElementsTable.getColumns().add(tagColumn);
        plantElementsTable.getColumns().add(descriptionColumn);
        plantElementsTable.getColumns().add(locationColumn);
        plantElementsTable.getColumns().add(inspectionColumn);
        plantElementsTable.getColumns().add(frequencyColumn);

        inspectionFrqSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0));

        ToggleInspectionItems();
        SetSearchFunction();
        SetDeleteLabelFunction();
        setPlantElementTableEdition();

        interactor.retrievePlantElements();
    }

    @FXML
    private void AddElement() {
        RequestNewPlantElement newPlantElement = new RequestNewPlantElement(
                tagField.getText(),
                descriptionArea.getText(),
                locationField.getText(),
                inspectionDatePicker.getValue(),
                inspectionFrqSpinner.getValue()
        );
        interactor.createPlantElement(newPlantElement);
        ClearFields();
    }

    @FXML
    private void DeleteElement() {
        if (!plantElementsTable.getSelectionModel().isEmpty()) {
            RequestDeletePlantElement request = new RequestDeletePlantElement(
                    plantElementsTable.getSelectionModel().getSelectedItem().getElementId(),
                    plantElementsTable.getSelectionModel().getSelectedItem().getElementTag(),
                    plantElementsTable.getSelectionModel().getFocusedIndex()
            );
            interactor.deletePlantElement(request);

        }
    }

    @FXML
    private void ToggleInspectionItems() {
        inspectionDatePicker.setDisable(!inspectionDatePicker.isDisable());
        inspectionFrqSpinner.setDisable(!inspectionFrqSpinner.isDisable());
    }

    /**
     * Sets the listener to filter the table and show the instrument or equipment.
     */
    private void SetSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("plantElement.searchList");
        searchCriteriaSelector.getItems().addAll(list);
        searchCriteriaSelector.setValue(list[0]);

        tagSearchField.setPromptText(PropertiesLoader.GetText("plantElement.searchTag"));
        tagSearchField.textProperty().addListener((_, _, newValue) -> {
            switch (searchCriteriaSelector.getValue()) {
                case "Tag":
                    plantElementFilList.setPredicate(p ->
                            p.getElementTag().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Description":
                    plantElementFilList.setPredicate(p ->
                            p.getElementDescription().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Location":
                    plantElementFilList.setPredicate(p ->
                            p.getElementLocation().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
            }
        });

        searchCriteriaSelector.getSelectionModel().selectedItemProperty().addListener(
                (_, _, newValue) -> {
                    //Reset table and text field when new choice is selected.
                    if (newValue != null) {
                        tagSearchField.setText("");
                    }
                });

    }

    /**
     * Sets the function to show the plant element selected from the list.
     */
    private void SetDeleteLabelFunction() {
        plantElementsTable.getSelectionModel().selectedItemProperty().addListener(
                (_, _, newSelection) -> {
                    if (newSelection != null) {
                        elementSelectedLabel.setText("Selected: " + newSelection.getElementTag());
                    }
                });
    }

    /**
     * Resets the fields and selectors.
     */
    private void ClearFields() {
        tagField.setText(null);
        descriptionArea.setText(null);
        locationField.setText(null);
        inspectionDatePicker.setValue(null);
        inspectionFrqSpinner.getValueFactory().setValue(0);
        inspectionFrcSelector.setSelected(false);
        ToggleInspectionItems();
    }

    @Override
    public void setInfoDisplay(String message) {
        infoLabel.setText(message);
    }

    @Override
    public void setPlantElementTableEdition() {
        plantElementsTable.setEditable(true);

        tagColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tagColumn.setOnEditCommit(cell -> {
            RequestUpdateElementTag request = new RequestUpdateElementTag(
                    cell.getRowValue().getElementId(),
                    cell.getNewValue(),
                    cell.getOldValue(),
                    cell.getTablePosition().getRow()
            );

            interactor.updateElementTag(request);
        });

        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(cell -> {
            RequestUpdateElementDescription request = new RequestUpdateElementDescription(
                    cell.getRowValue().getElementId(),
                    cell.getRowValue().getElementTag(),
                    cell.getNewValue(),
                    cell.getOldValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateElementDescription(request);
        });

        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setOnEditCommit(cell -> {
            RequestUpdateElementLocation request = new RequestUpdateElementLocation(
                    cell.getRowValue().getElementId(),
                    cell.getRowValue().getElementTag(),
                    cell.getNewValue(),
                    cell.getOldValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateElementLocation(request);
        });

        inspectionColumn.setCellFactory(cell -> new CellDatePicker());
        inspectionColumn.setOnEditCommit(cell -> {
            RequestUpdateElementInspectionDate request = new RequestUpdateElementInspectionDate(
                    cell.getRowValue().getElementId(),
                    cell.getRowValue().getElementTag(),
                    cell.getNewValue(),
                    cell.getOldValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateElementInspectionDate(request);
        });

        frequencyColumn.setCellFactory(cell -> new CellSpinner());
        frequencyColumn.setOnEditCommit(cell -> {
            RequestUpdateElementInspectionFrequency request = new RequestUpdateElementInspectionFrequency(
                    cell.getRowValue().getElementId(),
                    cell.getRowValue().getElementTag(),
                    cell.getNewValue(),
                    cell.getOldValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateElementInspectionFrequency(request);
        });

    }

    @Override
    public void setPlantElementTableItems(List<RowPlantElement> PlantElementList) {
        List<PlantElementModel> models = PlantElementList.stream().map(row ->
                new PlantElementModel(
                        row.elementId(),
                        row.elementTag(),
                        row.elementDescription(),
                        row.elementLocation(),
                        row.inspectionDate(),
                        row.inspectionFrequency()
                )
        ).toList();

        plantElementsObsList = FXCollections.observableArrayList(models);
        plantElementFilList = new FilteredList<>(plantElementsObsList);

        plantElementsTable.setItems(plantElementFilList);
    }

    @Override
    public void addPlantElementItem(RowPlantElement rowPlantElement) {
        PlantElementModel model = new PlantElementModel(
                rowPlantElement.elementId(),
                rowPlantElement.elementDescription(),
                rowPlantElement.elementLocation(),
                rowPlantElement.elementLocation(),
                rowPlantElement.inspectionDate(),
                rowPlantElement.inspectionFrequency()
        );
        plantElementsObsList.add(model);
    }

    @Override
    public void removePlantElementItem(int itemIndex) {
        plantElementsObsList.remove(itemIndex);
    }

    @Override
    public void setTableItemTag(String elementTag, int row) {
        PlantElementModel model = plantElementsTable.getItems().get(row);
        model.setElementTag(elementTag);
    }

    @Override
    public void setTableItemDescription(String elementDescription, int row) {
        PlantElementModel model = plantElementsTable.getItems().get(row);
        model.setElementDescription(elementDescription);
        plantElementsTable.refresh();
    }

    @Override
    public void setTableItemLocation(String elementLocation, int row) {
        PlantElementModel model = plantElementsTable.getItems().get(row);
        model.setElementLocation(elementLocation);
        plantElementsTable.refresh();
    }

    @Override
    public void setTableItemInspectionDate(LocalDate date, int row) {
        PlantElementModel model = plantElementsTable.getItems().get(row);
        model.setInspectionDate(date);
        plantElementsTable.refresh();
    }

    @Override
    public void setTableItemInspectionFrequency(int frequency, int row) {
        PlantElementModel model = plantElementsTable.getItems().get(row);
        model.setInspectionFrequency(frequency);
        plantElementsTable.refresh();
    }

    /**
     * Custom table cell that shows a date picker during the edition.
     */
    private static class CellDatePicker extends TableCell<PlantElementModel, LocalDate> {

        private DatePicker datePicker;

        private CellDatePicker() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                CreateDatePicker();
                setText(null);
                setGraphic(datePicker);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setGraphic(null);
            setText(GetDateFormatted());
        }

        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (datePicker != null) {
                        datePicker.setValue(GetDate());
                    }
                    setText(null);
                    setGraphic(datePicker);
                } else {
                    setText(GetDateFormatted());
                    setGraphic(null);
                }
            }
        }

        private void CreateDatePicker() {
            datePicker = new DatePicker(GetDate());
            datePicker.setMinWidth(getWidth() * 0.9);
            datePicker.setOnAction((e) -> {
                commitEdit(datePicker.getValue() == null ? null : datePicker.getValue());
            });
        }

        private LocalDate GetDate() {
            return getItem() == null ? null : getItem();
        }

        private String GetDateFormatted() {
            return getItem() == null ? null : getItem().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    /**
     * Custom table cell that shows a spinner selector during the edition.
     */
    private static class CellSpinner extends TableCell<PlantElementModel, Integer> {

        private Spinner<Integer> spinner;

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                CreateSpinner();
                setText(null);
                setGraphic(spinner);
            }
        }

        @Override
        public void cancelEdit() {
            if (spinner != null) {
                commitEdit(spinner.getValue());
            }
            super.cancelEdit();
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (spinner != null) {
                        spinner.getValueFactory().setValue(item);
                    }
                    setText(null);
                    setGraphic(spinner);
                } else {
                    setText(item == 0 ? "" : item.toString());
                    setGraphic(null);
                }
            }
        }

        private void CreateSpinner() {
            spinner = new Spinner<>(0, 60, getItem());
            spinner.setMinWidth(getWidth() * 0.9);
        }
    }
}
