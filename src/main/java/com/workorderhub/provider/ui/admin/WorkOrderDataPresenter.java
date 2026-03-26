package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.ResponsePlantElement;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;
import com.workorderhub.core.entity.Category;
import com.workorderhub.provider.models.CategoryModel;

import java.util.List;

public class WorkOrderDataPresenter implements WorkOrderDataOutput {

    private WorkOrderDataView view;

    public WorkOrderDataPresenter() {
    }

    public void setView(WorkOrderDataView view) {
        this.view = view;
    }

    @Override
    public void setCategoryList(List<Category> categoryList) {
        List<CategoryModel> modelList = categoryList.stream()
                .map(category -> new CategoryModel(
                        category.getCategoryName(),
                        category.getCategoryDescription()
                )).toList();

        view.setCategoryList(modelList);
    }

    @Override
    public void displayPlantElementInfo(ResponsePlantElement response) {
        view.displayPlantElementInfo(
                response.elementTag(),
                response.elementDescription(),
                response.elementLocation()
        );
        view.confirmPlantElement();
    }
}
