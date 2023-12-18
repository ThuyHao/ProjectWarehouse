package com.example.dw_huy.db.DBStaging;


import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBStagingConnect {
    private static DBStagingConnect install;

    private final Connection connection;

    private DBStagingConnect() {
        //1. Load config.properties
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String serverName = prop.getProperty("serverName");
        String dbName = prop.getProperty("dbName_staging");
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


    public static DBStagingConnect getInstall() {
        //6. Connect to db staging
        if (install == null) install = new DBStagingConnect();
        return install;
    }

    public PreparedStatement get(String sql) throws SQLException {
        if (connection == null) throw new SQLException("DB do not connect");
        return connection.prepareStatement(sql);
    }

    public static void main(String[] args) {
        try {
            PreparedStatement statement = DBStagingConnect.getInstall().get("SELECT * FROM `staging`");
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


