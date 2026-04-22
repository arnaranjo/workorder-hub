package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.UserRole;
import com.workorderhub.core.gateway.UserRoleGateway;
import com.workorderhub.infrastructure.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DBUserRole implements UserRoleGateway {

    private Connection sqlManager;

    @Override
    public List<UserRole> GetAllRoles() {

        List<UserRole> roleList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM employee_role
                ORDER BY role_id;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                UserRole userRole = new UserRole();

                userRole.setRoleId(resultSet.getInt("role_id"));
                userRole.setRoleName(resultSet.getString("role_name"));

                roleList.add(userRole);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return roleList;

    }

    @Override
    public UserRole GetRole(String role) {

        UserRole userRole = null;
        String sql = """
                SELECT *
                FROM employee_role
                WHERE role_name = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userRole = new UserRole();

                userRole.setRoleId(resultSet.getInt("role_id"));
                userRole.setRoleName(resultSet.getString("role_name"));

            }

            resultSet.close();
            statement.close();
            return userRole;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return userRole;

    }

    @Override
    public int InsertRole(String roleName) {
        int id = 0;
        String sql = """
                INSERT INTO employee_role (role_name)
                VALUES (?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, roleName);

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
    public boolean DeleteRole(String roleName) {

        String sql = """
                DELETE FROM employee_role
                WHERE role_name = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, roleName);

            int row = statement.executeUpdate();
            String result = row != 0 ? "Fila eliminada" : "Fila no eliminada";
            System.out.println(result);

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
    public boolean UpdateRole(UserRole role) {

        String sql = """
                UPDATE employee_role
                SET role_name = ?
                WHERE role_id = ?
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, role.getRoleName());
            statement.setInt(2, role.getRoleId());

            int row = statement.executeUpdate();
            String result = row != 0 ? "Fila actualizada" : "Fila no actualizada";
            System.out.println(result);

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
