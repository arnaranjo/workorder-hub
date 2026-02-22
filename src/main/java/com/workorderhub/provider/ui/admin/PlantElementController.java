package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.plantelement.PlantElementInput;
import com.workorderhub.core.caseuse.plantelement.PlantElementRow;
import com.workorderhub.core.caseuse.plantelement.PlantElementView;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.tablemodels.PlantElementModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlantElementController implements PlantElementView {

    private PlantElementInput input;
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
        PlantElementInput input
    ){
        this.input = input;
    }

    public  void initialize(){
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

        ToggleSpinner();
        SetSearchFunction();
        SetDeleteLabelFunction();

        input.retrievePlantElements();
    }

    @FXML
    private void AddElement() {
    }

    @FXML
    private void DeleteElement() {
    }

    @FXML
    private void ToggleSpinner() {
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
        ToggleSpinner();
    }

    @Override
    public void setInfoDisplay(String message) {

    }

    @Override
    public void setPlantElementTableEdition() {

    }

    @Override
    public void setPlantElementTableItems(List<PlantElementRow> PlantElementList) {
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
            return getItem() == null ? null : getItem().format(DateTimeFormatter.ofPattern("d/M/yyyy"));
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
