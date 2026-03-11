package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.SpareCategory;

import java.util.List;

public interface SpareCategoryGateway {
    List<SpareCategory> GetAllCategories();
}
