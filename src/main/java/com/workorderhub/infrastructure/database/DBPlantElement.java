package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.PlantElement;
import com.workorderhub.core.gateway.PlantElementGateway;
import com.workorderhub.infrastructure.common.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBPlantElement implements PlantElementGateway {

    private Connection sqlManager;

    @Override
    public List<PlantElement> GetPlantElementsList() {
        List<PlantElement> elementsList = new ArrayList<>();

        String sql = """
                SELECT *
                FROM plant_element
                ORDER BY element_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                PlantElement plantElement = new PlantElement.Builder()
                        .withElementId(resultSet.getInt("element_id"))
                        .withElementTag(resultSet.getString("element_tag"))
                        .withElementDescription(resultSet.getString("element_description"))
                        .withElementLocation(resultSet.getString("element_location"))
                        .withInspectionDate(
                                resultSet.getDate("inspection_date") == null ?
                                        null : resultSet.getDate("inspection_date").toLocalDate()
                        )
                        .withInspectionFrequency(resultSet.getInt("element_check_frequency"))
                        .build();

                elementsList.add(plantElement);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return elementsList;
    }

    @Override
    public PlantElement GetPlantElementByTag(String tag) {
        PlantElement plantElement = null;
        String sql = """
                SELECT *
                FROM plant_element
                WHERE element_tag = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, tag);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                plantElement = new PlantElement.Builder()
                        .withElementId(resultSet.getInt("element_id"))
                        .withElementTag(resultSet.getString("element_tag"))
                        .withElementDescription(resultSet.getString("element_description"))
                        .withElementLocation(resultSet.getString("element_location"))
                        .withInspectionDate(
                                resultSet.getDate("inspection_date") == null ?
                                        null : resultSet.getDate("inspection_date").toLocalDate())
                        .withInspectionFrequency(resultSet.getInt("element_check_frequency"))
                        .build();

            }
            resultSet.close();
            statement.close();
            return plantElement;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return plantElement;

    }

    @Override
    public int InsertPlantElement(PlantElement plantElement) {
        int id = 0;
        String sql = """
                INSERT INTO plant_element
                (element_tag, element_description, element_location, inspection_date, element_check_frequency)
                VALUES (?,?,?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, plantElement.getElementTag());
            statement.setString(2, plantElement.getElementDescription());
            statement.setString(3, plantElement.getElementLocation());
            if (plantElement.getInspectionDate() == null) {
                statement.setNull(4, Types.DATE);
            } else {
                statement.setDate(4, Date.valueOf(plantElement.getInspectionDate()));
            }
            if (plantElement.getInspectionFrequency() == 0) {
                statement.setNull(5, Types.INTEGER);
            } else {
                statement.setInt(5, plantElement.getInspectionFrequency());
            }

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
    public boolean DeletePlantElement(int elementId) {
        String sql = """
                DELETE FROM plant_element
                WHERE element_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, elementId);

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return false;

        } finally {
            DBConnection.DBDisconnect();

        }
    }

    @Override
    public boolean UpdateElementTag(int elementId, String tag) {
        String sql = """
                UPDATE plant_element
                SET element_tag = ?
                WHERE element_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, tag);
            statement.setInt(2, elementId);

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
    public boolean UpdateElementDescription(int elementId, String description) {
        String sql = """
                UPDATE plant_element
                SET element_description = ?
                WHERE element_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, description);
            statement.setInt(2, elementId);

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
    public boolean UpdateElementLocation(int elementId, String location) {
        String sql = """
                UPDATE plant_element
                SET element_location = ?
                WHERE element_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, location);
            statement.setInt(2, elementId);

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
    public boolean UpdateElementInspectionDate(int elementId, LocalDate inspectionDate) {
        String sql = """
                UPDATE plant_element
                SET inspection_date = ?
                WHERE element_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            if (inspectionDate == null) {
                statement.setNull(1, Types.DATE);
            } else {
                statement.setDate(1, Date.valueOf(inspectionDate));
            }
            statement.setInt(2, elementId);

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
    public boolean UpdateElementInspectionFrequency(int elementId, int inspectionFrequency) {
        String sql = """
                UPDATE plant_element
                SET element_check_frequency = ?
                WHERE element_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            if (inspectionFrequency == 0) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, inspectionFrequency);
            }
            statement.setInt(2, elementId);

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
