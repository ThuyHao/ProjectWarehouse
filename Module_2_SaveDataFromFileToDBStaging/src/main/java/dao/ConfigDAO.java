package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static context.DBContext.getConnection;

public class ConfigDAO {
    public static String getEmailFromDB() {
        String email = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  format FROM configs WHERE id = 33")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    email = resultSet.getString("format");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }

    public static String getPasswordFromDB() {
        String password = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  format FROM configs WHERE id = 34")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    password = resultSet.getString("format");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return password;
    }
    public static String getEmailReceiveFromDB() {
        String emailReceive = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  format FROM configs WHERE id = 35")) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    emailReceive = resultSet.getString("format");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailReceive;
    }
}
