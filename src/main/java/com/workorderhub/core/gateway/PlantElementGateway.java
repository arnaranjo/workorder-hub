package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.PlantElement;

import java.util.List;

public interface PlantElementGateway {
    List<PlantElement> GetPlantElementsList();
    PlantElement GetPlantElementByTag (String tag);
    int InsertPlantElement(PlantElement plantElement);
    boolean DeletePlantElement(int elementId);
    boolean UpdatePlantElement(PlantElement plantElement);
}
