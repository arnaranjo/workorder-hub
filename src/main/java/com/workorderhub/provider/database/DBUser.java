package com.workorderhub.provider.database;

import com.workorderhub.core.entity.User;
import com.workorderhub.core.entity.UserRoleEnum;
import com.workorderhub.core.gateway.UserGateway;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUser implements UserGateway {

    private Connection sqlManager;

    @Override
    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM employee
                ORDER BY employee_id;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("employee_id"));
                user.setUserName(resultSet.getString("employee_name"));
                user.setUserPhoneNumber(resultSet.getString("phone_number"));
                user.setUserEmail(resultSet.getString("company_email"));
                user.setIdRol(resultSet.getInt("role_id"));
                user.setIdAccess(resultSet.getInt("user_credentials_id"));

                userList.add(user);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return userList;
    }

    @Override
    public List<User> getUsersByRole(UserRoleEnum userRole) {
        List<User> userList = new ArrayList<>();
        String sql = """
                SELECT e.*
                FROM employee e
                INNER JOIN employee_role er ON e.role_id = er.role_id
                WHERE er.role_name = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, userRole.GetRoleName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("employee_id"));
                user.setUserName(resultSet.getString("employee_name"));
                user.setUserPhoneNumber(resultSet.getString("phone_number"));
                user.setUserEmail(resultSet.getString("company_email"));
                user.setIdRol(resultSet.getInt("role_id"));
                user.setIdAccess(resultSet.getInt("user_credentials_id"));

                userList.add(user);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return userList;
    }

    @Override
    public User getUser(String name, String email) {
        User user = null;
        String sql = """
                SELECT *
                FROM employee
                WHERE employee_name = ? AND company_email = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();

                user.setId(resultSet.getInt("employee_id"));
                user.setUserName(resultSet.getString("employee_name"));
                user.setUserPhoneNumber(resultSet.getString("phone_number"));
                user.setUserEmail(resultSet.getString("company_email"));
                user.setIdRol(resultSet.getInt("role_id"));
                user.setIdAccess(resultSet.getInt("user_credentials_id")); //If null, .getInt() return 0.

            }

            resultSet.close();
            statement.close();
            return user;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return user;
    }

    @Override
    public User getUserByCredentials(int idCredentials) {
        User user = null;
        String sql = """
                SELECT *
                FROM employee
                WHERE user_credentials_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, idCredentials);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();

                user.setId(resultSet.getInt("employee_id"));
                user.setUserName(resultSet.getString("employee_name"));
                user.setUserPhoneNumber(resultSet.getString("phone_number"));
                user.setUserEmail(resultSet.getString("company_email"));
                user.setIdRol(resultSet.getInt("role_id"));
                user.setIdAccess(resultSet.getInt("user_credentials_id"));

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return user;

    }

    @Override
    public int insertUser(User user) {
        int id = 0;
        String sql = """
                INSERT INTO employee (employee_name, phone_number, company_email, role_id, user_credentials_id)
                VALUES (?, ?, ?, ?, ?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPhoneNumber());
            statement.setString(3, user.getUserEmail());
            statement.setInt(4, user.getIdRol());
            if (user.getIdAccess() == 0) {
                statement.setNull(5, Types.NULL);
            } else {
                statement.setInt(5, user.getIdAccess());
            }

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
    public boolean deleteUser(User user) {
        String sql = """
                DELETE FROM employee
                WHERE employee_id = ? AND employee_name = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, user.getId());
            statement.setString(2, user.getUserName());

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
    public boolean updateUser(User user) {
        String sql = """
                UPDATE employee
                SET employee_name = ?, phone_number = ?, company_email  = ?, role_id  = ?, user_credentials_id  = ?
                WHERE employee_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getUserPhoneNumber());
            statement.setString(3, user.getUserEmail());
            statement.setInt(4, user.getIdRol());
            if (user.getIdAccess() == 0) {
                statement.setNull(5, Types.NULL);
            } else {
                statement.setInt(5, user.getIdAccess());
            }
            statement.setInt(6, user.getId());

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
