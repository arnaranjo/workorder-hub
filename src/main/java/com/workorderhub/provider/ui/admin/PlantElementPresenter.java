package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.plantelement.*;
import com.workorderhub.provider.common.PropertiesLoader;
import com.workorderhub.provider.common.Util;

import java.util.List;

public class PlantElementPresenter implements PlantElementOutput {

    private PlantElementView view;

    public PlantElementPresenter(){
    }

    public void setView(PlantElementController view){
        this.view = view;
    }

    @Override
    public boolean requestConfirmation(PlantElementEnum plantElementEnum) {
        String title = "plantElement.deletedElementConfirmation.title";
        String message = "plantElement.deletedElementConfirmation.content";

        if (plantElementEnum != PlantElementEnum.CONFIRM_DELETE_PLANT_ELEMENTS) {
            return false;
        }
        else {
            return Util.RequestConfirmation(title, message);
        }
    }

    @Override
    public void displayConfirmation(ResponsePlantElementConfirmation response, PlantElementEnum plantElementEnum) {
        switch (plantElementEnum){
            case PLANT_ELEMENT_ADDED:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.addedElement")
                        + " (" + response.requestedElementTag() + ")"
                );
                break;

            case PLANT_ELEMENT_UPDATED:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.updatedElement")
                        + " (" + response.requestedElementTag() + ")"
                );
                break;

            case PLANT_ELEMENT_DELETED:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.deletedElement")
                        + " (" + response.requestedElementTag() + ")"
                );
                break;
        }
    }

    @Override
    public void updateElementTagCell(ResponseUpdateElementTag response) {
        view.setTableItemTag(response.elementTag(), response.row());
    }

    @Override
    public void updateElementDescriptionCell(ResponseUpdateElementDescription response) {
        view.setTableItemDescription(response.elementDescription(), response.row());
    }

    @Override
    public void updateElementLocationCell(ResponseUpdateElementLocation response) {
        view.setTableItemLocation(response.elementLocation(), response.row());
    }

    @Override
    public void updateElementInspectionDateCell(ResponseUpdateElementInspectionDate response) {
        view.setTableItemInspectionDate(response.inspectionDate(), response.row());
    }

    @Override
    public void updateElementInspectionFrequencyCell(ResponseUpdateElementInspectionFrequency response) {
        view.setTableItemInspectionFrequency(response.inspectionFrequency(), response.row());
    }

    @Override
    public void addNewTableRow(PlantElementRow plantElementRow) {
        view.addPlantElementItem(plantElementRow);
    }

    @Override
    public void removePlantElementItem(ResponseDeletePlantElement response) {
        view.removePlantElementItem(response.plantElementIndex());
    }

    @Override
    public void displayError(PlantElementEnum plantElementEnum) {
        switch (plantElementEnum){
            case INCOMPLETE_INFORMATION:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.incompleteInfo"));
                break;

            case PlANT_ELEMENT_ADDITION_ERROR:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.addedElementError"));
                break;

            case PlANT_ELEMENT_UPDATE_ERROR:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.updatedElementError"));
                break;

            case PlANT_ELEMENT_DELETION_ERROR:
                view.setInfoDisplay(PropertiesLoader.GetText("plantElement.deletedElementError"));
                break;
        }
    }

    @Override
    public void populatePlantElementTable(List<PlantElementRow> plantElementRowList) {
        view.setPlantElementTableItems(plantElementRowList);
    }
}
