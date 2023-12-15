package com.example.dw_huy.DAO;

import com.example.dw_huy.beans.DBController.configs;
import com.example.dw_huy.db.DBController.DBControllerConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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

        //get all id and format of config table and store in map interger, string
    public Map<Integer, String> loadConfigs() {
        Map<Integer, String> configMap = new java.util.HashMap<>();
        try {
            String sql = "SELECT `id`, `format` FROM `configs`";
            PreparedStatement statement = dbConnect.get(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                configMap.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return configMap;
    }
    public String getValueById(Map<Integer, String> configMap, int id) {
        return configMap.get(id);
    }


    public static void main(String[] args) {
//check config id exist
        ConfigDAO configDAO = new ConfigDAO();
        System.out.println(configDAO.checkConfigId(1));

    }
}
