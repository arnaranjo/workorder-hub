package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.provider.models.CategoryModel;

import java.util.List;

public interface WorkOrderDataView {

    /**
     * Sets the list of work order categories to be displayed in the view.
     * @param categoryList An array of strings representing the work order categories.
     */
    void setCategoryList(List<CategoryModel> categoryList);

}
