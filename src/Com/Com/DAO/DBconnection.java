package Com.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBconnection {

    private static Connection connection;
    private String userName="sqlUser";
    private String password="Passw0rd!";
    private String driver="com.mysql.cj.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/client_schedule";

    public DBconnection(){
        tryConnectDB();
    }

    public boolean tryConnectDB(){
        try {
            Class.forName(driver);
            connection=DriverManager.getConnection(url,userName,password);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection() throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        DBconnection.connection = connection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
