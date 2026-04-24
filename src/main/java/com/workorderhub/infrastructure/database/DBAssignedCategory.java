package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.AssignedCategory;
import com.workorderhub.infrastructure.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBAssignedCategory implements com.workorderhub.core.gateway.AssignedCategoryGateway{

    private Connection sqlManager;

    @Override
    public List<AssignedCategory> getCategoriesByWorkOrder(long workOrderId) {
        List<AssignedCategory> assignedCategoryList = new ArrayList<>();
        String sql = """
                SELECT
                woc.work_order_id,
                woc.category_id,
                ca.category_name,
                ca.category_description
                FROM work_order_category woc
                LEFT JOIN category ca ON woc.category_id = ca.category_id
                WHERE woc.work_order_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, workOrderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                AssignedCategory assignedCategory = new AssignedCategory();

                assignedCategory.setWorkOrderId(resultSet.getLong("woc.work_order_id"));
                assignedCategory.setCategoryId(resultSet.getInt("woc.category_id"));
                assignedCategory.setCategoryName(resultSet.getString("ca.category_name"));
                assignedCategory.setCategoryDescription(resultSet.getString("ca.category_description"));

                assignedCategoryList.add(assignedCategory);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return assignedCategoryList;
    }

    @Override
    public boolean insertAssignedCategory(AssignedCategory assignedCategory) {
        String sql = """
                INSERT INTO work_order_category
                (work_order_id, category_id)
                VALUES (?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, assignedCategory.getWorkOrderId());
            statement.setInt(2, assignedCategory.getCategoryId());

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
    public boolean insertAssignedCategoryBatch(List<AssignedCategory> assignedCategoryList) {
        String sql = """
                INSERT INTO work_order_category
                (work_order_id, category_id)
                VALUES (?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            sqlManager.setAutoCommit(false);
            PreparedStatement statement = sqlManager.prepareStatement(sql);

            for (AssignedCategory assignedCategory : assignedCategoryList) {
                statement.setLong(1, assignedCategory.getWorkOrderId());
                statement.setInt(2, assignedCategory.getCategoryId());

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
    public boolean deleteAssignedCategory(AssignedCategory assignedCategory) {
        String sql = """
                DELETE FROM work_order_category
                WHERE work_order_id = ? AND category_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, assignedCategory.getWorkOrderId());
            statement.setInt(2, assignedCategory.getCategoryId());

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
    public boolean updateAssignedCategory(AssignedCategory assignedCategory, int newAssignedCategoryId) {
        String sql = """
                UPDATE work_order_category
                SET category_id = ?
                WHERE work_order_id = ? AND category_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, newAssignedCategoryId);
            statement.setLong(2, assignedCategory.getWorkOrderId());
            statement.setInt(3, assignedCategory.getCategoryId());

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
