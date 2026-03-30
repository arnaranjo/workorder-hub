package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.workorder.ResponsePlantElement;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;
import com.workorderhub.core.caseuse.workorder.WorkOrderEnum;
import com.workorderhub.core.entity.Category;
import com.workorderhub.core.entity.User;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.models.CategoryModel;
import com.workorderhub.provider.models.ParticipantModel;

import java.util.List;

public class WorkOrderDataPresenter implements WorkOrderDataOutput {

    private WorkOrderDataView viewController;

    public WorkOrderDataPresenter() {
    }

    public void setViewController(WorkOrderDataView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void setCategoryList(List<Category> categoryList) {
        List<CategoryModel> modelList = categoryList.stream()
                .map(category -> new CategoryModel(
                        category.getCategoryId(),
                        category.getCategoryName(),
                        category.getCategoryDescription()
                )).toList();

        viewController.setCategoryList(modelList);
    }

    @Override
    public void displayPlantElementInfo(ResponsePlantElement response) {
        viewController.displayPlantElementInfo(
                response.elementTag(),
                response.elementDescription(),
                response.elementLocation()
        );
        viewController.confirmPlantElement();
    }

    @Override
    public void displayTechnicianList(List<User> technicianList) {
        List<ParticipantModel> participantModels = technicianList.stream()
                .map(technician -> new ParticipantModel(
                        0,
                        technician.getUserId(),
                        technician.getUserName(),
                        technician.getUserPhoneNumber(),
                        technician.getUserEmail()
                )).toList();
        viewController.setTechnicianList(participantModels);
    }

    @Override
    public void displayHolderList(List<User> holderList, String userRole) {
        List<ParticipantModel> participantModels = holderList.stream()
                .map(holder -> new ParticipantModel(
                        0,
                        holder.getUserId(),
                        holder.getUserName(),
                        holder.getUserPhoneNumber(),
                        holder.getUserEmail()
                )).toList();
        viewController.setHolderList(participantModels);
    }

    @Override
    public void displayError(WorkOrderEnum workOrderEnum) {
        switch (workOrderEnum) {
            case NO_PLANT_ELEMENT:
                viewController.displayPlantElementInfo(
                        PropertiesLoader.GetText("workOrder.workOrderTab.pElementNotFound"),
                        "",
                        ""
                );
                break;

                //TODO: Implement
            case NO_TECHNICIANS:
                viewController.displayHolderInfo(
                    PropertiesLoader.GetText("workOrder.assignment.holderNotFound"),
                    "",
                    ""
                );
                break;

            case NO_SUPERVISORS:
                viewController.displayTechnicianInfo(
                        PropertiesLoader.GetText("workOrder.assignment.technicianNotFound"),
                        "",
                        ""
                );
                break;
        }
    }
}
