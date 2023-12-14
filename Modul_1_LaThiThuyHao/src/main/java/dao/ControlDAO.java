package dao;

import context.DBContext;
import model.*;
import org.jdbi.v3.core.Handle;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public class ControlDAO {

    /**
     * Select the controls table, arrange the ID in descending order, then retrieve the top row.
     * Check if its status is "RN"; if true, return true. Otherwise, return false.
     */
    public boolean isStatusRunning() {
        try (Handle handle = DBContext.me().open()) {
            String query = "SELECT name, description, status, config_id, created_at, updated_at FROM controls ORDER BY id DESC LIMIT 1";

            Optional<Controls> lastLog = handle.createQuery(query)
                    .mapToBean(Controls.class)
                    .findOne();

            return lastLog.isPresent() && "RN".equals(lastLog.get().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Insert one row into the "dbcontrol" table to record the event's name, description, status, and configuration ID.
     *
     * @param name (event name)
     * @param description (event description)
     * @param status (event status)
     * @param config_id (configuration ID)
     */
    public void insertControls(String name, String description, String status, int config_id) {
        try (Handle handle = DBContext.me().open()) {
            String query = "INSERT INTO controls (name, description, status, config_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            handle.createUpdate(query)
                    .bind(0, name)
                    .bind(1, description)
                    .bind(2, status)
                    .bind(3, config_id)
                    .bind(4, now)
                    .bind(5, now)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete the previously added row if its status is 'RN' in case the process fails.
     *
     * @param status (status condition for deletion)
     */
    public void deleteControls(String status) {
        try (Handle handle = DBContext.me().open()) {
            String query = "DELETE FROM controls WHERE status = ? ORDER BY id DESC LIMIT 1";
            handle.createUpdate(query)
                    .bind(0, status)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the status previously set to 'RN' to 'SC' if the process is successful.
     *
     * @param oldStatus (previous status)
     * @param newStatus (new status to be set)
     */
    public void updateControlsStatus(String oldStatus, String newStatus) {
        try (Handle handle = DBContext.me().open()) {
            String query = "UPDATE controls SET status = ? WHERE status = ? ORDER BY id DESC LIMIT 1";
            handle.createUpdate(query)
                    .bind(0, newStatus)
                    .bind(1, oldStatus)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
