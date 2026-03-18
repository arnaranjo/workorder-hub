package com.workorderhub.provider.database;

import com.workorderhub.core.entity.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBStatus implements com.workorderhub.core.gateway.StatusGateway {

    private Connection sqlManager;

    @Override
    public List<Status> GetStatusList() {
        List<Status> statusList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM status
                ORDER BY status_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Status status = new Status();
                status.setStatusId(resultSet.getInt("status_id"));
                status.setOrderStatus(resultSet.getString("order_status"));

                statusList.add(status);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return statusList;

    }

    @Override
    public int InsertStatus(Status status) {
        int id = 0;
        String sql = """
                INSERT INTO status
                order_status
                VALUES ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, status.getOrderStatus());

            statement.executeUpdate();
            ResultSet keyGen = statement.getGeneratedKeys();
            if (keyGen.next()) {
                id = keyGen.getInt(1);
            }
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return id;
    }

    @Override
    public boolean DeleteStatus(Status status) {
        String sql = """
                DELETE FROM status
                WHERE status_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, status.getStatusId());

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
    public boolean UpdateStatus(Status status) {
        String sql = """
                UPDATE status
                SET order_status = ?
                WHERE status_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, status.getOrderStatus());
            statement.setInt(2, status.getStatusId());

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
