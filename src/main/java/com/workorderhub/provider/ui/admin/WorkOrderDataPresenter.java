package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.spareparts.SparePartRow;
import com.workorderhub.core.caseuse.workorder.ResponsePlantElement;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataOutput;
import com.workorderhub.core.caseuse.workorder.WorkOrderDataView;
import com.workorderhub.core.caseuse.workorder.WorkOrderEnum;
import com.workorderhub.core.entity.Category;
import com.workorderhub.core.entity.User;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;
import com.workorderhub.provider.models.CategoryModel;
import com.workorderhub.provider.models.ParticipantModel;
import com.workorderhub.provider.models.SparePartModel;

import java.util.List;
import java.util.Objects;

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
        viewController.confirmPlantElement(response.elementId());
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
    public void displaySparePartsList(List<SparePartRow> sparePartRowList) {
        List<SparePartModel> sparePartModels = sparePartRowList.stream()
                .map(sparePartRow -> new SparePartModel(
                        sparePartRow.spareId(),
                        sparePartRow.spareName(),
                        sparePartRow.spareNumber(),
                        sparePartRow.spareDescription(),
                        sparePartRow.spareStock(),
                        sparePartRow.spareCategory()
                )).toList();
        viewController.setSparePartTableItems(sparePartModels);
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

            case WORK_ORDER_DESCRIPTION_ERROR:
                String titleNoDescription = "workOrder.newWorkOrder.errorTitle";
                String messageNoDescription = "workOrder.newWorkOrder.descriptionError";
                Util.ShowMessage(titleNoDescription, messageNoDescription);
                break;

            case WORK_ORDER_HOLDER_ERROR:
                String titleNoHolder = "workOrder.newWorkOrder.errorTitle";
                String messageNoHolder = "workOrder.newWorkOrder.holderError";
                Util.ShowMessage(titleNoHolder, messageNoHolder);
                break;

            case WORK_ORDER_PLANT_ELEMENT_ERROR:
                String titleNoPlantElement = "workOrder.newWorkOrder.errorTitle";
                String messageNoPlantElement = "workOrder.newWorkOrder.plantElementError";
                Util.ShowMessage(titleNoPlantElement, messageNoPlantElement);
                break;

            case WORK_ORDER_CATEGORY_ERROR:
                String titleNoCategory = "workOrder.newWorkOrder.errorTitle";
                String messageNoCategory = "workOrder.newWorkOrder.categoryError";
                Util.ShowMessage(titleNoCategory, messageNoCategory);
                break;

            case WORK_ORDER_CREATION_ERROR:
                String titleCreationError = "workOrder.newWorkOrder.errorTitle";
                String messageCreationError = "workOrder.newWorkOrder.newWOrderError";
                Util.ShowMessage(titleCreationError, messageCreationError);
                break;

            case WORK_PERMIT_DESCRIPTION_ERROR:
                String titleNoWorkPermitDescription = "workOrder.newWorkOrder.errorTitle";
                String messageNoWorkPermitDescription = "workOrder.newWorkPermit.newWPermitError";
                Util.ShowMessage(titleNoWorkPermitDescription, messageNoWorkPermitDescription);
                break;
        }
    }

    @Override
    public boolean requestConfirmation(WorkOrderEnum workOrderEnum) {
        if (Objects.requireNonNull(workOrderEnum) == WorkOrderEnum.REQUEST_WORK_ORDER_CREATION) {
            String title = "workOrder.newWorkOrder.confirmationTitle";
            String message = "workOrder.newWorkOrder.confirmationMessage";

            return Util.RequestConfirmation(title, message);
        }
        return false;
    }

    @Override
    public void displaySuccess(WorkOrderEnum workOrderEnum) {
        if (Objects.requireNonNull(workOrderEnum) == WorkOrderEnum.WORK_ORDER_CREATED) {
            String title = "workOrder.newWorkOrder.newWOrderTitle";
            String message = "workOrder.newWorkOrder.newWOrderMessage";

            Util.ShowMessage(title, message);
        }
    }
}
