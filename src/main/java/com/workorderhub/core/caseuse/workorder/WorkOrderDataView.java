package com.workorderhub.core.caseuse.workorder;

import com.workorderhub.provider.models.CategoryModel;

import java.util.List;

public interface WorkOrderDataView {

    /**
     * Sets the list of work order categories to be displayed in the view.
     * @param categoryList An array of strings representing the work order categories.
     */
    void setCategoryList(List<CategoryModel> categoryList);

    /**
     * Displays the information of the retrieved plant element
     * @param elementTag The tag of the plant element.
     * @param elementDescription The description of the plant element.
     * @param elementLocation The location of the plant element.
     */
    void displayPlantElementInfo(String elementTag, String elementDescription, String elementLocation);

    /**
     * Confirms the plant element is valid and can be associated with the work order.
     * To be used after the plant element information is displayed, to allow the user to confirm
     * the association of the plant element with the work order.
     */
    void confirmPlantElement();
}
