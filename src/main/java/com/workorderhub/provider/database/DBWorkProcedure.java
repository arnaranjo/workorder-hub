package com.workorderhub.provider.database;

import com.workorderhub.core.entity.WorkProcedure;
import com.workorderhub.core.gateway.WorkProcedureGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBWorkProcedure implements WorkProcedureGateway {

    private Connection sqlManager;

    @Override
    public List<WorkProcedure> getWorkProcedureList() {
        List<WorkProcedure> proceduresList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM work_procedure
                ORDER BY work_procedure_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                WorkProcedure workProcedure = new WorkProcedure(
                        resultSet.getInt("work_procedure_id"),
                        resultSet.getString("work_procedure_code"),
                        resultSet.getString("work_procedure_name")
                );
                workProcedure.setProcedureId(resultSet.getInt("work_procedure_id"));

                proceduresList.add(workProcedure);
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
    public int insertProcedure(WorkProcedure workProcedure) {
        int id = 0;
        String sql = """
                INSERT INTO work_procedure (work_procedure_code, work_procedure_name)
                VALUES (?, ?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, workProcedure.getDocumentCode());
            statement.setString(2, workProcedure.getDocumentName());

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
                DELETE FROM work_procedure
                WHERE work_procedure_id = ?;
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
    public boolean updateProcedure(WorkProcedure workProcedure) {

        String sql = """
                UPDATE work_procedure
                SET work_procedure_code = ?, work_procedure_name = ?
                WHERE work_procedure_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, workProcedure.getDocumentCode());
            statement.setString(2, workProcedure.getDocumentName());
            statement.setInt(3, workProcedure.getProcedureId());

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
