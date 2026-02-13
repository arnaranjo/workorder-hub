package com.workorderhub.core.caseuse.procedures;

import com.workorderhub.core.entity.LotoProcedure;
import com.workorderhub.core.gateway.LotoProcedureGateway;

import java.util.List;

public class LotoProcedureInteractor implements LotoProcedureInput {

    ProcedureOutput presenter;
    LotoProcedureGateway lotoProcedureGateway;

    public LotoProcedureInteractor(ProcedureOutput presenter, LotoProcedureGateway lotoProcedureGateway) {
        this.presenter = presenter;
        this.lotoProcedureGateway = lotoProcedureGateway;
    }

    @Override
    public void retrieveLotoProcedureList() {
        List<LotoProcedure> lotoProcedureList = lotoProcedureGateway.getLotoProceduresList();

        List<RowLotoProcedure> lotoProcedureRecordList = lotoProcedureList.stream().map(row ->
                new RowLotoProcedure(row.getProcedureId(), row.getDocumentCode(), row.getDocumentName())
        ).toList();

        presenter.populateLoroProcedureTable(lotoProcedureRecordList);

    }

    @Override
    public int CreateLotoProcedure(RequestNewLotoProcedure request) {
        if (request.documentName().isEmpty() || request.documentCode().isEmpty()) {
            presenter.displayLotoProcedureError(LotoProcedureEnum.INCOMPLETE_INFORMATION);

        } else if (isProcedureCodeInUse(request.documentCode())) {
            presenter.displayLotoProcedureError(LotoProcedureEnum.DOCUMENT_CODE_IN_USE);

        } else {
            LotoProcedure lotoProcedure = new LotoProcedure(
                    0,
                    request.documentCode(),
                    request.documentName()
            );
            int documentId = lotoProcedureGateway.insertProcedure(lotoProcedure);
            if (documentId != 0) {
                ResponseLotoProcedure procedure = new ResponseLotoProcedure(
                        documentId,
                        request.documentCode(),
                        request.documentName()
                );
                presenter.displayLotoProcedureConfirmation(
                        procedure,
                        LotoProcedureEnum.PROCEDURE_ADDED
                );

                return documentId;

            } else {
                presenter.displayLotoProcedureError(LotoProcedureEnum.PROCEDURE_ADDITION_ERROR);

            }
        }

        return 0;
    }

    @Override
    public boolean UpdateLotoProcedure(RequestUpdateLotoProcedure request) {
        if (request.documentCode().isEmpty() || request.documentName().isEmpty()) {
            presenter.displayLotoProcedureError(LotoProcedureEnum.INCOMPLETE_INFORMATION);

        } else if (isProcedureCodeInUse(request.documentCode())) {
            presenter.displayLotoProcedureError(LotoProcedureEnum.DOCUMENT_CODE_IN_USE);

        } else {
            LotoProcedure lotoProcedure = new LotoProcedure(
                    request.documentId(),
                    request.documentCode(),
                    request.documentName()
            );

            if (lotoProcedureGateway.updateProcedure(lotoProcedure)) {
                ResponseLotoProcedure procedure = new ResponseLotoProcedure(
                        request.documentId(),
                        request.documentCode(),
                        request.documentName()
                );
                presenter.displayLotoProcedureConfirmation(
                        procedure,
                        LotoProcedureEnum.PROCEDURE_UPDATED
                );
                return true;

            } else {
                presenter.displayLotoProcedureError(LotoProcedureEnum.PROCEDURE_UPDATE_ERROR);

            }
        }

        return false;
    }

    @Override
    public boolean DeleteLotoProcedure(RequestDeleteLotoProcedure request) {
        if (lotoProcedureGateway.deleteProcedure(request.documentId())) {
            ResponseLotoProcedure response = new ResponseLotoProcedure(
                    request.documentId(),
                    request.documentCode(),
                    request.documentName()
            );

            presenter.displayLotoProcedureConfirmation(
                    response,
                    LotoProcedureEnum.PROCEDURE_DELETED
            );
            return true;

        } else {
            presenter.displayLotoProcedureError(LotoProcedureEnum.PROCEDURE_DELETED_ERROR);

        }

        return false;
    }

    private boolean isProcedureCodeInUse(String documentCode) {
        List<LotoProcedure> workProcedureList = lotoProcedureGateway.getLotoProceduresList();

        for (LotoProcedure procedure : workProcedureList) {
            if (procedure.getDocumentCode().equals(documentCode)) {
                return true;

            }
        }

        return false;
    }
}
