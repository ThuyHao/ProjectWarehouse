package com.example.dw_huy.DAO.DBMart;

import com.example.dw_huy.DAO.DBNew.Game_newsDAO;
import com.example.dw_huy.beans.DBNew.game_newsFact;
import com.example.dw_huy.db.DBMart.DBMartConnect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Game_newsDAOMart {
    private final DBMartConnect dbConnect;
    private static final Logger logger = LogManager.getLogger(Game_newsDAOMart.class);

    public Game_newsDAOMart() {
        this.dbConnect = DBMartConnect.getInstall();
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
            String sql = "INSERT INTO `game_newsfact` (\n" +
                    "  `title`, `author_id`, `description`, `url`, `image`, `content`, `source`,\n" +
                    "  `category_id`, `created_at`, `updated_at`, `created_by`, `updated_by`\n" +
                    ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)\n" +
                    "ON DUPLICATE KEY UPDATE\n" +
                    "  `title` = VALUES(`title`),\n" +
                    "  `author_id` = VALUES(`author_id`),\n" +
                    "  `description` = VALUES(`description`),\n" +
                    "  `url` = VALUES(`url`),\n" +
                    "  `image` = VALUES(`image`),\n" +
                    "  `content` = VALUES(`content`),\n" +
                    "  `source` = VALUES(`source`),\n" +
                    "  `category_id` = VALUES(`category_id`),\n" +
                    "  `updated_at` = NOW(),\n" +
                    "  `updated_by` = VALUES(`updated_by`)";
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
            logger.error("Error insert game news: " + e);
            return "EI";
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

    //get game news data that day of created_at or updated_at is equal current day
    public List<game_newsFact> getNewsDataTimeUp() {
        List<game_newsFact> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM `game_newsfact` WHERE `isDelete` = 0 AND DATE(`created_at`) = CURDATE() OR `isDelete` = 0 AND DATE(`updated_at`) = CURDATE();";
            saveGameNewsList(list, sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private void saveGameNewsList(List<game_newsFact> list, String sql) throws SQLException {
        Game_newsDAO.doQuery(list, dbConnect.get(sql), sql);
    }

    //detele all data
    public void deleteAllData() {
        try {
            String sql = "DELETE FROM `game_newsfact`";
            PreparedStatement statement = dbConnect.get(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Game_newsDAOMart game_newsDAOMart = new Game_newsDAOMart();
        List<game_newsFact> list = game_newsDAOMart.getAllNews();
        for (game_newsFact item : list) {
            System.out.println(item.getId() + " " + item.getTitle() + " " + item.getAuthor_id() + " " + item.getDescription() + " " + item.getUrl() + " " + item.getImage() + " " + item.getContent() + " " + item.getCategory_id() + " " + item.getSource() + " " + item.getCreated_at() + " " + item.getUpdated_at() + " " + item.getCreated_by() + " " + item.getUpdated_by());
        }
    }
}
