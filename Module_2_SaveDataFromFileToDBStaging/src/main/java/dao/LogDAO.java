package dao;

import context.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogDAO {
    Connection connectionControl = DBContext.getConnection();

    public LogDAO() throws SQLException {
    }

    public static void insertLogs(Connection connectionControl, String event_name, String event_type, String status, String location) throws SQLException {

        String logsInsertSQL = "INSERT INTO logs (event_name, event_type, status,  location, created_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";
        try (PreparedStatement logsPreparedStatement = connectionControl.prepareStatement(logsInsertSQL)) {
            logsPreparedStatement.setString(1, event_name);
            logsPreparedStatement.setString(2, event_type);
            logsPreparedStatement.setString(3, status);
            logsPreparedStatement.setString(4, location);

            logsPreparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

}
