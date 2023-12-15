package context;

import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBContext {

    private static final HikariDataSource dataSource;
    static Jdbi jdbi;

    static {
        // 1 Load configs.properties
        Properties prop = new Properties();
        try {
            prop.load(DBContext.class.getClassLoader().getResourceAsStream("configs.properties"));

        }catch (IOException e){
            e.printStackTrace();
        }


        String serverName = prop.getProperty("serverName");
        System.out.println(serverName);
        String dbName = prop.getProperty("dbMart");
        String portNumber = prop.getProperty("portNumber");
        String instance = prop.getProperty("instance");
        String userID = prop.getProperty("userID");
        String password = prop.getProperty("password");
        System.out.println(password);

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

    //2 Connect dbmart.db
    public static Jdbi me() {
        if (jdbi == null) {
            jdbi = Jdbi.create(dataSource);
        }
        return jdbi;
    }

    private DBContext() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getConnection());
    }
}