package com.workorderhub.core.caseuse.plantelement;

import com.workorderhub.core.entity.PlantElement;
import com.workorderhub.core.gateway.PlantElementGateway;

import java.util.List;

public class PlantElementInteractor implements PlantElementInput{

    private PlantElementGateway plantElementGateway;
    private PlantElementOutput presenter;

    public PlantElementInteractor(
            PlantElementGateway plantElementGateway,
            PlantElementOutput presenter
    ){
        this.plantElementGateway = plantElementGateway;
        this.presenter = presenter;
    }

    @Override
    public void retrievePlantElements() {
        List<PlantElement> plantElementsList = plantElementGateway.GetPlantElementsList();

        List<PlantElementRow> plantElementRowList = plantElementsList.stream().map(row ->
                new PlantElementRow(
                        row.getElementId(),
                        row.getElementTag(),
                        row.getElementDescription(),
                        row.getElementLocation(),
                        row.getInspectionDate(),
                        row.getInspectionFrequency()
                )
        ).toList();

        presenter.populatePlantElementTable(plantElementRowList);
    }

    @Override
    public int createPlantElement(RequestNewPlantElement request) {
        return 0;
    }

    @Override
    public boolean updatePlantElement(RequestUpdatePlantElement request) {
        return false;
    }

    @Override
    public boolean deletePlantElement(RequestDeletePlantElement request) {
        return false;
    }
}
