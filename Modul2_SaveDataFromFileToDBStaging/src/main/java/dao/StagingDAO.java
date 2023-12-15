package dao;

import context.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static context.DBConnect.getConnectionDBStaging;

public class StagingDAO {


    Connection connectionStaging = DBConnect.getConnectionDBStaging();

    public StagingDAO() throws SQLException {
    }

    public static void truncateStagingTable(Connection connectionStaging) throws SQLException {
        try (Connection connection = getConnectionDBStaging();
             PreparedStatement truncateTableStatement = connection.prepareStatement("TRUNCATE TABLE staging")) {
            truncateTableStatement.executeUpdate();
        }
    }

    public static Timestamp convertToTimestamp(String timeString) {
        try {
            timeString = timeString.replaceAll("\"", "");

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date parsedDate = inputFormat.parse(timeString);
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Timestamp(System.currentTimeMillis());
        }
    }


}