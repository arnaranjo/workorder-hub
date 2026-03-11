package com.workorderhub.core.gateway;

import com.workorderhub.core.entity.SparePart;

import java.util.List;

public interface SparePartGateway {

    /**
     * Retrieves the complete list of spare parts.
     * @return list of spare parts.
     */
    List<SparePart> getSparePartList();

    /**
     * Retrieves the identifier of a spare part from its data.
     *
     * @param sparePart spare part entity.
     * @return spare part identifier.
     */
    int getSparePartId(SparePart sparePart);

    /**
     * Inserts a new spare part.
     *
     * @param sparePart spare part entity to insert.
     * @return generated identifier of the inserted spare part.
     */
    int insertSparePart(SparePart sparePart);

    /**
     * Deletes a spare part by its identifier.
     *
     * @param sparePartId spare part identifier.
     * @return true if the deletion was successful; otherwise, false.
     */
    boolean deleteSparePart(int sparePartId);

    /**
     * Updates the name of a spare part.
     *
     * @param sparePartId   spare part identifier.
     * @param sparePartName new spare part name.
     * @return true if the update was successful; otherwise, false.
     */
    boolean updateSparePartName(int sparePartId, String sparePartName);

    /**
     * Updates the part number of a spare part.
     *
     * @param sparePartId     spare part identifier.
     * @param sparePartNumber new part number.
     * @return true if the update was successful; otherwise, false.
     */
    boolean updateSparePartNumber(int sparePartId, String sparePartNumber);

    /**
     * Updates the description of a spare part.
     *
     * @param sparePartId          spare part identifier.
     * @param sparePartDescription new description.
     * @return true if the update was successful; otherwise, false.
     */
    boolean updateSparePartDescription(int sparePartId, String sparePartDescription);

    /**
     * Updates the stock of a spare part.
     *
     * @param sparePartId    spare part identifier.
     * @param sparePartStock new stock value.
     * @return true if the update was successful; otherwise, false.
     */
    boolean updateSparePartStock(int sparePartId, int sparePartStock);

    /**
     * Updates the category of a spare part.
     *
     * @param sparePartId       spare part identifier.
     * @param sparePartCategory identifier of the new category.
     * @return true if the update was successful; otherwise, false.
     */
    boolean updateSparePartCategory(int sparePartId, int sparePartCategory);
}
