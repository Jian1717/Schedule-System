package Com.DAO;

import Com.Helper.TimeHelper;
import Com.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public interface DBHelper {
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
    public static int deleteCustomer(int customerID) throws SQLException{
        String sql="DELETE from CUSTOMERS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        return sqlQueryStatement.executeUpdate();
    }
    public static int deleteAppointment(int appointmentID) throws SQLException{
        String sql="DELETE from APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,appointmentID);
        return sqlQueryStatement.executeUpdate();
    }
    public static int deleteAllAppointmentFromACustomer(int customerID) throws SQLException{
        String sql="DELETE from APPOINTMENTS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        return sqlQueryStatement.executeUpdate();
    }
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
    public static int getTotalAppointmentOfCustomer(int customerID) throws SQLException {
        int result=0;
        String sql="Select * from APPOINTMENTS WHERE Customer_ID=?";
        PreparedStatement sqlQueryStatement = DBconnection.getConnection().prepareStatement(sql);
        sqlQueryStatement.setInt(1,customerID);
        ResultSet resultSet=sqlQueryStatement.executeQuery();
        if(resultSet.next()){
            result++;
        }
        return result;
    }
}
