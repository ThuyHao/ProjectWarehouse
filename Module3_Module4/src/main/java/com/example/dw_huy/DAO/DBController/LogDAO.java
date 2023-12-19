package com.example.dw_huy.DAO.DBController;


import com.example.dw_huy.beans.DBController.logs;
import com.example.dw_huy.db.DBController.DBControllerConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {
    private final DBControllerConnect dbConnect;

    public LogDAO() {
        this.dbConnect = DBControllerConnect.getInstall();
    }

    //insert log
    public void insertLog(String eventName, String eventType, String status, String location) {
        try {
            String sql = "INSERT INTO `logs`(`event_name`, `event_type`,`status`, `location`,`created_at`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, eventName);
            statement.setString(2, eventType);
            statement.setString(3, status);
            statement.setString(4, location);
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            statement.executeUpdate();
            System.out.println("insert log success + " + status + " + " + eventName + " + " + location);
        } catch (SQLException e) {
            System.out.println("insert log fail + " + status + " + " + eventName + " + " + location);
            throw new RuntimeException(e);
        }
    }

    //get all log
    public List<logs> getAllLogs() {
        List<logs> logsList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `logs`";
            PreparedStatement statement = dbConnect.get(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                logs logs = new logs();
                logs.setId(rs.getInt(1));
                logs.setEvent_name(rs.getString(2));
                logs.setEvent_type(rs.getString(3));
                logs.setStatus(rs.getString(4));
                logs.setLocation(rs.getString(5));
                logs.setCreated_at(rs.getTimestamp(6));
                

                logsList.add(logs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return logsList;
    }

    public static void main(String[] args) {
        //test
        LogDAO logDAO = new LogDAO();
        logDAO.insertLog("Test", "insert", "SC", "Modul1");
    }

}
