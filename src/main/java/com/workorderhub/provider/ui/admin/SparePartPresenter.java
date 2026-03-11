package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.spareparts.SparePartOutput;
import com.workorderhub.core.caseuse.spareparts.RowSparePart;
import com.workorderhub.core.caseuse.spareparts.SparePartsView;

import java.util.List;

public class SparePartPresenter implements SparePartOutput {

    private SparePartsView view;

    public SparePartPresenter() {
    }

    public void setView (SparePartsView view){
        this.view = view;
    }

    @Override
    public void populatesSparePartsTable(List<RowSparePart> rowSparePartList) {
        view.setSparePartTableItems(rowSparePartList);
    }
}
