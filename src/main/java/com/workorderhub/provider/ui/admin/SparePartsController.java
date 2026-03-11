package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.spareparts.RowSparePart;
import com.workorderhub.core.caseuse.spareparts.SparePartInput;
import com.workorderhub.core.caseuse.spareparts.SparePartsView;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.tablemodels.SparePartModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SparePartsController implements SparePartsView {

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
    private TableColumn<SparePartModel, Integer> categoryColumn;

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

    }

    public SparePartsController(SparePartInput interactor) {
        this.interactor = interactor;
    }

    @FXML
    private void increaseStock(ActionEvent actionEvent) {
    }

    @FXML
    private void decreaseStock(ActionEvent actionEvent) {
    }

    @FXML
    private void addSparePart(ActionEvent actionEvent) {
    }

    @FXML
    private void deleteSparePart(ActionEvent actionEvent) {
    }


    @Override
    public void setSparePartTableItems(List<RowSparePart> sparePartList) {
        List<SparePartModel> sparePartModelList = sparePartList.stream()
                .map(rowSparePart -> new SparePartModel(
                        rowSparePart.spareId(),
                        rowSparePart.spareName(),
                        rowSparePart.spareNumber(),
                        rowSparePart.spareDescription(),
                        rowSparePart.spareStock(),
                        rowSparePart.spareCategory()
                )).toList();

        sparePartsObsList = FXCollections.observableList(sparePartModelList);
        sparePartFilList = new FilteredList<>(sparePartsObsList);

        sparePartTable.setItems(sparePartFilList);
    }
}
