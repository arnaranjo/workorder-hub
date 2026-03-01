package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.PlantElement;

import java.time.LocalDate;
import java.util.List;

public interface PlantElementGateway {
    /**
     * Gets all the plant elements.
     *
     * @return List of plant elements.
     */
    List<PlantElement> GetPlantElementsList();

    /**
     * Gets a specific plant element by tag.
     *
     * @param tag Plant element tag name.
     * @return Plant element object found or null if it does not exist.
     */
    PlantElement GetPlantElementByTag(String tag);

    /**
     * Insets a new plant element
     *
     * @param plantElement New plant element. The inspection Date can be null and the inspection frequency can be 0.
     * @return Generated ID when the plant element is inserted or 0 if not.
     */
    int InsertPlantElement(PlantElement plantElement);

    /**
     * Deletes a plant element.
     *
     * @param elementId Plant element ID.
     * @return True if the delection is successful or false if not.
     */
    boolean DeletePlantElement(int elementId);

    /**
     * Updates the plant element tag.
     *
     * @param elementId Plant element ID.
     * @param tag       New tag name.
     * @return True if the update is successful or false if not.
     */
    boolean UpdateElementTag(int elementId, String tag);

    /**
     * Updates the plant element description.
     *
     * @param elementId   Plant element ID.
     * @param description New description.
     * @return True if the update is successful or false if not.
     */
    boolean UpdateElementDescription(int elementId, String description);

    /**
     * Updates the plant element location.
     *
     * @param elementId Plant element ID.
     * @param location  New location.
     * @return True if the update is successful or false if not.
     */
    boolean UpdateElementLocation(int elementId, String location);

    /**
     * Updates the plant element inspection date.
     *
     * @param elementId      Plant element ID.
     * @param inspectionDate New inspection date.
     * @return True if the update is successful or false if not.
     */
    boolean UpdateElementInspectionDate(int elementId, LocalDate inspectionDate);

    /**
     * Updates the plant element inspection frequency.
     *
     * @param elementId           Plant element ID.
     * @param inspectionFrequency New inspection frequency.
     * @return True if the update is successful or false if not.
     */
    boolean UpdateElementInspectionFrequency(int elementId, int inspectionFrequency);
}
