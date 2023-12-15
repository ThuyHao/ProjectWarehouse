package dao;

import context.DBConnect;
import model.DetailNewAggregate;
import model.HomeAggregate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMartDAO {


    //lấy tất cả tin có trong db để hiển thị lên danh sách tin ở trang home
    public List<HomeAggregate> getAllStagingData() {
        List<HomeAggregate> homeAggregateList = new ArrayList<>();

        try (Connection connection = DBConnect.getConnection()) {
            String sql = "SELECT id, name_category, title, image, description, name_author, day_up FROM homeaggregate ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    HomeAggregate homeAggregate = new HomeAggregate();
                    homeAggregate.setId(resultSet.getLong("id"));
                    homeAggregate.setNameCategory(resultSet.getString("name_category"));
                    homeAggregate.setTitle(resultSet.getString("title"));
                    homeAggregate.setImage(resultSet.getString("image"));
                    homeAggregate.setDescription(resultSet.getString("description"));
                    homeAggregate.setNameAuthor(resultSet.getString("name_author"));
                    homeAggregate.setDayUp(resultSet.getTimestamp("day_up"));
                    homeAggregateList.add(homeAggregate);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(homeAggregateList);
            return homeAggregateList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //lấy ra 2 tin tức nằm cuối cùng để hiển thị đầu trang home
    public List<HomeAggregate> getProductWithMaxId() {
        List<HomeAggregate> homeWithMaxId = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection()) {
            String sql = "SELECT id, name_category, title, image, description, name_author, day_up FROM homeaggregate ORDER BY id DESC LIMIT 2";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    HomeAggregate homeAggregate = new HomeAggregate();
                    homeAggregate.setId(resultSet.getLong("id"));
                    homeAggregate.setNameCategory(resultSet.getString("name_category"));
                    homeAggregate.setTitle(resultSet.getString("title"));
                    homeAggregate.setImage(resultSet.getString("image"));
                    homeAggregate.setDescription(resultSet.getString("description"));
                    homeAggregate.setNameAuthor(resultSet.getString("name_author"));
                    homeAggregate.setDayUp(resultSet.getTimestamp("day_up"));
                    homeWithMaxId.add(homeAggregate);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception as needed
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return homeWithMaxId;
    }



        //lấy ra nội dung tin tức theo id
        public DetailNewAggregate getNewsById(String id) {
            String query = "SELECT id, name_category, title, image, description, name_author, day_up, content  FROM detailnewaggregate WHERE id=?";

            try (Connection connection = DBConnect.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        DetailNewAggregate detailNewAggregate = new DetailNewAggregate();
                        detailNewAggregate.setId(resultSet.getLong("id"));
                        detailNewAggregate.setNameCategory(resultSet.getString("name_category"));
                        detailNewAggregate.setTitle(resultSet.getString("title"));
                        detailNewAggregate.setImage(resultSet.getString("image"));
                        detailNewAggregate.setDescription(resultSet.getString("description"));
                        detailNewAggregate.setNameAuthor(resultSet.getString("name_author"));
                        detailNewAggregate.setDayUp(resultSet.getTimestamp("day_up"));
                        detailNewAggregate.setContent(resultSet.getString("content"));

                        return detailNewAggregate;
                    } else {
                        return null;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }


    //lấy ra 3 tin tức nằm cuối cùng để hiển thị lên danh sách tin tức mới nhất
    public List<HomeAggregate> getTop3Product() {
        List<HomeAggregate> top3Product = new ArrayList<>();
        try (Connection connection = DBConnect.getConnection()) {
            String sql = "SELECT id, name_category, title, image, description, name_author, day_up FROM homeaggregate ORDER BY id DESC LIMIT 3";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    HomeAggregate homeAggregate = new HomeAggregate();
                    homeAggregate.setId(resultSet.getLong("id"));
                    homeAggregate.setNameCategory(resultSet.getString("name_category"));
                    homeAggregate.setTitle(resultSet.getString("title"));
                    homeAggregate.setImage(resultSet.getString("image"));
                    homeAggregate.setDescription(resultSet.getString("description"));
                    homeAggregate.setNameAuthor(resultSet.getString("name_author"));
                    homeAggregate.setDayUp(resultSet.getTimestamp("day_up"));
                    top3Product.add(homeAggregate);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return top3Product;
    }

    //lấy ra 4 tin tức nằm cuối cùng để hiển thị lên danh sách tin tức gần đây
    public List<HomeAggregate> getTop4Product() {
        List<HomeAggregate> top4Product = new ArrayList<>();

        try (Connection connection = DBConnect.getConnection()) {
            String sql = "SELECT id, name_category, title, image, description, name_author, day_up  FROM homeaggregate ORDER BY id DESC LIMIT 4";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    HomeAggregate homeAggregate = new HomeAggregate();
                    homeAggregate.setId(resultSet.getLong("id"));
                    homeAggregate.setNameCategory(resultSet.getString("name_category"));
                    homeAggregate.setTitle(resultSet.getString("title"));
                    homeAggregate.setImage(resultSet.getString("image"));
                    homeAggregate.setDescription(resultSet.getString("description"));
                    homeAggregate.setNameAuthor(resultSet.getString("name_author"));
                    homeAggregate.setDayUp(resultSet.getTimestamp("day_up"));

                    top4Product.add(homeAggregate);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return top4Product;
    }

}

