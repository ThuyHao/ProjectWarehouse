package com.example.dw_huy.db.DBMart;

import java.sql.*;

public class DBMartConnect {
    private static DBMartConnect install;

    private final String DbUrl = "jdbc:mysql://localhost:3306/dbmart";
    private final String user = "root";
    private final String pass = "";

    private final Connection connection;

    private DBMartConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DbUrl, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBMartConnect getInstall() {
        //7. connect to dbmart.db [modul4]
        if (install == null) install = new DBMartConnect();
        return install;
    }

    public PreparedStatement get(String sql) throws SQLException {
        if (connection == null) throw new SQLException("DB do not connect");
        return connection.prepareStatement(sql);
    }

    public static void main(String[] args) {
        try {
            PreparedStatement statement = DBMartConnect.getInstall().get("SELECT * FROM `authorsdim`");
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

