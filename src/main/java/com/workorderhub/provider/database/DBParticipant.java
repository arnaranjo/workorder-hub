package com.workorderhub.provider.database;

import com.workorderhub.core.entity.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBParticipant implements com.workorderhub.core.gateway.ParticipantGateway {

    private Connection sqlManager;

    @Override
    public List<Participant> getParticipantsByWorkOrder(long workOrderId) {
        List<Participant> participlantList = new ArrayList<>();
        String sql = """
                SELECT
                woe.work_order_id,
                woe.employee_id,
                em.employee_name,
                em.company_email,
                em.phone_number
                FROM work_order_employee woe
                LEFT JOIN employee em ON woe.employee_id = em.employee_id
                WHERE woe.work_order_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, workOrderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Participant participant = new Participant();

                participant.setWorkOrderId(resultSet.getLong("woe.work_order_id"));
                participant.setUserId(resultSet.getInt("woe.employee_id"));
                participant.setEmployeeName(resultSet.getString("em.employee_name"));
                participant.setEmployeeEmail(resultSet.getString("em.company_email"));
                participant.setEmployeePhoneNumber(resultSet.getString("em.phone_number"));

                participlantList.add(participant);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return participlantList;
    }

    @Override
    public boolean insertParticipant(Participant participant) {
        String sql = """
                INSERT INTO work_order_employee
                (work_order_id, employee_id)
                VALUES (?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, participant.getWorkOrderId());
            statement.setInt(2, participant.getUserId());

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            DBConnection.DBDisconnect();

        }
    }

    @Override
    public boolean insertParticipantBatch(List<Participant> participantList) {
        String sql = """
                INSERT INTO work_order_employee
                (work_order_id, employee_id)
                VALUES (?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            sqlManager.setAutoCommit(false);
            PreparedStatement statement = sqlManager.prepareStatement(sql);

            for (Participant participant : participantList) {
                statement.setLong(1, participant.getWorkOrderId());
                statement.setInt(2, participant.getUserId());

                statement.addBatch();
            }

            statement.executeBatch();
            sqlManager.commit();
            sqlManager.setAutoCommit(true);

            statement.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;
    }

    @Override
    public boolean deleteParticipant(Participant participant) {
        String sql = """
                DELETE FROM work_order_employee
                WHERE work_order_id = ? AND employee_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, participant.getWorkOrderId());
            statement.setInt(2, participant.getUserId());

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            DBConnection.DBDisconnect();

        }
    }

    @Override
    public boolean updateParticipant(Participant participant, int newParticipantId) {
        String sql = """
                UPDATE work_order_employee
                SET employee_id = ?
                WHERE work_order_id = ? AND employee_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, newParticipantId);
            statement.setLong(2, participant.getWorkOrderId());
            statement.setInt(3, participant.getUserId());

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        } finally {
            DBConnection.DBDisconnect();

        }
    }
}
