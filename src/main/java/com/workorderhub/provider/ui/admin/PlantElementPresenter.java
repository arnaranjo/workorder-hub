package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.plantelement.*;

import java.util.List;

public class PlantElementPresenter implements PlantElementOutput {

    private PlantElementView view;

    public PlantElementPresenter(){}

    public void setView(PlantElementController view){
        this.view = view;
    }

    @Override
    public boolean requestConfirmation(PlantElementEnum plantElementEnum) {
        return false;
    }

    @Override
    public void displayConfirmation(ResponseNewPlantElement response, PlantElementEnum plantElementEnum) {

    }

    @Override
    public void displayError(PlantElementEnum plantElementEnum) {

    }

    @Override
    public void populatePlantElementTable(List<PlantElementRow> plantElementRowList) {
        view.setPlantElementTableItems(plantElementRowList);
    }
}
