package com.example.dw_huy.DAO;


import com.example.dw_huy.beans.DBController.logs;
import com.example.dw_huy.db.DBController.DBControllerConnect;
import com.example.dw_huy.db.DBStaging.DBStagingConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {
    private final DBControllerConnect dbConnect;

    public LogDAO() {
        this.dbConnect = DBControllerConnect.getInstall();
    }

    //insert log
    public void insertLog(String status, String event_name, String location) {
        try {
            String sql = "INSERT INTO `logs`(`status`, `event_name`, `location`) VALUES (?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, status);
            statement.setString(2, event_name);
            statement.setString(3, location);

            statement.executeUpdate();
            System.out.println("insert log success + " + status + " + " + event_name + " + " + location);
        } catch (SQLException e) {
            System.out.println("insert log fail + " + status + " + " + event_name + " + " + location);
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
                logs.setTimestamp(rs.getTimestamp(2));
                logs.setStatus(rs.getString(3));
                logs.setEvent_name(rs.getString(4));
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
        logDAO.insertLog("success", "test", "test");
        List<logs> logsList = logDAO.getAllLogs();
        for (logs logs : logsList) {
            System.out.println(logs.getId());
            System.out.println(logs.getTimestamp());
            System.out.println(logs.getStatus());
            System.out.println(logs.getEvent_name());
            System.out.println(logs.getLocation());
            System.out.println(logs.getCreated_at());
        }
    }

}
