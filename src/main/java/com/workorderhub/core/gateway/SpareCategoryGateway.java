package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.SpareCategory;

import java.util.List;

public interface SpareCategoryGateway {

    /**
     * Retrieves a list of all spare categories available in the system.
     * @return List of SpareCategory objects representing the spare categories.
     */
    List<SpareCategory> GetAllCategories();
}
