package com.example.dw_huy.DAO.DBMart;

import com.example.dw_huy.beans.DBNew.homeAggregate;
import com.example.dw_huy.db.DBMart.DBMartConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class homeAggregateDAOMart {
    private final DBMartConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(homeAggregateDAOMart.class);

    public homeAggregateDAOMart() {
        this.dbConnect = DBMartConnect.getInstall();
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
        String res = "";
        try {
            String sql;
            sql = "INSERT INTO dbmart.homeaggregate\n" +
                    "    (`name_category`, `title`, `image`, `description`, `name_author`, `day_up`)\n" +
                    "VALUES\n" +
                    "    (?, ?, ?, ?, ?, ?)\n" +
                    "ON DUPLICATE KEY UPDATE\n" +
                    "    `name_category` = VALUES(`name_category`),\n" +
                    "    `title` = VALUES(`title`),\n" +
                    "    `image` = VALUES(`image`),\n" +
                    "    `image` = VALUES(`image`),\n" +
                    "    `description` = VALUES(`description`),\n" +
                    "    `name_author` = VALUES(`name_author`),\n" +
                    "    `day_up` = VALUES(`day_up`);";
            doSQL(homeAggregate, dbConnect.get(sql), sql);
            res = "SC";
            System.out.println("Insert home aggregate success");
        } catch (SQLException e) {
            res = "EI";
            logger.error("Insert home aggregate fail");
        }
        return res;
    }

    public static void doSQL(homeAggregate homeAggregate, PreparedStatement preparedStatement, String sql) throws SQLException {
        preparedStatement.setString(1, homeAggregate.getName_category());
        preparedStatement.setString(2, homeAggregate.getTitle());
        preparedStatement.setString(3, homeAggregate.getImage());
        preparedStatement.setString(4, homeAggregate.getDescription());
        preparedStatement.setString(5, homeAggregate.getName_author());
        preparedStatement.setTimestamp(6, homeAggregate.getDay_up());

        preparedStatement.executeUpdate();
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
        homeAggregateDAOMart homeAggregateDAOMart = new homeAggregateDAOMart();
        homeAggregate homeAggregate = new homeAggregate();
        homeAggregate.setName_category("Game");
        homeAggregate.setTitle("Game");
        homeAggregate.setImage("Game");
        homeAggregate.setDescription("Game");
        homeAggregate.setName_author("Game");
        homeAggregate.setDay_up(new java.sql.Timestamp(System.currentTimeMillis()));
        homeAggregateDAOMart.insertHomeAggregate(homeAggregate);

    }


    public void deleteAllData() {
        try {
            String sql = "TRUNCATE homeaggregate";
            PreparedStatement statement = dbConnect.get(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
