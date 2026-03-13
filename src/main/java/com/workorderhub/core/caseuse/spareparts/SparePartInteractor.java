package com.workorderhub.core.caseuse.spareparts;

import com.workorderhub.core.entity.SpareCategory;
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

    private String getCategoryNameById(int categoryId, List<SpareCategory> spareCategoryList) {
        for (SpareCategory category : spareCategoryList) {
            if (category.getCategoryID() == categoryId) {
                return category.getCategoryName();
            }
        }
        return "Unknown Category";
    }
}

