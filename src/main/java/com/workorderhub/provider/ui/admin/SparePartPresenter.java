package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.spareparts.ResponseSparePartCategories;
import com.workorderhub.core.caseuse.spareparts.SparePartOutput;
import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.core.caseuse.spareparts.SparePartsView;
import com.workorderhub.provider.models.SparePartCategoryModel;

import java.util.List;

public class SparePartPresenter implements SparePartOutput {

    private SparePartsView view;

    public SparePartPresenter() {
    }

    public void setView (SparePartsView view){
        this.view = view;
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
