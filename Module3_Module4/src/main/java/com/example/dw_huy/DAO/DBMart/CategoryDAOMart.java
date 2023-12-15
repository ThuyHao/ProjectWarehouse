package com.example.dw_huy.DAO.DBMart;


import com.example.dw_huy.beans.DBNew.categoriesDim;
import com.example.dw_huy.db.DBMart.DBMartConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOMart {
    private final DBMartConnect dbConnect;
    public CategoryDAOMart() {
        this.dbConnect = DBMartConnect.getInstall();
    }
    private static final Logger logger = LogManager.getLogger(CategoryDAOMart.class);
    public List<categoriesDim> getAllAuthors() {
        List<categoriesDim> categoriesDimList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `categoriesdim`";
            getCategoryList(categoriesDimList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoriesDimList;
    }

    //insert category
    public String insertCategory(categoriesDim categoriesDim) {
        String res = "";
        try {
            String sql = "INSERT INTO dbmart.categoriesdim\n" +
                    "    (`id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`)\n" +
                    "VALUES\n" +
                    "    (?, ?, ?, ?, ?, ?)\n" +
                    "ON DUPLICATE KEY UPDATE\n" +
                    "    `name` = VALUES(`name`),\n" +
                    "    `updated_at` = VALUES(`updated_at`),\n" +
                    "    `updated_by` = VALUES(`updated_by`);";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, categoriesDim.getId());
            statement.setString(2, categoriesDim.getName());
            statement.setTimestamp(3, categoriesDim.getCreated_at());
            statement.setTimestamp(4, categoriesDim.getUpdated_at());
            statement.setString(5, categoriesDim.getCreated_by());
            statement.setString(6, categoriesDim.getUpdated_by());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert category success");
                res = "SC";
            } else {
                System.out.println("Insert category fail");
                res = "EI";
            }
        } catch (SQLException e) {
            logger.error("Error insert category: " + e);
            return "EI";
        }
        return res;
    }

    //get category id and insert into fact table
    public int getCategoryId(String categoryName) {
        try {
            String sql = "SELECT id FROM `categoriesdim` WHERE name = ?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, categoryName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Error get category id: " + e);
        }
        return -1;
    }

    //check category exist by name
    public boolean checkCategoryExist(String categoryName) {
        try {
            String sql = "SELECT * FROM `categoriesdim` WHERE name = ?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, categoryName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error check category exist: " + e);
        }
        return false;
    }

    //list category that day created_at or updated_at is equal current day
    public List<categoriesDim> getCategoryDataTimeUp() {
        List<categoriesDim> categoriesDimList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `categoriesdim` WHERE DATE(`created_at`) = CURDATE() OR DATE(`updated_at`) = CURDATE()";
            getCategoryList(categoriesDimList, sql);
        } catch (SQLException e) {
            logger.error("Error get category data time up: " + e);
        }

        return categoriesDimList;
    }

    private void getCategoryList(List<categoriesDim> categoriesDimList, String sql) throws SQLException {
        PreparedStatement statement = dbConnect.get(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            categoriesDim categoriesDim = new categoriesDim();
            categoriesDim.setId(rs.getInt(1));
            categoriesDim.setName(rs.getString(2));
            categoriesDim.setCreated_at(rs.getTimestamp(3));
            categoriesDim.setUpdated_at(rs.getTimestamp(4));
            categoriesDim.setCreated_by(rs.getString(5));
            categoriesDim.setUpdated_by(rs.getString(6));

            categoriesDimList.add(categoriesDim);
        }
    }

    public static void main(String[] args) {
        //test error insert category
        CategoryDAOMart categoryDAOMart = new CategoryDAOMart();
        categoriesDim categoriesDim = new categoriesDim();
        categoriesDim.setName("test");
        categoriesDim.setCreated_at(new Timestamp(System.currentTimeMillis()));
        categoriesDim.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        categoriesDim.setCreated_by("admin");
        categoriesDim.setUpdated_by("admin");
        String res = categoryDAOMart.insertCategory(categoriesDim);
        System.out.println(res);

    }

    public void deleteAllData() {
        try {
            String sql = "DELETE FROM categoriesdim";
            PreparedStatement statement = dbConnect.get(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

