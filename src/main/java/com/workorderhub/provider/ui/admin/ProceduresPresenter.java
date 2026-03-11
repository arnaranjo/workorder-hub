package com.workorderhub.provider.ui.admin;

import com.workorderhub.core.caseuse.procedures.*;
import com.workorderhub.provider.common.PropertiesLoader;

import java.util.List;

public class ProceduresPresenter implements ProcedureOutput {

    private ProcedureView view;

    public ProceduresPresenter() {
    }

    public void setView(ProcedureView view) {
        this.view = view;
    }

    @Override
    public void displayWorkProcedureConfirmation(ResponseWorkProcedure response, WorkProcedureEnum workProcedureEnum) {
        switch (workProcedureEnum) {
            case PROCEDURE_ADDED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.addedElement") + " " +
                                response.documentName() + " (" + response.documentCode() + ")."
                );
                break;

            case PROCEDURE_UPDATED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.updatedElement") + " " +
                                response.documentName() + " (" + response.documentCode() + ")."
                );
                break;

            case PROCEDURE_DELETED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.deletedElement") + " " +
                                response.documentName() + " (" + response.documentCode() + ")."
                );
                break;
        }
    }

    @Override
    public void displayWorkProcedureError(WorkProcedureEnum workProcedureEnum) {
        switch (workProcedureEnum) {
            case PROCEDURE_ADDITION_ERROR:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.addedElementError")
                );
                break;

            case PROCEDURE_UPDATE_ERROR:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.updatedElementError")
                );
                break;

            case PROCEDURE_DELETED_ERROR:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.deletedElementError")
                );
                break;

            case INCOMPLETE_INFORMATION:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.wrongProcedureInfo")
                );
                break;

            case DOCUMENT_CODE_IN_USE:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("workProcedure.wrongProcedureCode")
                );
                break;
        }
    }

    @Override
    public void displayLotoProcedureConfirmation(ResponseLotoProcedure response, LotoProcedureEnum lotoProcedureEnum) {
        switch (lotoProcedureEnum) {
            case PROCEDURE_ADDED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.addedElement") + " " +
                                response.documentName() + " (" + response.documentCode() + ")."
                );
                break;

            case PROCEDURE_UPDATED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.updatedElement") + " " +
                                response.documentName() + " (" + response.documentCode() + ")."
                );
                break;

            case PROCEDURE_DELETED:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.deletedElement") + " " +
                                response.documentName() + " (" + response.documentCode() + ")."
                );
                break;
        }
    }

    @Override
    public void displayLotoProcedureError(LotoProcedureEnum lotoProcedureEnum) {
        switch (lotoProcedureEnum) {
            case PROCEDURE_ADDITION_ERROR:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.addedElementError")
                );
                break;

            case PROCEDURE_UPDATE_ERROR:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.updatedElementError")
                );
                break;

            case PROCEDURE_DELETED_ERROR:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.deletedElementError")
                );
                break;

            case INCOMPLETE_INFORMATION:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.wrongProcedureInfo")
                );
                break;

            case DOCUMENT_CODE_IN_USE:
                view.setInfoDisplay(
                        PropertiesLoader.GetText("lotoProcedure.wrongProcedureCode")
                );
                break;
        }
    }

    @Override
    public void populateWorkProcedureTable(List<RowWorkProcedure> workProcedureList) {
        view.setWorkProcedureTableTitles(
                PropertiesLoader.GetText("workProcedure.descriptionCode"),
                PropertiesLoader.GetText("workProcedure.descriptionName")
        );

        view.setWorkProcedureTableItems(workProcedureList);
    }

    @Override
    public void populateLoroProcedureTable(List<RowLotoProcedure> lotoProcedureList) {
        view.setLotoProcedureTableTitles(
                PropertiesLoader.GetText("lotoProcedure.descriptionCode"),
                PropertiesLoader.GetText("lotoProcedure.descriptionName")
        );

        view.setLotoProcedureTableItems(lotoProcedureList);
    }
}
