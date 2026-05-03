package com.workorderhub.infrastructure.ui.technician;

import com.workorderhub.core.caseuse.technician.*;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.UsedSparePartModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WorkOrderCheckPresenter implements WorkOrderCheckOutput {

    private WorkOrderCheckView viewController;

    public WorkOrderCheckPresenter() {}

    public void setViewController(WorkOrderCheckView viewController) {
        this.viewController = viewController;
    }

    @Override
    public void displayWorkOrderData(ResponseWorkOrderData responseWorkOrderData) {
        viewController.setPeriodDate(
                responseWorkOrderData.startDate() == null ? "N/A" : responseWorkOrderData.startDate().toString(),
                responseWorkOrderData.endDate() == null ? "N/A" : responseWorkOrderData.endDate().toString()
        );
        viewController.setWorkOrderData(
                responseWorkOrderData.workOrderDescripcion(),
                responseWorkOrderData.workOrderStatus(),
                responseWorkOrderData.workOrderCategoryList()
        );
    }

    @Override
    public void displayHolderInfo(ResponseHolderInfo responseHolderInfo) {
        viewController.setHolderInfo(
                responseHolderInfo.userName(),
                responseHolderInfo.userEmail() + " / " + responseHolderInfo.userPhoneNumber()
        );
    }

    @Override
    public void displayPlantElement(ResponsePlantElement responsePlantElement) {
        viewController.setPlantElement(
                responsePlantElement.elementTag(),
                responsePlantElement.elementLocation(),
                responsePlantElement.elementDescription()
        );
    }

    @Override
    public void displayProcedureInfo(ResponseProcedureInfo responseProcedureInfo) {
        viewController.setWorkProcedureInfo(
                responseProcedureInfo.documentName(),
                responseProcedureInfo.documentCode()
        );
    }

    @Override
    public void displayWorkPermit(ResponseWorkPermit responseWorkPermit) {
        viewController.setWorkPermitInfo(
                responseWorkPermit.workPermitDescription() == null ? "N/A" : responseWorkPermit.workPermitDescription(),
                responseWorkPermit.lockDeviceId() == null ? "N/A" : responseWorkPermit.lockDeviceId(),
                responseWorkPermit.LotoProcedureName() == null ? "N/A" : responseWorkPermit.LotoProcedureName(),
                responseWorkPermit.LotoProcedureCode() == null ? "N/A" : responseWorkPermit.LotoProcedureCode()
        );
    }

    @Override
    public void displayParticipantList(List<ResponseParticipant> participantList) {
        List<String> nameList = participantList.stream()
                .map(participant -> {
                    return participant.userName()
                            + " (" + participant.userPhone() + " / " + participant.userEmail() + ")";
                })
                .toList();
        viewController.setParticipants(nameList);
    }

    @Override
    public void displayUsedSparePartList(List<ResponseUsedSparePart> usedSparePartList) {
        List<UsedSparePartModel> sparePartModelList = usedSparePartList.stream()
                .map(sparePart -> new UsedSparePartModel(
                        sparePart.workOrderId(),
                        sparePart.sparePartId(),
                        sparePart.selectedNumber(),
                        sparePart.currentStock(),
                        sparePart.spareName(),
                        sparePart.spareNumber()
                ))
                .collect(Collectors.toList());
        viewController.setSparePartTableItems(sparePartModelList);
    }

    @Override
    public void displaySuccess(WorkOrderCheckEnum workOrderCheckEnum) {
        switch (workOrderCheckEnum) {
            case ADDED_COMMENT_SUCCESS:
                String requestStartTitle = "technicianView.addCommentTitle";
                String requestStartMessage = "technicianView.commentAdded";

                Util.ShowMessage(requestStartTitle, requestStartMessage);
                break;

            case UPDATED_SPARE_PARTS_SUCCESS:
                break;
        }
    }

    @Override
    public boolean requestConfirmation(WorkOrderCheckEnum workOrderCheckEnum) {
        switch (workOrderCheckEnum) {
            case REQUEST_ADD_COMMENT:
                String requestStartTitle = "technicianView.addCommentTitle";
                String requestStartMessage = "technicianView.addedComment";

                return Util.RequestConfirmation(requestStartTitle, requestStartMessage);

            case REQUEST_UPDATE_SPARE_PARTS:
                return false;
        }
        return false;
    }
}
