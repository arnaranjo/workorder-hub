package com.workorderhub.core.caseuse.plantelement;

import com.workorderhub.core.entity.PlantElement;
import com.workorderhub.core.gateway.PlantElementGateway;

import java.util.List;

public class PlantElementInteractor implements PlantElementInput {

    private PlantElementGateway plantElementGateway;
    private PlantElementOutput presenter;

    public PlantElementInteractor(
            PlantElementGateway plantElementGateway,
            PlantElementOutput presenter
    ) {
        this.plantElementGateway = plantElementGateway;
        this.presenter = presenter;
    }

    @Override
    public void retrievePlantElements() {
        List<PlantElement> plantElementsList = plantElementGateway.GetPlantElementsList();

        List<RowPlantElement> rowPlantElementList = plantElementsList.stream().map(row ->
                new RowPlantElement(
                        row.getElementId(),
                        row.getElementTag(),
                        row.getElementDescription(),
                        row.getElementLocation(),
                        row.getInspectionDate(),
                        row.getInspectionFrequency()
                )
        ).toList();

        presenter.populatePlantElementTable(rowPlantElementList);
    }

    @Override
    public void createPlantElement(RequestNewPlantElement request) {
        if (
                request.elementTag().isEmpty() ||
                        request.elementDescription().isEmpty() ||
                        request.elementLocation().isEmpty()
        )
            presenter.displayError(PlantElementEnum.INCOMPLETE_INFORMATION);

        else if (isElementTagInUse(request.elementTag(), 0)) {
            presenter.displayError(PlantElementEnum.PLANT_ELEMENT_TAG_IN_USE);

        } else {
            PlantElement newPlantElement = new PlantElement.Builder()
                    .withElementId(0)
                    .withElementTag(request.elementTag())
                    .withElementDescription(request.elementDescription())
                    .withElementLocation(request.elementLocation())
                    .withInspectionDate(request.inspectionDate())
                    .withInspectionFrequency(request.inspectionFrequency())
                    .build();

            int elementId = plantElementGateway.InsertPlantElement(newPlantElement);

            if (elementId != 0) {
                RowPlantElement newRow = new RowPlantElement(
                        elementId,
                        request.elementTag(),
                        request.elementDescription(),
                        request.elementLocation(),
                        request.inspectionDate(),
                        request.inspectionFrequency()
                );

                ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(
                        request.elementTag()
                );
                presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_ADDED);
                presenter.addNewTableRow(newRow);
            } else {
                presenter.displayError(PlantElementEnum.PlANT_ELEMENT_ADDITION_ERROR);

            }
        }
    }

    @Override
    public void updateElementTag(RequestUpdateElementTag request) {
        ResponseUpdateElementTag responseCell = new ResponseUpdateElementTag(
                request.oldElementTag(),
                request.row()
        );

        if (request.newElementTag().isEmpty()) {
            presenter.displayError(PlantElementEnum.INCOMPLETE_INFORMATION);

            //Recover the old value in the table cell.
            presenter.updateElementTagCell(responseCell);

        } else if (isElementTagInUse(request.newElementTag(), request.elementId())) {
            presenter.displayError(PlantElementEnum.PLANT_ELEMENT_TAG_IN_USE);
            presenter.updateElementTagCell(responseCell);

        } else if (plantElementGateway.UpdateElementTag(request.elementId(), request.newElementTag())) {
            //Displays the confirmation
            ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(request.newElementTag());
            presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_UPDATED);

            //Update table with the new value.
            responseCell = new ResponseUpdateElementTag(
                    request.newElementTag(),
                    request.row()
            );
            presenter.updateElementTagCell(responseCell);

        } else {
            presenter.displayError(PlantElementEnum.PlANT_ELEMENT_UPDATE_ERROR);
            presenter.updateElementTagCell(responseCell);
        }
    }

    @Override
    public void updateElementDescription(RequestUpdateElementDescription request) {
        ResponseUpdateElementDescription responseCell = new ResponseUpdateElementDescription(
                request.oldElementDescription(),
                request.row()
        );

        if (request.newElementDescription().isEmpty()) {
            presenter.displayError(PlantElementEnum.INCOMPLETE_INFORMATION);
            presenter.updateElementDescriptionCell(responseCell);

        } else if (plantElementGateway.UpdateElementDescription(request.elementId(), request.newElementDescription())) {

            ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(request.elementTag());
            presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_UPDATED);

            responseCell = new ResponseUpdateElementDescription(
                    request.newElementDescription(),
                    request.row()
            );
            presenter.updateElementDescriptionCell(responseCell);

        } else {
            presenter.displayError(PlantElementEnum.PlANT_ELEMENT_UPDATE_ERROR);
            presenter.updateElementDescriptionCell(responseCell);
        }
    }

    @Override
    public void updateElementLocation(RequestUpdateElementLocation request) {
        ResponseUpdateElementLocation responseCell = new ResponseUpdateElementLocation(
                request.oldElementLocation(),
                request.row()
        );

        if (request.newElementLocation().isEmpty()) {
            presenter.displayError(PlantElementEnum.INCOMPLETE_INFORMATION);
            presenter.updateElementLocationCell(responseCell);

        } else if (plantElementGateway.UpdateElementLocation(request.elementId(), request.newElementLocation())) {
            ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(request.elementTag());
            presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_UPDATED);

            responseCell = new ResponseUpdateElementLocation(
                    request.newElementLocation(),
                    request.row()
            );
            presenter.updateElementLocationCell(responseCell);

        } else {
            presenter.displayError(PlantElementEnum.PlANT_ELEMENT_UPDATE_ERROR);
            presenter.updateElementLocationCell(responseCell);
        }
    }

    @Override
    public void updateElementInspectionDate(RequestUpdateElementInspectionDate request) {
        ResponseUpdateElementInspectionDate responseCell = new ResponseUpdateElementInspectionDate(
                request.oldInspectionDate(),
                request.row()
        );

        //Inspection Date can be null.
        if (plantElementGateway.UpdateElementInspectionDate(request.elementId(), request.newInspectionDate())) {
            ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(request.elementTag());
            presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_UPDATED);

            responseCell = new ResponseUpdateElementInspectionDate(
                    request.newInspectionDate(),
                    request.row()
            );
            presenter.updateElementInspectionDateCell(responseCell);

        } else {
            presenter.displayError(PlantElementEnum.PlANT_ELEMENT_UPDATE_ERROR);
            presenter.updateElementInspectionDateCell(responseCell);

        }
    }

    @Override
    public void updateElementInspectionFrequency(RequestUpdateElementInspectionFrequency request) {
        ResponseUpdateElementInspectionFrequency responseCell = new ResponseUpdateElementInspectionFrequency(
                request.oldInspectionFrequency(),
                request.row()
        );

        //Inspection frequency can be 0.
        if (plantElementGateway.UpdateElementInspectionFrequency(
                request.elementId(),
                request.newInspectionFrequency())) {
            ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(request.elementTag());
            presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_UPDATED);

            responseCell = new ResponseUpdateElementInspectionFrequency(
                    request.newInspectionFrequency(),
                    request.row()
            );
            presenter.updateElementInspectionFrequencyCell(responseCell);


        } else {
            presenter.displayError(PlantElementEnum.PlANT_ELEMENT_UPDATE_ERROR);
            presenter.updateElementInspectionFrequencyCell(responseCell);

        }
    }

    @Override
    public void deletePlantElement(RequestDeletePlantElement request) {
        if (request.elementId() == 0) {
            presenter.displayError(PlantElementEnum.PlANT_ELEMENT_DELETION_ERROR);

        } else if (presenter.requestConfirmation(PlantElementEnum.CONFIRM_DELETE_PLANT_ELEMENTS)) {
            if (plantElementGateway.DeletePlantElement(request.elementId())) {
                ResponsePlantElementConfirmation confirmation = new ResponsePlantElementConfirmation(
                        request.elementTag()
                );
                presenter.displayConfirmation(confirmation, PlantElementEnum.PLANT_ELEMENT_DELETED);

                ResponseDeletePlantElement response = new ResponseDeletePlantElement(
                        request.plantElementIndex()
                );
                presenter.removePlantElementItem(response);

            } else {
                presenter.displayError(PlantElementEnum.PlANT_ELEMENT_DELETION_ERROR);

            }
        }
    }

    //Auxiliary methods

    /**
     * Checks if the element tag is already in use by another plant element.
     * @param elementTag
     * @param currentElementId
     * @return
     */
    private boolean isElementTagInUse(String elementTag, int currentElementId) {
        PlantElement plantElement = plantElementGateway.GetPlantElementByTag(elementTag);

        return plantElement != null
                && plantElement.getElementId() != currentElementId;
    }
}
