package dao;

import model.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static context.DBContext.getConnection;

public class FileDAO {
    public static boolean checkStatusFile(Connection connectionControl) {
        String sql = "SELECT id, config_id, name, column_name, data_format, file_timestamp, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by\n" +
                "FROM files\n" +
                "WHERE status = 'SC' AND DATE(created_at) = CURRENT_DATE;\n ";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean updateStatusFile(Connection connectionControl) throws SQLException {
        String updateStatusDoneSQL = "UPDATE files " +
                "SET status = 'done' " +
                "WHERE DATE(created_at) = CURRENT_DATE AND status = 'SC' " +
                "ORDER BY created_at DESC LIMIT 1";

        try (Connection controlConnection = getConnection();
             PreparedStatement updateStatement = controlConnection.prepareStatement(updateStatusDoneSQL)) {
            updateStatement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }
        return true;
    }
    public static Files getFileCSV() {
        Files files = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, config_id, name, " +
                     "column_name, data_format, file_timestamp, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by FROM files ORDER BY id DESC LIMIT 1;")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    files = new Files();
                    files.setId(resultSet.getInt("id"));
                    files.setConfigId(resultSet.getInt("config_id"));
                    files.setName(resultSet.getString("name"));
                    files.setColumnName(resultSet.getString("column_name"));
                    files.setDataFormat(resultSet.getString("data_format"));
                    files.setFileTimestamp(resultSet.getTimestamp("file_timestamp"));
                    files.setDestination(resultSet.getString("destination"));
                    files.setDirSave(resultSet.getString("dir_save"));
                    files.setDirArchive(resultSet.getString("dir_archive"));
                    files.setNote(resultSet.getString("note"));
                    files.setStatus(resultSet.getString("status"));
                    files.setCreatedAt(resultSet.getTimestamp("created_at"));
                    files.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    files.setCreatedBy(resultSet.getString("created_by"));
                    files.setUpdatedBy(resultSet.getString("updated_by"));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return files;
    }
}
