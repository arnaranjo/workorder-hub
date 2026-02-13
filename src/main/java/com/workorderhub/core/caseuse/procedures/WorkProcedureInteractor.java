package com.workorderhub.core.caseuse.procedures;

import com.workorderhub.core.entity.WorkProcedure;
import com.workorderhub.core.gateway.WorkProcedureGateway;

import java.util.List;

public class WorkProcedureInteractor implements WorkProcedureInput {

    ProcedureOutput presenter;
    WorkProcedureGateway workProcedureGateway;

    public WorkProcedureInteractor(ProcedureOutput presenter, WorkProcedureGateway workProcedureGateway) {
        this.presenter = presenter;
        this.workProcedureGateway = workProcedureGateway;
    }

    @Override
    public void retrieveWorkProcedureList() {
        List<WorkProcedure> workProcedureList = workProcedureGateway.getWorkProcedureList();

        List<RowWorkProcedure> workProcedureRecordList = workProcedureList.stream().map(row ->
                new RowWorkProcedure(row.getProcedureId(), row.getDocumentCode(), row.getDocumentName())
        ).toList();

        presenter.populateWorkProcedureTable(workProcedureRecordList);
    }

    @Override
    public int CreateWorkProcedure(RequestNewWorkProcedure request) {
        if (request.documentName().isEmpty() || request.documentCode().isEmpty()) {
            presenter.displayWorkProcedureError(WorkProcedureEnum.INCOMPLETE_INFORMATION);

        } else if (isProcedureCodeInUse(request.documentCode())) {
            presenter.displayWorkProcedureError(WorkProcedureEnum.DOCUMENT_CODE_IN_USE);

        } else {
            WorkProcedure workProcedure = new WorkProcedure(
                    0,
                    request.documentCode(),
                    request.documentName()
            );
            int documentId = workProcedureGateway.insertProcedure(workProcedure);
            if (documentId != 0) {
                ResponseWorkProcedure procedure = new ResponseWorkProcedure(
                        documentId,
                        request.documentCode(),
                        request.documentName()
                );
                presenter.displayWorkProcedureConfirmation(
                        procedure,
                        WorkProcedureEnum.PROCEDURE_ADDED
                );

                return documentId;

            } else {
                presenter.displayWorkProcedureError(WorkProcedureEnum.PROCEDURE_ADDITION_ERROR);

            }
        }

        return 0;
    }

    @Override
    public boolean UpdateWorkProcedure(RequestUpdateWorkProcedure request) {
        if (request.documentCode().isEmpty() || request.documentName().isEmpty()) {
            presenter.displayWorkProcedureError(WorkProcedureEnum.INCOMPLETE_INFORMATION);

        } else if (isProcedureCodeInUse(request.documentCode())) {
            presenter.displayWorkProcedureError(WorkProcedureEnum.DOCUMENT_CODE_IN_USE);

        } else {
            WorkProcedure workProcedure = new WorkProcedure(
                    request.documentId(),
                    request.documentCode(),
                    request.documentName()
            );

            if (workProcedureGateway.updateProcedure(workProcedure)) {
                ResponseWorkProcedure procedure = new ResponseWorkProcedure(
                        request.documentId(),
                        request.documentCode(),
                        request.documentName()
                );
                presenter.displayWorkProcedureConfirmation(
                        procedure,
                        WorkProcedureEnum.PROCEDURE_UPDATED
                );
                return true;

            } else {
                presenter.displayWorkProcedureError(WorkProcedureEnum.PROCEDURE_UPDATE_ERROR);

            }
        }

        return false;
    }

    @Override
    public boolean DeleteWorkProcedure(RequestDeleteWorkProcedure request) {
        if (workProcedureGateway.deleteProcedure(request.documentId())) {
            ResponseWorkProcedure response = new ResponseWorkProcedure(
                    request.documentId(),
                    request.documentCode(),
                    request.documentName()
            );

            presenter.displayWorkProcedureConfirmation(
                    response,
                    WorkProcedureEnum.PROCEDURE_DELETED
            );
            return true;

        } else {
            presenter.displayWorkProcedureError(WorkProcedureEnum.PROCEDURE_DELETED_ERROR);

        }

        return false;
    }

    private boolean isProcedureCodeInUse(String documentCode) {
        List<WorkProcedure> workProcedureList = workProcedureGateway.getWorkProcedureList();

        for (WorkProcedure procedure : workProcedureList) {
            if (procedure.getDocumentCode().equals(documentCode)) {
                return true;

            }
        }

        return false;
    }
}
