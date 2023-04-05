package Com.Model;

import Com.DAO.DBHelper;

import java.sql.SQLException;

public class Customer {
    private int customerID;
    private String name;
    private String phone;
    private String address;
    private int divisionID;
    private String postalCode;
    private int countryID;
    public Customer(int customerID, String name, String phone, String address,int divisionID,String postalCode) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.divisionID=divisionID;
        this.postalCode=postalCode;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getFullAddress() throws SQLException {
        return address+", "+ DBHelper.getDivision(divisionID)+", "+postalCode+", "+DBHelper.getCountry(divisionID);
    }

    @Override
    public String toString() {
        return name;
    }
}
