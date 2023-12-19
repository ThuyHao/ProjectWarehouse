package com.example.dw_huy.DAO.DBController;

import com.example.dw_huy.beans.DBStaging.staging;
import com.example.dw_huy.db.DBStaging.DBStagingConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StagingDAO {
    private final DBStagingConnect dbConnect;

    public StagingDAO() {
        this.dbConnect = DBStagingConnect.getInstall();
    }

    //get staging data
    public List<staging> getStagingData() {
        List<staging> stagingList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `staging`";
            getList(stagingList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stagingList;
    }

    //check staging table if status ="sc" limit 1
    public boolean checkStagingData() {
        boolean check = false;
        try {
            String sql = "SELECT * FROM `staging` WHERE `status` = 'SC' LIMIT 1";
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

    // get all data that timeup just day is equal current day
    public List<staging> getStagingDataTimeUp() {
        List<staging> stagingList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `staging` WHERE DATE(`timeUp`) = CURDATE() and `status`='SC'";
            getList(stagingList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return stagingList;
    }


    private void getList(List<staging> stagingList, String sql) throws SQLException {
        PreparedStatement statement = dbConnect.get(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            staging staging = new staging();
            staging.setId(rs.getInt(1));
            staging.setTitle(rs.getString(2));
            staging.setDescription(rs.getString(3));
            staging.setAuthor_name(rs.getString(4));
            staging.setTimeUp(rs.getTimestamp(5));
            staging.setUrl(rs.getString(6));
            staging.setCategory_name(rs.getString(7));
            staging.setImage(rs.getString(8));
            staging.setContent(rs.getString(9));
            staging.setSource_name(rs.getString(10));
            staging.setStatus(rs.getString(11));
            staging.setCreated_at(rs.getTimestamp(12));

            stagingList.add(staging);

        }
    }

    public static void main(String[] args) {
        //get stating current day data
        StagingDAO stagingDAO = new StagingDAO();
        List<staging> stagingList = stagingDAO.getStagingDataTimeUp();
        for (staging staging : stagingList) {
            System.out.println(staging.getTitle() + " " + staging.getTimeUp());
        }
    }
}
