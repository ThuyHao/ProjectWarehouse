package com.example.dw_huy.DAO.DBNew;

import com.example.dw_huy.beans.DBNew.authorsDim;
import com.example.dw_huy.db.DBMart.DBMartConnect;
import com.example.dw_huy.db.DBNew.DBConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    private final DBConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(AuthorDAO.class);

    public AuthorDAO() {
        this.dbConnect = DBConnect.getInstall();
    }

    // Hàm lấy tất cả các tác giả từ bảng authorsdim
    public List<authorsDim> getAllAuthors() {
        List<authorsDim> authors = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `authorsdim`";
            getAuthorDAO(authors, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return authors;
    }

    //insert authors
    public String insertAuthor(authorsDim author) {
        String res = "";
        try {
            String sql = "INSERT INTO `authorsdim`(`name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES (?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, author.getName());
            statement.setTimestamp(2, author.getCreated_at());
            statement.setTimestamp(3, author.getUpdated_at());
            statement.setString(4, author.getCreated_by());
            statement.setString(5, author.getUpdated_by());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert author success");
                res = "SC";
            } else {
                System.out.println("Insert author fail");
                res = "EI";
            }
        } catch (SQLException e) {
            res = "EI";
            logger.error("Error insert author: " + e.getMessage());
        }
        return res;
    }

    //get author id and insert into fact table
    public int getAuthorId(String authorName) {
        int authorId = 0;
        try {
            String sql = "SELECT id FROM `authorsdim` WHERE name = ?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, authorName);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                authorId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorId;
    }

    //get author name by id
    public String getAuthorName(int authorId) {
        String authorName = "";
        try {
            String sql = "SELECT name FROM `authorsdim` WHERE id = ? LIMIT 1";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, authorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                authorName = rs.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorName;
    }

    //check author exist by name
    public boolean checkAuthorExist(String authorName) {
        boolean check = false;
        try {
            String sql = "SELECT * FROM `authorsdim` WHERE name = ?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, authorName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    //list data the day of created_at or day of updated_at is equal current day
    public List<authorsDim> getAuthorsDataTimeUp() {
        List<authorsDim> authorsDimList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `authorsdim` WHERE DATE(`created_at`) = CURDATE() OR DATE(`updated_at`) = CURDATE()";
            getAuthorDAO(authorsDimList, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorsDimList;
    }

    //truncate table
    public void deleteAllData() {
        try {
            String sql = "TRUNCATE authorsdim";
            PreparedStatement statement = dbConnect.get(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAuthorDAO(List<authorsDim> authorsDimList, String sql) throws SQLException {
        PreparedStatement statement = dbConnect.get(sql);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            authorsDim authorsDim = new authorsDim();
            authorsDim.setId(rs.getInt(1));
            authorsDim.setName(rs.getString(2));
            authorsDim.setCreated_at(rs.getTimestamp(3));
            authorsDim.setUpdated_at(rs.getTimestamp(4));
            authorsDim.setCreated_by(rs.getString(5));
            authorsDim.setUpdated_by(rs.getString(6));

            authorsDimList.add(authorsDim);
        }
    }

    public static void main(String[] args) {
        AuthorDAO authorDAO = new AuthorDAO();
        //get author id
        boolean authorId = authorDAO.checkAuthorExist("riot Phlox");
        System.out.println(authorId);
    }

}
