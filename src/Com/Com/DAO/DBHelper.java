package Com.DAO;

import Com.Helper.TimeHelper;
import Com.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
/**This interface contain useful methods that handling SQL Database CRUD in this application. */
public interface DBHelper {
    /**This method insert an appointment into appointments table in the Database.
     * @param title appointment title
     * @param contact_ID  contact ID
     * @param customer_ID customer ID
     * @param description appointment description
     * @param end appointment end datetime
     * @param location appointment location
     * @param start appointment start datetime
     * @param type appointment type
     * @param user_ID user ID
     * @return 1 for successful insertion 0 for fail insertion */
    public static int insertAppointment(String title, String description, String type, String location, LocalDateTime start, LocalDateTime end, int user_ID, int customer_ID, int contact_ID) throws SQLException {
        String sql= "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, User_ID, Customer_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setString(1,title);
        sqlQueryStatement.setString(2,description);
        sqlQueryStatement.setString(3,location);
        sqlQueryStatement.setString(4,type);
        sqlQueryStatement.setTimestamp(5, TimeHelper.toUTCTimestamp(start));
        sqlQueryStatement.setTimestamp(6, TimeHelper.toUTCTimestamp(end));
        sqlQueryStatement.setInt(7,user_ID);
        sqlQueryStatement.setInt(8,customer_ID);
        sqlQueryStatement.setInt(9,contact_ID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method insert a customer into customers table in the Database.
     * @param name customer name
     * @param address customer address
     * @param divisionID FirstLevelDivision ID
     * @param phone customer phone number
     * @param postalCode customer postal code
     * @return 1 for successful insertion 0 for fail insertion */
    public static int insertCustomer(String name, String address,String postalCode,String phone,int divisionID) throws SQLException {
        String sql="INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setString(1,name);
        sqlQueryStatement.setString(2,address);
        sqlQueryStatement.setString(3,postalCode);
        sqlQueryStatement.setString(4,phone);
        sqlQueryStatement.setInt(5,divisionID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method update a customer in the customers table.
     * @param customerID customer ID
     * @param name customer name
     * @param address customer address
     * @param divisionID FirstLevelDivision ID
     * @param phone customer phone number
     * @param postalCode customer postal code
     * @return 1 for successful update 0 for fail update */
    public static int updateCustomer(String name, String address,String postalCode,String phone,int divisionID,int customerID) throws SQLException {
        String sql="UPDATE CUSTOMERS SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setString(1,name);
        sqlQueryStatement.setString(2,address);
        sqlQueryStatement.setString(3,postalCode);
        sqlQueryStatement.setString(4,phone);
        sqlQueryStatement.setInt(5,divisionID);
        sqlQueryStatement.setInt(6,customerID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method update an appointment in the appointments table.
     * @param appointmentID appointment ID
     * @param title appointment title
     * @param contact_ID  contact ID
     * @param customer_ID customer ID
     * @param description appointment description
     * @param end appointment end datetime
     * @param location appointment location
     * @param start appointment start datetime
     * @param type appointment type
     * @param user_ID user ID
     * @return 1 for successful update 0 for fail update */
    public static int updateAppointment(String title, String description, String type, String location, LocalDateTime start, LocalDateTime end, int user_ID, int customer_ID, int contact_ID,int appointmentID) throws SQLException {
        String sql= "UPDATE APPOINTMENTS SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, User_ID=?, Customer_ID=?, Contact_ID=? WHERE Appointment_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setString(1,title);
        sqlQueryStatement.setString(2,description);
        sqlQueryStatement.setString(3,location);
        sqlQueryStatement.setString(4,type);
        sqlQueryStatement.setTimestamp(5, TimeHelper.toUTCTimestamp(start));
        sqlQueryStatement.setTimestamp(6, TimeHelper.toUTCTimestamp(end));
        sqlQueryStatement.setInt(7,user_ID);
        sqlQueryStatement.setInt(8,customer_ID);
        sqlQueryStatement.setInt(9,contact_ID);
        sqlQueryStatement.setInt(10,appointmentID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method delete a customer in the customers table.
     * @param customerID customer ID
     * @return 1 for successful deletion 0 for fail deletion */
    public static int deleteCustomer(int customerID) throws SQLException{
        String sql="DELETE from CUSTOMERS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method delete an appointment in the appointments table.
     * @param appointmentID appointment ID
     * @return 1 for successful deletion 0 for fail deletion*/
    public static int deleteAppointment(int appointmentID) throws SQLException{
        String sql="DELETE from APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,appointmentID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method delete all the appointments that associated with selected customer in the appointments table.
     * @param customerID customer ID
     * @return number of successful deletion 0 for fail deletion*/
    public static int deleteAllAppointmentFromACustomer(int customerID) throws SQLException{
        String sql="DELETE from APPOINTMENTS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        return sqlQueryStatement.executeUpdate();
    }
    /**This method will check if user input is matching the credentials in Database.
     * @param password user password
     * @param username user username
     * @return boolean true for match false for mismatch*/
    public static boolean checkUserCredentials(String username, String password) throws SQLException {
        String sql ="SELECT Password FROM USERS WHERE User_Name = ?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setString(1,username);
        ResultSet result = sqlQueryStatement.executeQuery();
        if(result.next()) {
            return password.equals(result.getString("Password"));
        }
        return false;
    }
    /**This method will return name of selected firstLevelDivision.
     * @param divisionID firstLevelDivision ID
     * @return firstLevelDivision name*/
    public static String getDivision(int divisionID) throws SQLException {
        String result="";
        String divisionSQL="SELECT Division FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(divisionSQL);
        sqlQueryStatement.setInt(1,divisionID);
        ResultSet resultSet= sqlQueryStatement.executeQuery();
        if(resultSet.next()) {
            result+=resultSet.getString("Division")+" ";
        }
        return result;
    }
    /**This method will return country name of selected firstLevelDivision.
     * @param divisionID firstLevelDivision ID
     * @return country name*/
    public static String getCountry(int divisionID) throws SQLException {
        String result="";
        int countryID = 0;
        String divisionSQL="SELECT Country_ID FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID = ?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(divisionSQL);
        sqlQueryStatement.setInt(1,divisionID);
        ResultSet resultSet= sqlQueryStatement.executeQuery();
        if(resultSet.next()) {
            countryID=resultSet.getInt("Country_ID");
        }
        String countrySQL="SELECT Country FROM COUNTRIES WHERE Country_ID = ?";
        PreparedStatement sqlQueryStatement1 = DBconnection.getConnection().prepareStatement(countrySQL);
        sqlQueryStatement1.setInt(1,countryID);
        resultSet= sqlQueryStatement1.executeQuery();
        if(resultSet.next()) {
            result+=resultSet.getString("Country");
        }
        return result;
    }
    /**This method will return all customers in SQL Database.
     * @return List of all customers*/
    public static ObservableList<Customer> getCustomerList() throws SQLException {
        ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();
        String sql="Select * from CUSTOMERS";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            allCustomersList.add(new Customer(resultSet.getInt("Customer_ID"),resultSet.getString("Customer_Name"),resultSet.getString("Phone"),resultSet.getString("Address"),resultSet.getInt("Division_ID"),resultSet.getString("Postal_Code")));
        }
        return allCustomersList;
    }
    /**This method will return all appointments in SQL Database.
     * @return List of all appointments*/
    public static ObservableList<Appointment> getAppointmentList() throws SQLException{
        ObservableList<Appointment> allAppointmentList = FXCollections.observableArrayList();
        String sql="Select * from APPOINTMENTS";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            allAppointmentList.add(new Appointment(resultSet.getInt("Appointment_ID"),resultSet.getString("Title"),resultSet.getString("Description"),resultSet.getString("Type"),resultSet.getString("Location"),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("Start").toLocalDateTime()),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("End").toLocalDateTime()),resultSet.getInt("User_ID"),resultSet.getInt("Customer_ID"),resultSet.getInt("Contact_ID")));
        }
        return allAppointmentList;
    }
    /**This method will return all appointments for a specify contact in SQL Database.
     * @param contactID  contact ID
     * @return List of appointments*/
    public static ObservableList<Appointment> getContactAppointmentList(int contactID) throws SQLException{
        ObservableList<Appointment> contactAppointmentList = FXCollections.observableArrayList();
        String sql="Select * from APPOINTMENTS WHERE Contact_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,contactID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            contactAppointmentList.add(new Appointment(resultSet.getInt("Appointment_ID"),resultSet.getString("Title"),resultSet.getString("Description"),resultSet.getString("Type"),resultSet.getString("Location"),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("Start").toLocalDateTime()),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("End").toLocalDateTime()),resultSet.getInt("User_ID"),resultSet.getInt("Customer_ID"),resultSet.getInt("Contact_ID")));
        }
        return contactAppointmentList;
    }
    /**This method will return all appointments for a specify customer in SQL Database.
     * @param customerID  customer ID
     * @return List of appointments*/
    public static ObservableList<Appointment> getCustomerAppointmentList(int customerID) throws SQLException {
        ObservableList<Appointment> resultList = FXCollections.observableArrayList();
        String sql="Select * from APPOINTMENTS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            resultList.add(new Appointment(resultSet.getInt("Appointment_ID"),resultSet.getString("Title"),resultSet.getString("Description"),resultSet.getString("Type"),resultSet.getString("Location"),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("Start").toLocalDateTime()),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("End").toLocalDateTime()),resultSet.getInt("User_ID"),resultSet.getInt("Customer_ID"),resultSet.getInt("Contact_ID")));
        }
        return resultList;
    }
    /**This will get a specify customer from SQL Database.
     * @param customerID customer ID
     * @return selected customer*/
    public static Customer getCustomer(int customerID)throws SQLException{
        String sql="Select * from CUSTOMERS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        if(resultSet.next()){
            return new Customer(resultSet.getInt("Customer_ID"),resultSet.getString("Customer_Name"),resultSet.getString("Phone"),resultSet.getString("Address"),resultSet.getInt("Division_ID"),resultSet.getString("Postal_Code"));
        }
        return null;
    }
    /**This will get a specify user from SQL Database.
     * @param userID user ID
     * @return selected user*/
    public static User getUser(int userID)throws SQLException{
        String sql="Select * from USERS WHERE User_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,userID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        if(resultSet.next()){
            return new User(resultSet.getInt("User_ID"),resultSet.getString("User_Name"));
        }
        return null;
    }
    /**This will get a specify contact from SQL Database.
     * @param contactID contact ID
     * @return selected contact*/
    public static Contact getContact(int contactID)throws SQLException{
        String sql="Select * from CONTACTS WHERE Contact_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,contactID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        if(resultSet.next()){
            return new Contact(resultSet.getString("Contact_Name"),resultSet.getString("Email"),resultSet.getInt("Contact_ID"));
        }
        return null;
    }
    /**This will get a specify appointment from SQL Database.
     * @param appointmentID appointment ID
     * @return selected appointment*/
    public static Appointment getAppointment(int appointmentID) throws SQLException{
        String sql="Select * from APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,appointmentID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        if(resultSet.next()){
            return new Appointment(resultSet.getInt("Appointment_ID"),resultSet.getString("Title"),resultSet.getString("Description"),resultSet.getString("Type"),resultSet.getString("Location"),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("Start").toLocalDateTime()),TimeHelper.toDefaultSystemTime(resultSet.getTimestamp("End").toLocalDateTime()),resultSet.getInt("User_ID"),resultSet.getInt("Customer_ID"),resultSet.getInt("Contact_ID"));
        }
        return null;
    }
    /**This method will return all country in SQL Database.
     * @return List of all country*/
    public static ObservableList<Country> getCountryDataList() throws SQLException{
        ObservableList<Country> countryList = FXCollections.observableArrayList();
        String sql="Select * from COUNTRIES";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            countryList.add(new Country(resultSet.getInt("Country_ID"),resultSet.getString("Country")));
        }
        return countryList;
    }
    /**This method will return all firstLevelDivision for a specify country in SQL Database.
     * @param countryID country ID
     * @return List of firstLevelDivision*/
    public static ObservableList<FirstLevelDivision> getFirstLevelDivisionDataList(int countryID) throws SQLException{
        ObservableList<FirstLevelDivision> firstLevelDivisionList = FXCollections.observableArrayList();
        String sql="Select * from FIRST_LEVEL_DIVISIONS WHERE Country_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,countryID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            firstLevelDivisionList.add(new FirstLevelDivision(resultSet.getInt("Division_ID"),resultSet.getInt("Country_ID"),resultSet.getString("Division")));
        }
        return firstLevelDivisionList;
    }
    /**This method will return all contact in SQL Database.
     * @return List of all contact*/
    public static ObservableList<Contact> getContactDataList() throws SQLException{
        ObservableList<Contact> contactDataList = FXCollections.observableArrayList();
        String sql="Select * from CONTACTS";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            contactDataList.add(new Contact(resultSet.getString("Contact_Name"),resultSet.getString("Email"),resultSet.getInt("Contact_ID")));
        }
        return contactDataList;
    }
    /**This method will return all user in SQL Database.
     * @return List of all user*/
    public static ObservableList<User> getUserDataList() throws SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();
        String sql="Select * from USERS";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        while (resultSet.next()){
            userList.add(new User(resultSet.getInt("User_ID"),resultSet.getString("User_Name")));
        }
        return userList;
    }
}
