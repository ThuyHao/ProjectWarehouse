package com.example.dw_huy.DAO.DBMart;

import com.example.dw_huy.beans.DBNew.authorsDim;
import com.example.dw_huy.db.DBMart.DBMartConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOMart {
    private final DBMartConnect dbConnect;

    public AuthorDAOMart() {
        this.dbConnect = DBMartConnect.getInstall();
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
            String sql = "INSERT INTO dbmart.authorsdim\n" +
                    "    (`id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`)\n" +
                    "VALUES\n" +
                    "    (?, ?, ?, ?, ?, ?)\n" +
                    "ON DUPLICATE KEY UPDATE\n" +
                    "    `name` = VALUES(`name`),\n" +
                    "    `updated_at` = VALUES(`updated_at`),\n" +
                    "    `updated_by` = VALUES(`updated_by`);";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, author.getId());
            statement.setString(2, author.getName());
            statement.setTimestamp(3, author.getCreated_at());
            statement.setTimestamp(4, author.getUpdated_at());
            statement.setString(5, author.getCreated_by());
            statement.setString(6, author.getUpdated_by());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert author success");
                res = "SC";
            } else {
                System.out.println("Insert author fail");
                res = "EI";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "EI";
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
    //truncate table
    public void deleteAllData() {
        try {
            String sql = "DELETE FROM authorsdim";
            PreparedStatement statement = dbConnect.get(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //test insert
        AuthorDAOMart authorDAOMart = new AuthorDAOMart();
        authorsDim authorsDim = new authorsDim();
        authorsDim.setId(7);
        authorsDim.setName("");
        authorsDim.setCreated_at(new java.sql.Timestamp(System.currentTimeMillis()));
        authorsDim.setUpdated_at(new java.sql.Timestamp(System.currentTimeMillis()));
        authorsDim.setCreated_by("admin");
        authorsDim.setUpdated_by("HUy");
        authorDAOMart.insertAuthor(authorsDim);

    }

}
