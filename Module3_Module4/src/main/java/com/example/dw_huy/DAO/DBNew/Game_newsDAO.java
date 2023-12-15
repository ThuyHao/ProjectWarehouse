package com.example.dw_huy.DAO.DBNew;

import com.example.dw_huy.beans.DBNew.game_newsFact;
import com.example.dw_huy.db.DBNew.DBConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Game_newsDAO {
    private final DBConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(Game_newsDAO.class);
    public Game_newsDAO() {
        this.dbConnect = DBConnect.getInstall();
    }

    //get all game news
    public List<game_newsFact> getAllNews() {
        List<game_newsFact> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `game_newsfact`";
            saveGameNewsList(list, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    //insert game news
    public String insertNews(game_newsFact game_newsFact) {
        String res = "";
        try {
            String sql = "INSERT INTO `game_newsfact`(`title`, `author_id`, `description`, `url`, `image`, `content`, `source`,`category_id`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, game_newsFact.getTitle());
            statement.setInt(2, game_newsFact.getAuthor_id());
            statement.setString(3, game_newsFact.getDescription());
            statement.setString(4, game_newsFact.getUrl());
            statement.setString(5, game_newsFact.getImage());
            statement.setString(6, game_newsFact.getContent());
            statement.setString(7, game_newsFact.getSource());
            statement.setInt(8, game_newsFact.getCategory_id());
            statement.setTimestamp(9, game_newsFact.getCreated_at());
            statement.setTimestamp(10, game_newsFact.getUpdated_at());
            statement.setString(11, game_newsFact.getCreated_by());
            statement.setString(12, game_newsFact.getUpdated_by());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert game news success");
                res = "SC";
            } else {
                System.out.println("Insert game news fail");
                res = "EI";
            }
        } catch (SQLException e) {
           res="EI";
           logger.error("Error insert game news: " + e.getMessage());
        }
        return res;
    }

    //update game news data by author id and category id
    public void updateNews(game_newsFact game_newsFact) {
        try {
            String sql = "UPDATE `game_newsfact` SET `title` = '?', `description` = '?', `url` = '?', `image` = '?', `content` = '?', `name_source` = '?', `updated_at` = current_timestamp(), `updated_by` = '?' WHERE `game_newsfact`.`author_id` = ? AND `game_newsfact`.`category_id` = ?;";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setString(1, game_newsFact.getTitle());
            statement.setString(2, game_newsFact.getDescription());
            statement.setString(3, game_newsFact.getUrl());
            statement.setString(4, game_newsFact.getImage());
            statement.setString(5, game_newsFact.getContent());
            statement.setString(6, game_newsFact.getSource());
            statement.setString(7, game_newsFact.getUpdated_by());
            statement.setInt(8, game_newsFact.getAuthor_id());
            statement.setInt(9, game_newsFact.getCategory_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //disable game news by id
    public void disableNews(int id) {
        try {
            String sql = "UPDATE `game_newsfact` SET `isDelete` = '1' WHERE `game_newsfact`.`id` = ?";
            PreparedStatement statement = dbConnect.get(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //get game news data that day of created_at or updated_at is equal current day and delete is 0
    public List<game_newsFact> getDataTimeUp() {
        List<game_newsFact> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `game_newsfact` WHERE `isDelete` = 0 AND DATE(`created_at`) = CURDATE() OR `isDelete` = 0 AND DATE(`updated_at`) = CURDATE();" ;
            saveGameNewsList(list, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    private void saveGameNewsList(List<game_newsFact> list, String sql) throws SQLException {
        doQuery(list, dbConnect.get(sql), sql);
    }

    public static void doQuery(List<game_newsFact> list, PreparedStatement preparedStatement, String sql) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            game_newsFact item = new game_newsFact();
            item.setId(rs.getInt(1));
            item.setTitle(rs.getString(2));
            item.setAuthor_id(rs.getInt(3));
            item.setDescription(rs.getString(4));
            item.setUrl(rs.getString(5));
            item.setImage(rs.getString(6));
            item.setContent(rs.getString(7));
            item.setSource(rs.getString(8));
            item.setCategory_id(rs.getInt(9));
            item.setIsDelete(rs.getInt(10));
            item.setCreated_at(rs.getTimestamp(11));
            item.setUpdated_at(rs.getTimestamp(12));
            item.setCreated_by(rs.getString(13));
            item.setUpdated_by(rs.getString(14));
            list.add(item);
        }
    }

    public static void main(String[] args) {
        Game_newsDAO game_newsDAO = new Game_newsDAO();
        List<game_newsFact> list = game_newsDAO.getDataTimeUp();
        for (game_newsFact item : list) {
            System.out.println(item.getAuthor_id() +" - " + item.getCategory_id());
        }
    }
}
