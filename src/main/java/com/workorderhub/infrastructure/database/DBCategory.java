package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.Category;
import com.workorderhub.core.gateway.CategoryGateway;
import com.workorderhub.infrastructure.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBCategory implements CategoryGateway {

    private Connection sqlManager;

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM category
                ORDER BY category_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                category.setCategoryDescription(resultSet.getString("category_description"));

                categoryList.add(category);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.printf(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return categoryList;

    }

    @Override
    public int getCategoryId(Category category) {
        String sql = """
                SELECT category_id
                FROM category
                WHERE category_name = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, category.getCategoryName());

            ResultSet resultSet = statement.executeQuery();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt("category_id");
            }

            resultSet.close();
            statement.close();
            return id;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return 0;
    }

    @Override
    public int insertCategory(Category category) {
        int id = 0;
        String sql = """
                INSERT INTO category
                (category_name, category_description)
                VALUES (?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getCategoryDescription());

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
    public boolean deleteCategory(Category category) {
        String sql = """
                DELETE FROM category
                WHERE category_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, category.getCategoryId());

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
    public boolean updateCategory(Category category) {
        String sql = """
                UPDATE category
                SET category_name = ?, category_description = ?
                WHERE category_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getCategoryDescription());
            statement.setInt(3, category.getCategoryId());

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
