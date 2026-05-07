package com.workorderhub.infrastructure.ui.admin;

import com.workorderhub.core.caseuse.spareparts.*;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.SparePartCategoryModel;

import java.util.List;

public class SparePartPresenter implements SparePartOutput {

    private SparePartsView view;

    public SparePartPresenter() {
    }

    public void setView(SparePartsView view) {
        this.view = view;
    }

    @Override
    public boolean requestConfirmation(SparePartEnum sparePartEnum) {
        String title = "spareParts.deleteSpareConfirmation.title";
        String message = "spareParts.deleteSpareConfirmation.content";

        if (sparePartEnum != SparePartEnum.CONFIRM_DELETE_SPARE_PARTS) {
            return false;

        } else {
            return Util.requestConfirmation(title, message);

        }
    }

    @Override
    public void displayConfirmation(ResponseSparePartConfirmation response, SparePartEnum sparePartEnum) {
        switch (sparePartEnum) {
            case SPARE_PART_ADDED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("spareParts.addedSparePart") + " " +
                                response.spareName() + " (" + response.spareNumber() + ")"
                );
                break;

            case SPARE_PART_UPDATED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("spareParts.updatedSparePart") + " " +
                                response.spareName() + " (" + response.spareNumber() + ")"
                );
                break;

            case SPARE_PART_DELETED:

                view.setInfoDisplay(
                        PropertiesLoader.GetText("spareParts.deletedSparePart") + " " +
                                response.spareName() + " (" + response.spareNumber() + ")"
                );
                break;
        }
    }

    @Override
    public void updateNameCell(ResponseUpdateSpareName response) {
        view.setTableItemName(response.name(), response.row());
    }

    @Override
    public void updateNumberCell(ResponseUpdateSpareNumber response) {
        view.setTableItemPartNumber(response.partNumber(), response.row());
    }

    @Override
    public void updateDescriptionCell(ResponseUpdateSpareDescription response) {
        view.setTableItemDescription(response.description(), response.row());
    }

    @Override
    public void updateStockCell(ResponseUpdateSpareStock response) {
        view.setTableItemStock(response.stock(), response.row());
    }

    @Override
    public void updateCategoryCell(ResponseUpdateSpareCategory response) {
        view.setTableItemCategory(response.category(), response.row());
    }

    @Override
    public void addNewRow(SparePartRow sparePartRow) {
        view.addSparePartItem(sparePartRow);
    }

    @Override
    public void removeSparePartItem(ResponseDeleteSparePart response) {
        view.removeSparePartItem(response.sparePartIndex());
    }

    @Override
    public void displayError(SparePartEnum sparePartEnum) {
        switch (sparePartEnum) {
            case INCOMPLETE_INFORMATION:
                view.setInfoDisplay(PropertiesLoader.GetText("spareParts.incompleteInfo"));
                break;

            case SPARE_PART_ADDITION_ERROR:
                view.setInfoDisplay(PropertiesLoader.GetText("spareParts.addSparePartError"));
                break;

            case SPARE_PART_UPDATE_ERROR:
                view.setInfoDisplay(PropertiesLoader.GetText("spareParts.updateSparePartError"));
                break;

            case SPARE_PART_DELETION_ERROR:
                view.setInfoDisplay(PropertiesLoader.GetText("spareParts.deleteSparePartError"));
                break;
        }
    }

    @Override
    public void populatesSparePartTable(List<SparePartRow> sparePartRowList) {
        view.setSparePartTableItems(sparePartRowList);
    }

    @Override
    public void provideSparePartCategories(List<ResponseSparePartCategories> categoryList) {
        List<SparePartCategoryModel> modelList = categoryList.stream()
                .map(category -> new SparePartCategoryModel(
                        category.categoryID(),
                        category.categoryName()
                )).toList();

        view.setCategoryList(modelList);
    }
}
