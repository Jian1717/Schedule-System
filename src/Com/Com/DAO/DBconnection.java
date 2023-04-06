package Com.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**This class define characteristics of a DBconnection and its behaviors. */
public class DBconnection {

    private static Connection connection;
    /**This is default constructor for DBconnection.
     * It creates user DBconnection and call tryConnectDB upon creation.*/
    public DBconnection(){
        tryConnectDB();
    }
    /**This method will connect and store Connection to SQL database. */
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
    /**This method will close Connection to SQL database. */
    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**This method will return Connection to SQL database.
     *@return Connection to SQL database*/
    public static Connection getConnection() {
        return connection;
    }

}
