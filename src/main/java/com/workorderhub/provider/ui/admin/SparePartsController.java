package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.spareparts.*;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.models.SparePartCategoryModel;
import com.workorderhub.provider.models.SparePartModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.util.List;

/**
 * Controller associated with the spare parts inventory window.
 * CODE REFERENCES
 * 1 - Allow only digits <a href="https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#sum"/>
 */
public class SparePartsController implements SparePartsView {

    //Stock selectors values:
    int MIN_STOCK = 0;
    int MAX_STOCK = 500;
    int INCREMENT_STOCK = 1;

    private final SparePartInput interactor;

    @FXML
    private Label mainLabel;
    @FXML
    private ChoiceBox<String> searchCriteriaSelector;
    @FXML
    private TextField searchField;
    @FXML
    private Label stockLabel;
    @FXML
    private Spinner<Integer> newStockSelector;
    @FXML
    private TextField spareNameField;
    @FXML
    private TextField spareNumberField;
    @FXML
    private TextArea spareDescriptionArea;
    @FXML
    private ComboBox<String> spareCategorySelector;
    @FXML
    private Spinner<Integer> spareStockSelector;
    @FXML
    private Label sparePartSelectedLabel;
    @FXML
    private Label infoLabel;
    @FXML
    private TableView<SparePartModel> sparePartTable;

    private ObservableList<SparePartModel> sparePartsObsList;
    private FilteredList<SparePartModel> sparePartFilList;
    private TableColumn<SparePartModel, Integer> idColumn;
    private TableColumn<SparePartModel, String> nameColumn;
    private TableColumn<SparePartModel, String> partNumberColumn;
    private TableColumn<SparePartModel, String> descriptionColumn;
    private TableColumn<SparePartModel, String> categoryColumn;

    private List<SparePartCategoryModel> categoryList;

    public void initialize() {
        mainLabel.setText(PropertiesLoader.GetText("spareParts.default"));
        mainLabel.setStyle(PropertiesLoader.GetText("spareParts.defaultStyle"));

        idColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemID"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("spareId"));
        nameColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemName"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("spareName"));
        partNumberColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.partNumber"));
        partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("spareNumber"));
        descriptionColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemDescription"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("spareDescription"));
        categoryColumn = new TableColumn<>(PropertiesLoader.GetText("spareParts.itemCategory"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("spareCategory"));

        sparePartTable.getColumns().add(idColumn);
        sparePartTable.getColumns().add(nameColumn);
        sparePartTable.getColumns().add(partNumberColumn);
        sparePartTable.getColumns().add(descriptionColumn);
        sparePartTable.getColumns().add(categoryColumn);

        interactor.retrieveSpareParts();
        interactor.retrieveSpareCategories();

        setSearchFunction();
        setStockInfoFunction();
        setDeleteLabelFunction();
        setSparePartTableEdition();
        createStockSelectors();

    }

    public SparePartsController(SparePartInput interactor) {
        this.interactor = interactor;
    }

    /**
     * Sets the function to filter the table according to the criteria selected: sparePartId, sparePartName or spare part number.
     */
    private void setSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("spareParts.searchList");
        searchCriteriaSelector.getItems().addAll(list);
        searchCriteriaSelector.setValue(list[0]);

        searchField.setPromptText(PropertiesLoader.GetText("spareParts.searchTag"));
        searchField.textProperty().addListener((_, _, newValue) -> {
            switch (searchCriteriaSelector.getValue()) {
                case "ID":
                    sparePartFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }
                        return String.valueOf(p.getSparePartId()).contains(newValue);
                    });

                    break;

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

        searchCriteriaSelector.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println(newValue);
                searchField.setText("");
            }
        });
    }

    /**
     * Sets the function to show the item stock.
     * When no item is selected, it shows the default text. When an item with 0 stock is selected,
     * it shows the out of stock text.
     * When an item with stock is selected, it shows the stock info text with the stock quantity.
     */
    private void setStockInfoFunction() {
        stockLabel.setText(PropertiesLoader.GetText("spareParts.stockInfo"));
        sparePartTable.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue == null) {
                stockLabel.setText(PropertiesLoader.GetText("spareParts.stockInfo"));

            } else if (newValue.getSpareStock() == 0) {
                stockLabel.setText(PropertiesLoader.GetText("spareParts.outOfStock"));

            } else {
                stockLabel.setText(PropertiesLoader.GetText("spareParts.stockInfo") + " " + newValue.getSpareStock());

            }
        });
    }

    /**
     * Initialises and restricts the input format of the selectors.
     * REF [1]
     */
    private void createStockSelectors() {
        newStockSelector.setEditable(true);
        spareStockSelector.setEditable(true);

        newStockSelector.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_STOCK, MAX_STOCK, INCREMENT_STOCK)
        );
        spareStockSelector.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(MIN_STOCK, MAX_STOCK, INCREMENT_STOCK)
        );

        TextFormatter<Integer> newStockFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
        newStockSelector.getEditor().setTextFormatter(newStockFormatter);

        TextFormatter<Integer> stockFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
        spareStockSelector.getEditor().setTextFormatter(stockFormatter);

        newStockSelector.getValueFactory().valueProperty().addListener(
                (_, oldValue, newValue) -> {
                    if (newValue == null) {
                        newStockSelector.getValueFactory().setValue(oldValue);
                    }
                });

        spareStockSelector.getValueFactory().valueProperty().addListener(
                (_, oldValue, newValue) -> {
                    if (newValue == null) {
                        spareStockSelector.getValueFactory().setValue(oldValue);
                    }
                });
    }

    /**
     * Sets the function so show the spare part selected from the list.
     */
    private void setDeleteLabelFunction() {
        sparePartTable.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) {
                sparePartSelectedLabel.setText(newSelection.getSpareName() +
                        " (ID " + newSelection.getSparePartId() + ")"
                );
            }
        });
    }

    @FXML
    private void increaseStock() {
        if (!sparePartTable.getSelectionModel().isEmpty()) {
            RequestUpdateSpareStock request = new RequestUpdateSpareStock(
                    sparePartTable.getSelectionModel().getSelectedItem().getSparePartId(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareName(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareNumber(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareStock(),
                    newStockSelector.getValue(),
                    sparePartTable.getSelectionModel().getFocusedIndex()
            );
            interactor.increaseSpareStock(request);
        }
    }

    @FXML
    private void decreaseStock() {
        if (!sparePartTable.getSelectionModel().isEmpty()) {
            RequestUpdateSpareStock request = new RequestUpdateSpareStock(
                    sparePartTable.getSelectionModel().getSelectedItem().getSparePartId(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareName(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareNumber(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareStock(),
                    newStockSelector.getValue(),
                    sparePartTable.getSelectionModel().getFocusedIndex()
            );
            interactor.decreaseSpareStock(request);
        }
    }


    @FXML
    private void addSparePart() {
        RequestNewSparePart newSparePart = new RequestNewSparePart(
                spareNameField.getText(),
                spareNumberField.getText(),
                spareDescriptionArea.getText(),
                spareCategorySelector.getValue(),
                spareStockSelector.getValue()
        );
        interactor.createSparePart(newSparePart);
        clearFields();
    }

    @FXML
    private void deleteSparePart() {
        if (!sparePartTable.getSelectionModel().isEmpty()) {
            RequestDeleteSparePart request = new RequestDeleteSparePart(
                    sparePartTable.getSelectionModel().getSelectedItem().getSparePartId(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareName(),
                    sparePartTable.getSelectionModel().getSelectedItem().getSpareNumber(),
                    sparePartTable.getSelectionModel().getFocusedIndex()
            );
            interactor.deleteSparePart(request);

        }
    }


    @Override
    public void setInfoDisplay(String message) {
        infoLabel.setText(message);
    }

    @Override
    public void clearFields() {
        spareNameField.clear();
        spareNumberField.clear();
        spareDescriptionArea.clear();
        spareStockSelector.getValueFactory().setValue(1);
    }

    @Override
    public void setCategoryList(List<SparePartCategoryModel> categoryList) {
        if (categoryList != null) {
            this.categoryList = categoryList;

            for (SparePartCategoryModel category : categoryList) {
                spareCategorySelector.getItems().add(category.getCategoryName());
            }
        }
    }

    @Override
    public void setSparePartTableEdition() {
        sparePartTable.setEditable(true);

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(cell -> {
            RequestUpdateSpareName request = new RequestUpdateSpareName(
                    cell.getRowValue().getSparePartId(),
                    cell.getOldValue(),
                    cell.getNewValue(),
                    cell.getRowValue().getSpareNumber(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateSpareName(request);
        });

        partNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        partNumberColumn.setOnEditCommit(cell -> {
            RequestUpdateSpareNumber request = new RequestUpdateSpareNumber(
                    cell.getRowValue().getSparePartId(),
                    cell.getRowValue().getSpareName(),
                    cell.getOldValue(),
                    cell.getNewValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateSpareNumber(request);

        });

        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(cell -> {
            RequestUpdateSpareDescription request = new RequestUpdateSpareDescription(
                    cell.getRowValue().getSparePartId(),
                    cell.getRowValue().getSpareName(),
                    cell.getRowValue().getSpareNumber(),
                    cell.getOldValue(),
                    cell.getNewValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateSpareDescription(request);

        });

        categoryColumn.setCellFactory(_ -> new CellCategorySelector());
        categoryColumn.setOnEditCommit(cell -> {
            RequestUpdateSpareCategory request = new RequestUpdateSpareCategory(
                    cell.getRowValue().getSparePartId(),
                    cell.getRowValue().getSpareName(),
                    cell.getRowValue().getSpareNumber(),
                    cell.getOldValue(),
                    cell.getNewValue(),
                    cell.getTablePosition().getRow()
            );
            interactor.updateSpareCategory(request);

        });
    }

    @Override
    public void setSparePartTableItems(List<SparePartRow> sparePartList) {
        List<SparePartModel> sparePartModelList = sparePartList.stream()
                .map(rowSparePart -> new SparePartModel(
                        rowSparePart.spareId(),
                        rowSparePart.spareName(),
                        rowSparePart.spareNumber(),
                        rowSparePart.spareDescription(),
                        rowSparePart.spareStock(),
                        rowSparePart.spareCategory()
                )).toList();

        sparePartsObsList = FXCollections.observableArrayList(sparePartModelList);
        sparePartFilList = new FilteredList<>(sparePartsObsList);

        sparePartTable.setItems(sparePartFilList);
    }

    @Override
    public void addSparePartItem(SparePartRow sparePartRow) {
        SparePartModel sparePartModel = new SparePartModel(
                sparePartRow.spareId(),
                sparePartRow.spareName(),
                sparePartRow.spareNumber(),
                sparePartRow.spareDescription(),
                sparePartRow.spareStock(),
                sparePartRow.spareCategory()
        );
        sparePartsObsList.add(sparePartModel);
    }

    @Override
    public void removeSparePartItem(int itemIndex) {
        sparePartsObsList.remove(itemIndex);
    }

    @Override
    public void setTableItemName(String sparePartName, int row) {
        SparePartModel model = sparePartTable.getItems().get(row);
        model.setSpareName(sparePartName);
        sparePartTable.refresh();
    }

    @Override
    public void setTableItemPartNumber(String sparePartNumber, int row) {
        SparePartModel model = sparePartTable.getItems().get(row);
        model.setSpareNumber(sparePartNumber);
        sparePartTable.refresh();
    }

    @Override
    public void setTableItemDescription(String sparePartDescription, int row) {
        SparePartModel model = sparePartTable.getItems().get(row);
        model.setSpareDescription(sparePartDescription);
        sparePartTable.refresh();
    }

    @Override
    public void setTableItemStock(int sparePartStock, int row) {
        SparePartModel model = sparePartTable.getItems().get(row);
        model.setSpareStock(sparePartStock);
    }

    @Override
    public void setTableItemCategory(String sparePartCategory, int row) {
        SparePartModel model = sparePartTable.getItems().get(row);
        model.setSpareCategory(sparePartCategory);
        sparePartTable.refresh();
    }


    /**
     * Custom table cell that shows a ComboBox selector during the edition.
     */
    private class CellCategorySelector extends TableCell<SparePartModel, String> {

        private ComboBox<String> categoryBox;

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                CreateCategoryBox();
                setText(null);
                setGraphic(categoryBox);
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setGraphic(null);
            setText(getItem());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    setText(null);
                    setGraphic(categoryBox);
                } else {
                    setText(item);
                    setGraphic(null);
                }
            }
        }

        private void CreateCategoryBox() {
            categoryBox = new ComboBox<>();
            categoryBox.setMinWidth(getWidth() * 0.9);

            for (SparePartCategoryModel spareCategory : categoryList) {
                categoryBox.getItems().add(spareCategory.getCategoryName());
            }

            categoryBox.getSelectionModel().select(getItem());
            categoryBox.setOnAction((_) -> commitEdit(categoryBox.getSelectionModel().getSelectedItem()));

        }
    }
}
