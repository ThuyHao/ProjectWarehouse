package dao;

import context.DBContext;
import model.Controls;
import org.jdbi.v3.core.Handle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class FileDAO {

    /**
     * Select the files table and check if any of them has a row with status 'done'.
     * If true, return true. Otherwise, return false.
     *
     * @return the result of the method
     */
    public boolean isCheckFile() {
        try (Handle handle = DBContext.me().open()) {
            String query = "SELECT id, config_id, name, column_name, data_format, file_timestamp, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by FROM files ORDER BY id DESC LIMIT 1";

            Optional<Controls> lastLog = handle.createQuery(query)
                    .mapToBean(Controls.class)
                    .findOne();

            return lastLog.isPresent() && "done".equals(lastLog.get().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Insert one row into the files table with status set to 'RN' to indicate that the method is running.
     *
     * @param config_id
     * @param name
     * @param column_name
     * @param data_format
     * @param destination
     * @param dir_save
     * @param dir_archive
     * @param note
     * @param status
     * @param created_by
     * @param updated_by
     */
    public void insertFile(int config_id, String name, String column_name, String data_format, String destination, String dir_save, String dir_archive, String note, String status, String created_by, String updated_by) {
        try (Handle handle = DBContext.me().open()) {
            String query = "INSERT INTO files (config_id, name, column_name, data_format, destination, dir_save, dir_archive, note, status, created_at, updated_at, created_by, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            handle.createUpdate(query)
                    .bind(0, config_id)
                    .bind(1, name)
                    .bind(2, column_name)
                    .bind(3, data_format)
                    .bind(4, destination)
                    .bind(5, dir_save)
                    .bind(6, dir_archive)
                    .bind(7, note)
                    .bind(8, status)
                    .bind(9, now)
                    .bind(10, now)
                    .bind(11, created_by)
                    .bind(12, updated_by)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the previously added row if its status is 'RN' in case the data retrieval process fails.
     *
     * @param status (status condition for deletion)
     */
    public void deleteFiles(String status) {
        try (Handle handle = DBContext.me().open()) {
            String query = "DELETE FROM files WHERE status = ? ORDER BY id DESC LIMIT 1";
            handle.createUpdate(query)
                    .bind(0, status)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the status from 'RN' to 'SC' if the data file retrieval is successful.
     * @param name
     * @param timestamp
     * @param oldStatus (previous status)
     * @param newStatus (new status to be set)
     */
    public void updateFilesStatus(String name, Timestamp timestamp, String oldStatus, String newStatus) {
        try (Handle handle = DBContext.me().open()) {
            String query = "UPDATE files SET status = ?, name = ?, file_timestamp = ? WHERE status = ? ORDER BY id DESC LIMIT 1";
            handle.createUpdate(query)
                    .bind(0, newStatus)
                    .bind(1, name)
                    .bind(2, timestamp)
                    .bind(3, oldStatus)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
