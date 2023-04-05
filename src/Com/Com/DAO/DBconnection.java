package Com.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBconnection {

    private static Connection connection;

    public DBconnection(){
        tryConnectDB();
    }

    public void tryConnectDB(){
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String userName = "sqlUser";
            String password = "Passw0rd!";
            String url = "jdbc:mysql://localhost:3306/client_schedule";
            connection=DriverManager.getConnection(url, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

}
