package com.workorderhub.provider.database;

import com.workorderhub.core.entity.SparePart;
import com.workorderhub.core.gateway.SparePartGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBSparePart implements SparePartGateway {

    private Connection sqlManager;

    @Override
    public List<SparePart> getSparePartList() {
        List<SparePart> sparePartsList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM spare_part
                ORDER BY spare_part_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                SparePart sparePart = new SparePart.Builder()
                        .withSpareId(resultSet.getInt("spare_part_id"))
                        .withSpareName(resultSet.getString("spare_part_name"))
                        .withSpareNumber(resultSet.getString("spare_part_number"))
                        .withSpareDescription(resultSet.getString("spare_part_description"))
                        .withSpareStock(resultSet.getInt("spare_part_stock"))
                        .withSpareCategory(resultSet.getInt("spare_part_category_id"))
                        .build();
                sparePartsList.add(sparePart);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return sparePartsList;
    }

    @Override
    public int getSparePartId(SparePart sparePart) {
        String sql = """
                SELECT spare_part_id
                FROM spare_part
                WHERE spare_part_name = ? AND spare_part_number = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, sparePart.getSpareName());
            statement.setString(2, sparePart.getSpareNumber());

            ResultSet resultSet = statement.executeQuery();
            int id = -1;
            if (resultSet.next()) {
                id = resultSet.getInt("spare_part_id");

            }
            resultSet.close();
            statement.close();
            return id;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return -1;

    }

    @Override
    public int insertSparePart(SparePart sparePart) {
        int id = 0;
        String sql = """
                INSERT INTO spare_part
                (spare_part_name, spare_part_number, spare_part_description, spare_part_stock, spare_part_category_id)
                VALUES (?,?,?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, sparePart.getSpareName());
            statement.setString(2, sparePart.getSpareNumber());
            statement.setString(3, sparePart.getSpareDescription());
            statement.setInt(4, sparePart.getSpareStock());
            statement.setInt(5, sparePart.getSpareCategory());

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
    public boolean deleteSparePart(int sparePartId) {
        String sql = """
                DELETE FROM spare_part
                WHERE spare_part_id =  ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, sparePartId);

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
    public boolean updateSparePartName(int sparePartId, String sparePartName) {
        String sql = """
                UPDATE spare_part
                SET spare_part_name = ?
                WHERE spare_part_id = ?;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, sparePartName);
            statement.setInt(2, sparePartId);

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
    public boolean updateSparePartNumber(int sparePartId, String sparePartNumber) {
        String sql = """
                UPDATE spare_part
                SET spare_part_number = ?
                WHERE spare_part_id = ?;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, sparePartNumber);
            statement.setInt(2, sparePartId);

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
    public boolean updateSparePartDescription(int sparePartId, String sparePartDescription) {
        String sql = """
                UPDATE spare_part
                SET spare_part_description = ?
                WHERE spare_part_id = ?;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, sparePartDescription);
            statement.setInt(2, sparePartId);

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
    public boolean updateSparePartStock(int sparePartId, int sparePartStock) {
        String sql = """
                UPDATE spare_part
                SET spare_part_stock = ?
                WHERE spare_part_id = ?;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, sparePartStock);
            statement.setInt(2, sparePartId);

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
    public boolean updateSparePartCategory(int sparePartId, int sparePartCategory) {
        String sql = """
                UPDATE spare_part
                SET spare_part_category_id = ?
                WHERE spare_part_id = ?;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, sparePartCategory);
            statement.setInt(2, sparePartId);

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
