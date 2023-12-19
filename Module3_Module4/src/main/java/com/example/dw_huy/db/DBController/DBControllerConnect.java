package com.example.dw_huy.db.DBController;

import com.example.dw_huy.db.DBStaging.DBStagingConnect;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Queue;

public class DBControllerConnect {
    private static DBControllerConnect install;


    private final Connection connection;

    private DBControllerConnect() {
        //1. Load configs.properties
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("configs.properties")) {
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String serverName = prop.getProperty("serverName");
        String dbName = prop.getProperty("dbName_controller");
        String portNumber = prop.getProperty("portNumber");
        String instance = prop.getProperty("instance");
        String userID = prop.getProperty("userID");
        String password = prop.getProperty("password");

        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
        if (instance != null && !instance.trim().isEmpty()) {
            url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + instance + "/" + dbName;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, userID, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBControllerConnect getInstall() {
        //2. Connect to db controller
        if (install == null) install = new DBControllerConnect();
        return install;
    }

    public PreparedStatement get(String sql) throws SQLException {
        if (connection == null) throw new SQLException("DB do not connect");
        return connection.prepareStatement(sql);
    }

    public static void main(String[] args) {
        //test get data from db controller
        try {
            PreparedStatement statement = DBControllerConnect.getInstall().get("SELECT * FROM `configs`");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


