package dao;

import context.DBContext;

import java.sql.*;

import static context.DBContext.getConnection;

public class ControlDAO {
    Connection connectionControl = DBContext.getConnection();

    public ControlDAO() throws SQLException {
    }

    public static boolean checkStatusControl(Connection connectionControl) {
        String sql = "SELECT id, name, description, status, config_id, created_at, updated_at\n" +
                "FROM controls\n" +
                "WHERE status = 'RN' AND DATE(created_at) = CURRENT_DATE\n" +
                "ORDER BY created_at DESC\n" +
                "LIMIT 1;\n";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void insertRowControl(Connection connectionControl) throws SQLException {
        String insertSQL = "INSERT INTO controls (name, description, status, config_id, created_at, updated_at)" +
                " VALUES (?, ?, 'RN', ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (PreparedStatement preparedStatement = connectionControl.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, "Save data to DBStaging");
            preparedStatement.setString(2, "Save data from file GetDataWeb_yyyyMMdd_HHmmss.csv to DBStaging "); // Thay thế "Sample Description" bằng giá trị thực tế
            preparedStatement.setInt(3, 15); // Thay thế 1 bằng giá trị thực tế cho config_id
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            /**
             * 6.1 insert into the Logs table:
             * event_type, status, event_name, location, created_at with status='EI'
             */
            // Insert error log
            LogDAO.insertLogs(connectionControl, "Insert a Row from controls table", "error insert a row into table controls with status ='RN' and AND DATE(created_at) = CURRENT_DATE",
                    "EI", "Modul 2");

        }

    }
    public static void deleteRowControl(Connection connectionControl) {
        String deleteSQL = "DELETE FROM controls WHERE status = 'RN' AND DATE(created_at) = CURRENT_DATE";

        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean updateStatusControl(Connection connectionControl) throws SQLException {
        String updateStatusDoneSQL = "UPDATE controls " +
                "SET status = 'SC' " +
                "WHERE DATE(created_at) = CURRENT_DATE AND status = 'RN' " +
                "ORDER BY created_at DESC LIMIT 1";

        try (Connection controlConnection = getConnection();
             PreparedStatement updateStatement = controlConnection.prepareStatement(updateStatusDoneSQL)) {
            updateStatement.executeUpdate();
        } catch (SQLException e) {

            throw new RuntimeException("Error updating status into Controls table", e);
        }
        return true;
    }
}

