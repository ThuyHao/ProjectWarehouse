package com.example.dw_huy.DAO.DBNew;


import com.example.dw_huy.beans.DBNew.categoriesDim;
import com.example.dw_huy.db.DBNew.DBConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private final DBConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(CategoryDAO.class);
    public CategoryDAO() {
        this.dbConnect = DBConnect.getInstall();
    }

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
            String sql = "INSERT INTO `categoriesdim`(`name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, categoriesDim.getName());
            statement.setTimestamp(2, categoriesDim.getCreated_at());
            statement.setTimestamp(3, categoriesDim.getUpdated_at());
            statement.setString(4, categoriesDim.getCreated_by());
            statement.setString(5, categoriesDim.getUpdated_by());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert category success");
                res = "SC";
            } else {
                System.out.println("Insert category fail");
                res = "EI";
            }
        } catch (SQLException e) {
            res="EI";
            logger.error("Error insert category: " + e.getMessage());
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
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
        //test day data
        CategoryDAO categoryDAO = new CategoryDAO();
        List<categoriesDim> categoriesDimList = categoryDAO.getCategoryDataTimeUp();
        for (categoriesDim item : categoriesDimList) {
            System.out.println(item.getId() + " " + item.getName() + " " + item.getCreated_at() + " " + item.getUpdated_at() + " " + item.getCreated_by() + " " + item.getUpdated_by());
        }

    }

    public String getCategoryName(int categoryId) {
        String categoryName = "";
        try {
            String sql = "SELECT name FROM `categoriesdim` WHERE id = ?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                categoryName = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryName;
    }

}

