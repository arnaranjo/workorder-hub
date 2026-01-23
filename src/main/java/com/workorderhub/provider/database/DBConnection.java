package com.workorderhub.provider.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Properties properties = new Properties();
    private static FileInputStream fileInputStream = null;

    private static String address;
    private static String username;
    private static String password;

    private static Connection dbAccess = null;

    static {
        try {
            fileInputStream = new FileInputStream(
                    "src/main/java/com/workorderhub/provider/ui/properties/config.properties"
            );
            properties.load(fileInputStream);

            address = properties.getProperty("database.URL");
            username = properties.getProperty("database.username");
            password = properties.getProperty("database.password");
            String connector = properties.getProperty("database.connector");

            Class.forName(connector);
            dbAccess = DriverManager.getConnection(address, username, password);

        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }

    public static Connection DBConnect() {
        try {
            if (dbAccess == null) {
                dbAccess = DriverManager.getConnection(address, username, password);

                System.out.println("Connected...");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbAccess;
    }

    public static void DBDisconnect() {
        try {
            if (dbAccess != null && !dbAccess.isClosed()) {
                dbAccess.close();
                dbAccess = null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
