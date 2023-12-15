package dao;

import context.DBContext;
import org.jdbi.v3.core.Handle;

import java.sql.Timestamp;
import java.time.LocalDateTime;


public class LogDAO {

    /**
     * Writes a log entry when the event is executed.
     * @param eventName
     * @param eventType
     * @param status
     * @param location
     */
    public void insertLogs(String eventName, String eventType, String status, String location) {
        try (Handle handle = DBContext.me().open()) {
            String query = "INSERT INTO logs (event_name, event_type, status, location, created_at) VALUES (?, ?, ?, ?, ?)";
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            handle.createUpdate(query)
                    .bind(0, eventName)
                    .bind(1, eventType)
                    .bind(2, status)
                    .bind(3, location)
                    .bind(4, now)
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogDAO log = new LogDAO();
        log.insertLogs("Test", "insert", "SC", "Modul1");
    }

}
