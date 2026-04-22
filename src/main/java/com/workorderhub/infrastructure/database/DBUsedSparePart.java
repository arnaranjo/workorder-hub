package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.UsedSparePart;
import com.workorderhub.core.gateway.UsedSparePartGateway;
import com.workorderhub.infrastructure.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBUsedSparePart implements UsedSparePartGateway {

    private Connection sqlManager;

    @Override
    public List<UsedSparePart> getUsedSpareByWorkOrder(long workOrderId) {

        List<UsedSparePart> usedSparePartsList = new ArrayList<>();

        String sql = """
                SELECT
                wos.work_order_id,
                wos.selected_number,
                sp.spare_part_id,
                sp.spare_part_name,
                sp.spare_part_number
                FROM work_order_spare wos
                LEFT JOIN spare_part sp ON wos.spare_part_id = sp.spare_part_id
                WHERE wos.work_order_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, workOrderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UsedSparePart usedSparePart = new UsedSparePart();

                usedSparePart.setWorkOrderId(resultSet.getLong("wos.work_order_id"));
                usedSparePart.setSelectedNumber(resultSet.getInt("wos.selected_number"));
                usedSparePart.setSparePartId(resultSet.getInt("sp.spare_part_id"));
                usedSparePart.setSpareName(resultSet.getString("sp.spare_part_name"));
                usedSparePart.setSpareNumber(resultSet.getString("sp.spare_part_number"));

                usedSparePartsList.add(usedSparePart);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return usedSparePartsList;

    }

    @Override
    public boolean insertUsedSparePart(UsedSparePart usedSparePart) {

        String sql = """
                INSERT INTO work_order_spare
                (work_order_id, spare_part_id, selected_number)
                VALUES (?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, usedSparePart.getWorkOrderId());
            statement.setInt(2, usedSparePart.getSparePartId());
            statement.setInt(3, usedSparePart.getSelectedNumber());

            statement.executeUpdate();
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
    public boolean insertUsedSparePartBatch(List<UsedSparePart> usedSparePartList) {

        String sql = """
                INSERT INTO work_order_spare
                (work_order_id, spare_part_id, selected_number)
                VALUES (?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            sqlManager.setAutoCommit(false);
            PreparedStatement statement = sqlManager.prepareStatement(sql);

            for (UsedSparePart usedSparePart : usedSparePartList) {
                statement.setLong(1, usedSparePart.getWorkOrderId());
                statement.setInt(2, usedSparePart.getSparePartId());
                statement.setInt(3, usedSparePart.getSelectedNumber());

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
    public boolean deleteUsedSparePart(UsedSparePart usedSparePart) {

        String sql = """
                DELETE FROM work_order_spare
                WHERE work_order_id = ? AND spare_part_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, usedSparePart.getWorkOrderId());
            statement.setInt(2, usedSparePart.getSparePartId());

            statement.executeUpdate();
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
    public boolean updateUsedSparePart(UsedSparePart usedSparePart) {

        String sql = """
                UPDATE work_order_spare
                SET selected_number = ?
                WHERE work_order_id = ? AND spare_part_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, usedSparePart.getSelectedNumber());
            statement.setLong(2, usedSparePart.getWorkOrderId());
            statement.setInt(3, usedSparePart.getSparePartId());

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;
    }
}
