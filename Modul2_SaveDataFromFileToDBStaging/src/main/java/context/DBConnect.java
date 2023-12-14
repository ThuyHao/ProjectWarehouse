package context;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String jdbcUrlStaging = "jdbc:mysql://localhost:3306/DBStaging";
    private static final String jdbcUrlControl = "jdbc:mysql://localhost:3306/DBController";
    private static final String username = "root";
    private static final String password = "Root@123";

    public static Connection getConnectionDBStaging() throws SQLException {
        return DriverManager.getConnection(jdbcUrlStaging, username, password);
   }
    public static Connection getConnectionDBControl() throws SQLException {
        return DriverManager.getConnection(jdbcUrlControl, username, password);
    }
}