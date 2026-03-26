package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.core.entity.Category;

import java.util.List;

public interface WorkOrderDataOutput {

    /**
     * Sets the list of work order categories to be displayed in the view.
     * @param categoryList A list of Category objects representing the work order categories.
     */
    void setCategoryList(List<Category> categoryList);

    /**
     * Displays the information of the retrieved plant element
     * @param response Response with the plant element information to be displayed in the view.
     */
    void displayPlantElementInfo(ResponsePlantElement response);
}
