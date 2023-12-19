package com.example.dw_huy.DAO.DBNew;

import com.example.dw_huy.DAO.DBMart.homeAggregateDAOMart;
import com.example.dw_huy.beans.DBNew.homeAggregate;
import com.example.dw_huy.db.DBNew.DBConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class homeAggregateDAO {
    private final DBConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(homeAggregateDAO.class);

    public homeAggregateDAO() {
        this.dbConnect = DBConnect.getInstall();
    }

    public List<homeAggregate> getAllData() {
        List<homeAggregate> categoriesDimList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `homeaggregate`";
            saveHomeList(categoriesDimList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoriesDimList;
    }

    //insert home aggregate
    public String insertHomeAggregate(homeAggregate homeAggregate) {
        try {
            String sql = "INSERT INTO `homeaggregate`(`name_category`, `title`, `image`, `description`, `name_author`, `day_up`) VALUES (?,?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, homeAggregate.getName_category());
            statement.setString(2, homeAggregate.getTitle());
            statement.setString(3, homeAggregate.getImage());
            statement.setString(4, homeAggregate.getDescription());
            statement.setString(5, homeAggregate.getName_author());
            statement.setTimestamp(6, homeAggregate.getDay_up());

        } catch (SQLException e) {
            logger.error("Insert home aggregate fail" + e);
            return "EI";
        }
        return "SC";
    }

    //get data that day of dayup is equal current day
    public List<homeAggregate> getDataTimeUp() {
        List<homeAggregate> homeAggregateList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `homeaggregate` WHERE DATE(`day_up`) = CURDATE()";
            saveHomeList(homeAggregateList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return homeAggregateList;
    }

    private void saveHomeList(List<homeAggregate> homeAggregateList, String sql) throws SQLException {
        PreparedStatement statement = dbConnect.get(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            homeAggregate homeAggregate = new homeAggregate();
            homeAggregate.setId(rs.getInt(1));
            homeAggregate.setName_category(rs.getString(2));
            homeAggregate.setTitle(rs.getString(3));
            homeAggregate.setImage(rs.getString(4));
            homeAggregate.setDescription(rs.getString(5));
            homeAggregate.setName_author(rs.getString(6));
            homeAggregate.setDay_up(rs.getTimestamp(7));

            homeAggregateList.add(homeAggregate);
        }
    }


    public static void main(String[] args) {
        //insert data
        homeAggregateDAO homeAggregateDAO = new homeAggregateDAO();
        homeAggregate homeAggregate = new homeAggregate();
        homeAggregate.setName_category("Game");
        homeAggregate.setTitle("Game");
        homeAggregate.setImage("Game");
        homeAggregate.setDescription("Game");
        homeAggregate.setName_author("Game");
        homeAggregate.setDay_up(new java.sql.Timestamp(System.currentTimeMillis()));
        homeAggregateDAO.insertHomeAggregate(homeAggregate);

    }


}
