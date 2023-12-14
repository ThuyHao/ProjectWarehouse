package com.example.dw_huy.DAO;

import com.example.dw_huy.beans.DBController.configs;
import com.example.dw_huy.db.DBController.DBControllerConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigDAO {
    private final DBControllerConnect dbConnect;

    public ConfigDAO() {
        this.dbConnect = DBControllerConnect.getInstall();
    }

    //check config id exist
    public boolean checkConfigId(int id) {
        boolean res = false;
        try {
            String sql = "SELECT * FROM `configs` WHERE `id`=?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                res = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    //insert config


    public static void main(String[] args) {
//check config id exist
        ConfigDAO configDAO = new ConfigDAO();
        System.out.println(configDAO.checkConfigId(1));

    }
}
