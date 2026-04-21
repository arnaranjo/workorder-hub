package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.WorkPermit;
import com.workorderhub.core.gateway.WorkPermitGateway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWorkPermit implements WorkPermitGateway {

    Connection sqlManager;

    @Override
    public List<WorkPermit> getWorkPermitList() {

        List<WorkPermit> workPermitList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM work_permit
                ORDER BY work_permit_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                WorkPermit workPermit = new WorkPermit();
                workPermit.setWorkPermitId(resultSet.getInt("work_permit_id"));
                workPermit.setLockoutDeviceId(resultSet.getString("lockout_device_id"));
                workPermit.setDescription(resultSet.getString("work_permit_description"));
                workPermit.setLotoProcedureId(resultSet.getInt("loto_procedure_id"));

                workPermitList.add(workPermit);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return workPermitList;

    }

    @Override
    public WorkPermit getWorkPermitById(int workPermitId) {

        WorkPermit workPermit = null;

        String sql = """
                SELECT *
                FROM work_permit
                WHERE work_permit_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, workPermitId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                workPermit = new WorkPermit();
                workPermit.setWorkPermitId(resultSet.getInt("work_permit_id"));
                workPermit.setLockoutDeviceId(resultSet.getString("lockout_device_id"));
                workPermit.setDescription(resultSet.getString("work_permit_description"));
                workPermit.setLotoProcedureId(resultSet.getInt("loto_procedure_id"));

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return workPermit;

    }

    @Override
    public int insertWorkPermit(WorkPermit workPermit) {

        int id = 0;

        String sql = """
                INSERT INTO work_permit (lockout_device_id, work_permit_description, loto_procedure_id)
                VALUES (?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, workPermit.getLockoutDeviceId());
            statement.setString(2, workPermit.getDescription());

            if (workPermit.getLotoProcedureId() == 0) {
                statement.setNull(3, Types.NULL);
            } else {
                statement.setInt(3, workPermit.getLotoProcedureId());
            }

            statement.executeUpdate();
            ResultSet keyGen = statement.getGeneratedKeys();
            if (keyGen.next()) {
                id = keyGen.getInt(1);
            }
            keyGen.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return id;

    }

    @Override
    public boolean deleteWorkPermit(WorkPermit workPermit) {

        String sql = """
                DELETE FROM work_permit
                WHERE work_permit_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, workPermit.getWorkPermitId());
            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                statement.close();
                return true;

            } else {
                statement.close();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;

    }

    @Override
    public boolean updateWorkPermit(WorkPermit workPermit) {

        String sql = """
                UPDATE work_permit
                SET lockout_device_id = ?, work_permit_description = ?, loto_procedure_id = ?
                WHERE work_permit_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, workPermit.getLockoutDeviceId());
            statement.setString(2, workPermit.getDescription());

            if (workPermit.getLotoProcedureId() == 0) {
                statement.setNull(3, Types.NULL);
            } else {
                statement.setInt(3, workPermit.getLotoProcedureId());
            }

            statement.setInt(4, workPermit.getWorkPermitId());
            statement.executeUpdate();

            if (statement.getUpdateCount() > 0) {
                statement.close();
                return true;

            } else {
                statement.close();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;
    }
}
