package context;


import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBContextStaging {

    private static final HikariDataSource dataSource;
    static Jdbi jdbi;

    static {
        // 1 Load configs.properties
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("configs.properties")) {
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String serverName = prop.getProperty("serverName");
        System.out.println(serverName);
        String dbName = prop.getProperty("dbStaging");
        String portNumber = prop.getProperty("portNumber");
        String instance = prop.getProperty("instance");
        String userID = prop.getProperty("userID");
        String password = prop.getProperty("password");

        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
        if (instance != null && !instance.trim().isEmpty()) {
            url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + instance + "/" + dbName;
        }

        dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(userID);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(200);
        dataSource.setMinimumIdle(30);
    }

    //2 Connect dbcontroller.db
    public static Jdbi me() {
        if (jdbi == null) {
            jdbi = Jdbi.create(dataSource);
        }
        return jdbi;
    }

    private DBContextStaging() {
    }


    public static Connection getConnectionDBStaging() throws SQLException {
        return dataSource.getConnection();
    }




    public static void main(String[] args) throws SQLException {
        System.out.println(getConnectionDBStaging());
    }
}