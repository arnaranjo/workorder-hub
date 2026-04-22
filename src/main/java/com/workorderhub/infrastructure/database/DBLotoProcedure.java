package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.LotoProcedure;
import com.workorderhub.core.gateway.LotoProcedureGateway;
import com.workorderhub.infrastructure.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBLotoProcedure implements LotoProcedureGateway {

    private Connection sqlManager;

    @Override
    public List<LotoProcedure> getLotoProceduresList() {
        List<LotoProcedure> proceduresList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM loto_procedure
                ORDER BY loto_procedure_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                LotoProcedure lotoProcedure = new LotoProcedure(
                        resultSet.getInt("loto_procedure_id"),
                        resultSet.getString("loto_procedure_code"),
                        resultSet.getString("loto_procedure_name")
                );
                lotoProcedure.setProcedureId(resultSet.getInt("loto_procedure_id"));

                proceduresList.add(lotoProcedure);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return proceduresList;

    }

    @Override
    public int insertProcedure(LotoProcedure lotoProcedure) {
        int id = 0;
        String sql = """
                INSERT INTO loto_procedure (loto_procedure_id, loto_procedure_code, loto_procedure_name)
                VALUES (?, ?, ?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, lotoProcedure.getProcedureId());
            statement.setString(2, lotoProcedure.getDocumentCode());
            statement.setString(3, lotoProcedure.getDocumentName());

            statement.executeUpdate();
            ResultSet keyGen = statement.getGeneratedKeys();
            if (keyGen.next()) {
                id = keyGen.getInt(1);
            }
            statement.close();

            return id;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return id;
    }

    @Override
    public boolean deleteProcedure(int documentId) {
        String sql = """
                DELETE FROM loto_procedure
                WHERE loto_procedure_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, documentId);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            DBConnection.DBDisconnect();

        }
    }

    @Override
    public boolean updateProcedure(LotoProcedure lotoProcedure) {
        String sql = """
                UPDATE loto_procedure
                SET loto_procedure_code = ?, loto_procedure_name = ?
                WHERE loto_procedure_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, lotoProcedure.getDocumentCode());
            statement.setString(2, lotoProcedure.getDocumentName());
            statement.setInt(3, lotoProcedure.getProcedureId());

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            DBConnection.DBDisconnect();

        }
    }
}
