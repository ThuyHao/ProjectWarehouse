package com.example.dw_huy.DAO.DBController;

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

    //get id by url source
    public int getIdByUrlSource(String sourceName) {
        int id = 0;
        try {
            String sql = "SELECT id FROM `configs` WHERE name LIKE ? LIMIT 1";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, "url Source%" + sourceName + "%");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }


    public static void main(String[] args) {
    //test load config
        ConfigDAO configDAO = new ConfigDAO();
        boolean res = configDAO.checkConfigId(37);
        System.out.println(res);

    }
}
