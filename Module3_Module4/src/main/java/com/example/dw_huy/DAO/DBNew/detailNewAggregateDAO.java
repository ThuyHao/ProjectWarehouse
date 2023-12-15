package com.example.dw_huy.DAO.DBNew;


import com.example.dw_huy.beans.DBNew.categoriesDim;
import com.example.dw_huy.beans.DBNew.detailNewAggregate;
import com.example.dw_huy.beans.DBNew.homeAggregate;
import com.example.dw_huy.db.DBNew.DBConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class detailNewAggregateDAO {
    private final DBConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(detailNewAggregateDAO.class);

    public detailNewAggregateDAO() {
        this.dbConnect = DBConnect.getInstall();
    }

    public List<detailNewAggregate> getAllData() {
        List<detailNewAggregate> categoriesDimList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `detailnewaggregate`";
            savedetail(categoriesDimList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoriesDimList;
    }

    //insert detail news aggregate
    public String insertDetailNewAggregate(detailNewAggregate detailNewAggregate) {
        String res = "";
        try {
            String sql = "INSERT INTO `detailnewaggregate`(`name_category`, `title`, `image`, `description`, `name_author`, `day_up`, `content`) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, detailNewAggregate.getName_category());
            statement.setString(2, detailNewAggregate.getTitle());
            statement.setString(3, detailNewAggregate.getImage());
            statement.setString(4, detailNewAggregate.getDescription());
            statement.setString(5, detailNewAggregate.getName_author());
            statement.setTimestamp(6, detailNewAggregate.getDay_up());
            statement.setString(7, detailNewAggregate.getContent());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert aggregate success");
                res = "SC";
            } else {
                System.out.println("Insert aggregate fail");
                res = "EI";
            }
        } catch (SQLException e) {
            res = "EI";
            logger.error("Error insert aggregate: " + e.getMessage());
        }
        return res;
    }

    //get data that day of dayup is equal current day
    public List<detailNewAggregate> getDataTimeUp() {
        List<detailNewAggregate> detailNewAggregateList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `detailnewaggregate` WHERE DATE(`day_up`) = CURDATE()";
            savedetail(detailNewAggregateList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return detailNewAggregateList;
    }

    private void savedetail(List<detailNewAggregate> detailNewAggregateList, String sql) throws SQLException {
        PreparedStatement statement = dbConnect.get(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            detailNewAggregate item = new detailNewAggregate();
            item.setId(rs.getInt(1));
            item.setName_category(rs.getString(2));
            item.setTitle(rs.getString(3));
            item.setImage(rs.getString(4));
            item.setDescription(rs.getString(5));
            item.setName_author(rs.getString(6));
            item.setDay_up(rs.getTimestamp(7));
            item.setContent(rs.getString(8));

            detailNewAggregateList.add(item);
        }
    }


}

