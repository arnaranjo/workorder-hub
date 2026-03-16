package com.workorderhub.core.caseuse.spareparts;

import com.workorderhub.core.entity.SpareCategory;
import com.workorderhub.core.entity.SparePart;
import com.workorderhub.core.gateway.SpareCategoryGateway;
import com.workorderhub.core.gateway.SparePartGateway;

import java.util.List;

public class SparePartInteractor implements SparePartInput {

    private final SparePartOutput output;
    private final SparePartGateway sparePartGateway;
    private final SpareCategoryGateway spareCategoryGateway;

    public SparePartInteractor(
            SparePartOutput output,
            SparePartGateway sparePartGateway,
            SpareCategoryGateway spareCategoryGateway
    ) {
        this.output = output;
        this.sparePartGateway = sparePartGateway;
        this.spareCategoryGateway = spareCategoryGateway;
    }

    @Override
    public void retrieveSpareParts() {

        List<SpareCategory> spareCategoryList = spareCategoryGateway.GetAllCategories();

        List<SparePartRow> sparePartRowList = sparePartGateway.getSparePartList().stream()
                .map(sparePart -> new SparePartRow(
                        sparePart.getSpareId(),
                        sparePart.getSpareName(),
                        sparePart.getSpareNumber(),
                        sparePart.getSpareDescription(),
                        sparePart.getSpareStock(),
                        getCategoryNameById(sparePart.getSpareCategory(), spareCategoryList)
                )).toList();

        output.populatesSparePartTable(sparePartRowList);
    }

    @Override
    public void retrieveSpareCategories() {
        List<SpareCategory> spareCategoryList = spareCategoryGateway.GetAllCategories();

        List<ResponseSparePartCategories> categoryList = spareCategoryList.stream()
                .map(category -> new ResponseSparePartCategories(
                        category.getCategoryID(),
                        category.getCategoryName()
                )).toList();

        output.provideSparePartCategories(categoryList);
    }

    @Override
    public void createSparePart(RequestNewSparePart request) {
        if (
                request.spareName().isEmpty() ||
                        request.spareNumber().isEmpty() ||
                        request.spareDescription().isEmpty()
        ) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);

        } else {

            int categoryId = getCategoryIdByName(request.spareCategory(), spareCategoryGateway.GetAllCategories());
            SparePart sparePart = new SparePart.Builder()
                    .withSpareName(request.spareName())
                    .withSpareNumber(request.spareNumber())
                    .withSpareDescription(request.spareDescription())
                    .withSpareStock(request.spareStock())
                    .withSpareCategory(categoryId)
                    .build();

            int sparePartId = sparePartGateway.insertSparePart(sparePart);
            if (sparePartId != 0) {
                SparePartRow newRow = new SparePartRow(
                        sparePartId,
                        sparePart.getSpareName(),
                        sparePart.getSpareNumber(),
                        sparePart.getSpareDescription(),
                        sparePart.getSpareStock(),
                        request.spareCategory()
                );

                ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                        sparePart.getSpareName(),
                        sparePart.getSpareNumber()
                );
                output.displayConfirmation(response, SparePartEnum.SPARE_PART_ADDED);
                output.addNewRow(newRow);

            } else {
                output.displayError(SparePartEnum.SPARE_PART_ADDITION_ERROR);

            }
        }
    }

    @Override
    public void updateSpareName(RequestUpdateSpareName request) {
        ResponseUpdateSpareName responseCell = new ResponseUpdateSpareName(
                request.oldSparePartName(),
                request.tableRowIndex()
        );

        if (request.newSparePartName().isEmpty()) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);
            output.updateNameCell(responseCell);

        } else if (sparePartGateway.updateSparePartName(request.sparePartId(), request.newSparePartName())) {

            ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                    request.newSparePartName(),
                    request.partNumber()
            );
            output.displayConfirmation(response, SparePartEnum.SPARE_PART_UPDATED);

            responseCell = new ResponseUpdateSpareName(
                    request.newSparePartName(),
                    request.tableRowIndex()
            );
            output.updateNameCell(responseCell);

        } else {
            output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
            output.updateNameCell(responseCell);

        }
    }

    @Override
    public void updateSpareNumber(RequestUpdateSpareNumber request) {
        ResponseUpdateSpareNumber responseCell = new ResponseUpdateSpareNumber(
                request.oldPartNumber(),
                request.tableRowIndex()
        );

        if (request.newPartNumber().isEmpty()) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);
            output.updateNumberCell(responseCell);

        } else if (sparePartGateway.updateSparePartName(request.sparePartId(), request.newPartNumber())) {

            ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                    request.sparePartName(),
                    request.newPartNumber()
            );
            output.displayConfirmation(response, SparePartEnum.SPARE_PART_UPDATED);

            responseCell = new ResponseUpdateSpareNumber(
                    request.newPartNumber(),
                    request.tableRowIndex()
            );
            output.updateNumberCell(responseCell);

        } else {
            output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
            output.updateNumberCell(responseCell);

        }
    }

    @Override
    public void updateSpareDescription(RequestUpdateSpareDescription request) {
        ResponseUpdateSpareDescription responseCell = new ResponseUpdateSpareDescription(
                request.oldDescription(),
                request.tableRowIndex()
        );

        if (request.newDescription().isEmpty()) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);
            output.updateDescriptionCell(responseCell);

        } else if (sparePartGateway.updateSparePartDescription(request.sparePartId(), request.newDescription())) {

            ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                    request.sparePartName(),
                    request.partNumber()
            );
            output.displayConfirmation(response, SparePartEnum.SPARE_PART_UPDATED);

            responseCell = new ResponseUpdateSpareDescription(
                    request.newDescription(),
                    request.tableRowIndex()
            );
            output.updateDescriptionCell(responseCell);

        } else {
            output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
            output.updateDescriptionCell(responseCell);
        }
    }

    @Override
    public void increaseSpareStock(RequestUpdateSpareStock request) {
        ResponseUpdateSpareStock responseCell = new ResponseUpdateSpareStock(
                request.newValue(),
                request.tableRowIndex()
        );

        if (request.newValue() < 0) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);
        } else {

            int newStockValue = request.currentStock() + request.newValue();

            if (sparePartGateway.updateSparePartStock(request.sparePartId(), request.newValue())) {

                ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                        request.sparePartName(),
                        request.partNumber()
                );
                output.displayConfirmation(response, SparePartEnum.SPARE_PART_UPDATED);

                responseCell = new ResponseUpdateSpareStock(
                        newStockValue,
                        request.tableRowIndex()
                );
                output.updateStockCell(responseCell);

            } else {
                output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
                output.updateStockCell(responseCell);
            }
        }
    }

    @Override
    public void decreaseSpareStock(RequestUpdateSpareStock request) {
        ResponseUpdateSpareStock responseCell = new ResponseUpdateSpareStock(
                request.newValue(),
                request.tableRowIndex()
        );

        if (request.newValue() < 0) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);
        } else {

            int newStockValue = request.currentStock() - request.newValue();

            ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                    request.sparePartName(),
                    request.partNumber()
            );

            if (newStockValue > 0) {
                if (sparePartGateway.updateSparePartStock(request.sparePartId(), request.newValue())) {

                    output.displayConfirmation(response, SparePartEnum.SPARE_PART_UPDATED);

                    responseCell = new ResponseUpdateSpareStock(
                            newStockValue,
                            request.tableRowIndex()
                    );
                    output.updateStockCell(responseCell);

                } else {
                    output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
                    output.updateStockCell(responseCell);
                }
            } else {

                newStockValue = 0;
                if (sparePartGateway.updateSparePartStock(request.sparePartId(), request.newValue())) {

                    output.displayConfirmation(response, SparePartEnum.OUT_OF_STOCK);

                    responseCell = new ResponseUpdateSpareStock(
                            newStockValue,
                            request.tableRowIndex()
                    );
                    output.updateStockCell(responseCell);

                } else {
                    output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
                    output.updateStockCell(responseCell);
                }
            }
        }
    }

    @Override
    public void updateSpareCategory(RequestUpdateSpareCategory request) {
        ResponseUpdateSpareCategory responseCell = new ResponseUpdateSpareCategory(
                request.oldCategoryName(),
                request.tableRowIndex()
        );

        if (request.newCategoryName().isEmpty()) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);
            output.updateCategoryCell(responseCell);

        } else {

            int categoryId = getCategoryIdByName(request.newCategoryName(), spareCategoryGateway.GetAllCategories());
            if (sparePartGateway.updateSparePartCategory(request.sparePartId(), categoryId)) {

                ResponseSparePartConfirmation response = new ResponseSparePartConfirmation(
                        request.sparePartName(),
                        request.partNumber()
                );
                output.displayConfirmation(response, SparePartEnum.SPARE_PART_UPDATED);

                responseCell = new ResponseUpdateSpareCategory(
                        request.newCategoryName(),
                        request.tableRowIndex()
                );
                output.updateCategoryCell(responseCell);

            } else {
                output.displayError(SparePartEnum.SPARE_PART_UPDATE_ERROR);
                output.updateCategoryCell(responseCell);
            }
        }
    }

    @Override
    public void deleteSparePart(RequestDeleteSparePart request) {
        if (request.sparePartIndex() <= 0) {
            output.displayError(SparePartEnum.INCOMPLETE_INFORMATION);

        } else if (output.requestConfirmation(SparePartEnum.CONFIRM_DELETE_SPARE_PARTS)) {
            if (sparePartGateway.deleteSparePart(request.sparePartId())) {
                ResponseSparePartConfirmation confirmation = new ResponseSparePartConfirmation(
                        request.sparePartName(),
                        request.partNumber()
                );
                output.displayConfirmation(confirmation, SparePartEnum.SPARE_PART_DELETED);

                ResponseDeleteSparePart response = new ResponseDeleteSparePart(request.sparePartIndex());
                output.removeSparePartItem(response);

            } else {
                output.displayError(SparePartEnum.SPARE_PART_DELETION_ERROR);

            }
        }
    }

    /**
     * Helper method to get category sparePartName by ID
     *
     * @param categoryName      the category sparePartName to search for
     * @param spareCategoryList the list of spare categories to search through
     * @return the category ID if found, otherwise 0
     */
    private int getCategoryIdByName(String categoryName, List<SpareCategory> spareCategoryList) {
        for (SpareCategory category : spareCategoryList) {
            if (category.getCategoryName().equals(categoryName)) {
                return category.getCategoryID();
            }
        }
        return 0;
    }

    /**
     * Helper method to get category sparePartName by ID
     *
     * @param categoryId        the category ID to search for
     * @param spareCategoryList the list of spare categories to search through
     * @return the category sparePartName if found, otherwise "Unknown Category"
     */
    private String getCategoryNameById(int categoryId, List<SpareCategory> spareCategoryList) {
        for (SpareCategory category : spareCategoryList) {
            if (category.getCategoryID() == categoryId) {
                return category.getCategoryName();
            }
        }
        return "Unknown Category";
    }
}

