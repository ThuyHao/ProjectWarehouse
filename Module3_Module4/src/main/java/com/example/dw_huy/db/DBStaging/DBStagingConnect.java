package com.example.dw_huy.db.DBStaging;


import java.sql.*;

public class DBStagingConnect {
    private static DBStagingConnect install;

    private final String DbUrl = "jdbc:mysql://localhost:3306/dbstaging";
    private final String user = "root";
    private final String pass = "";

    private final Connection connection;

    private DBStagingConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DbUrl, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBStagingConnect getInstall() {
        //4. Connect to db staging
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


