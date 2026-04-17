package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.UsedSparePart;

import java.util.List;

public interface UsedSparePartGateway {
    List<UsedSparePart> getUsedSpareByWorkOrder(long workOrderId);
    boolean insertUsedSparePart(UsedSparePart usedSparePart);
    boolean insertUsedSparePartBatch(List<UsedSparePart> usedSparePartList);
    boolean deleteUsedSparePart(UsedSparePart usedSparePart);
    boolean updateUsedSparePart(UsedSparePart usedSparePart);
}
