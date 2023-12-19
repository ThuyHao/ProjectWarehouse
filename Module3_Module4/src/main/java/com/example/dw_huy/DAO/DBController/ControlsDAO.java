package com.example.dw_huy.DAO.DBController;

import com.example.dw_huy.db.DBController.DBControllerConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControlsDAO {
    private final DBControllerConnect dbConnect;

    public ControlsDAO() {
        this.dbConnect = DBControllerConnect.getInstall();
    }

    //insert controls
    public String insertControl(int config_id, String name, String description, String status) {
        String res = "";
        try {
            String sql = "INSERT INTO `controls`(`config_id`, `name`, `description`, `status`, `created_at`, `updated_at`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, config_id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setString(4, status);
            statement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            statement.setTimestamp(6, null);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert control success");
                res = "SC";
            } else {
                System.out.println("Insert control fail");
                res = "EI";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    //delete 1 latest control
    public void deleteControl() {
        String res = "";
        try {
            String sql = "DELETE FROM `controls` WHERE status = 'RN' ORDER BY id DESC LIMIT 1";
            PreparedStatement statement = dbConnect.get(sql);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Delete control success");
                res = "SC";
            } else {
                System.out.println("No process need to be deleted");
                res = "ED";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //update controls that just insert
    public String updateControl(String status) {
        String res = "";
        try {
            String sql = "UPDATE `controls` SET `status`=?,`updated_at`=NOW() ORDER BY id DESC LIMIT 1";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, status);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Update control success");
                res = "SC";
            } else {
                System.out.println("Update control fail");
                res = "EU";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }


    //2.
    public boolean checkControlRunning() {
        boolean check = false;
        try {
            String sql = "SELECT * FROM `controls` WHERE status = 'RN'";
            PreparedStatement statement = dbConnect.get(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    //get the latest config id
    public int getLatestConfigId() {
        int configId = 0;
        try {
            String sql = "SELECT id FROM `configs` ORDER BY id DESC LIMIT 1";
            PreparedStatement statement = dbConnect.get(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                configId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return configId;
    }
//get only 1 id by name





    public static void main(String[] args) {
        ControlsDAO controlsDAO = new ControlsDAO();
    controlsDAO.deleteControl();
    }
}
