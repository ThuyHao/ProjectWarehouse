package com.example.dw_huy.db.DBController;

import com.example.dw_huy.db.DBStaging.DBStagingConnect;

import java.sql.*;

public class DBControllerConnect {
    private static DBControllerConnect install;

    private final String DbUrl = "jdbc:mysql://localhost:3306/dbcontroller";
    private final String user = "root";
    private final String pass = "";

    private final Connection connection;

    private DBControllerConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DbUrl, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBControllerConnect getInstall() {
        if (install == null) install = new DBControllerConnect();
        return install;
    }

    public PreparedStatement get(String sql) throws SQLException {
        if (connection == null) throw new SQLException("DB do not connect");
        return connection.prepareStatement(sql);
    }

    public static void main(String[] args) {
        try {
            PreparedStatement statement = DBControllerConnect.getInstall().get("SELECT * FROM `controls`");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


