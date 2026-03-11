package com.workorderhub.provider.database;

import com.workorderhub.core.entity.SpareCategory;
import com.workorderhub.core.gateway.SpareCategoryGateway;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBSpareCategory implements SpareCategoryGateway {

    private Connection sqlManager;

    @Override
    public List<SpareCategory> GetAllCategories() {

        List<SpareCategory> spareCategoryList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM spare_part_category
                ORDER BY category_id;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                SpareCategory spareCategory = new SpareCategory();

                spareCategory.setCategoryID(resultSet.getInt("category_id"));
                spareCategory.setCategoryName(resultSet.getString("category_name"));

                spareCategoryList.add(spareCategory);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return spareCategoryList;

    }
}
