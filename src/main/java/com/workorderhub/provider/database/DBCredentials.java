package com.workorderhub.provider.database;

import com.workorderhub.core.entity.Credentials;
import com.workorderhub.core.gateway.CredentialsGateway;

import java.sql.*;

public class DBCredentials implements CredentialsGateway {

    private Connection sqlManager;

    @Override
    public int getCredentialsId(Credentials access) {

        String sql = """
                SELECT user_credentials_id
                FROM user_credentials
                WHERE user_name = ? AND user_password = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, access.getName());
            statement.setString(2, access.getAccessKey());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("user_credentials_id");

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }

        return -1;
    }

    @Override
    public Credentials getCredentialsById(int id) {

        Credentials credentials = null;
        String sql = """
                SELECT user_name, user_password
                FROM user_credentials
                WHERE user_credentials_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                credentials = new Credentials();
                credentials.setAccessId(id);
                credentials.setName(resultSet.getString("user_name"));
                credentials.setAccessKey(resultSet.getString("user_password"));

                return credentials;

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }

        return credentials;
    }

    @Override
    public int insertCredentials(Credentials access) {
        int id = 0;
        String sql = """
                INSERT INTO user_credentials (user_name, user_password)
                VALUES (?, ?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, access.getName());
            statement.setString(2, access.getAccessKey());

            statement.executeUpdate();
            ResultSet keyGen = statement.getGeneratedKeys();
            if (keyGen.next()) {
                id = keyGen.getInt(1);
            }
            statement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return id;

    }

    @Override
    public boolean deleteCredentials(Credentials access) {

        String sql = """
                DELETE FROM user_credentials
                WHERE user_credentials_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, access.getAccessId());

            int row = statement.executeUpdate();
            String result = row != 0 ? "Fila eliminada" : "Fila no eliminada";
            System.out.println(result);

            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;

    }

    @Override
    public boolean updateCredentials(Credentials access) {

        String sql = """
                UPDATE user_credentials
                SET user_name = ?, user_password = ?
                WHERE user_credentials_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, access.getName());
            statement.setString(2, access.getAccessKey());
            statement.setInt(3, access.getAccessId());

            int row = statement.executeUpdate();
            String result = row != 0 ? "Fila actualizada" : "Fila no actualizada";
            System.out.println(result);

            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;

    }
}
