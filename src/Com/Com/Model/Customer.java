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

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFullAddress() throws SQLException {
        return address+", "+ DBHelper.getDivision(divisionID)+", "+postalCode+", "+DBHelper.getCountry(divisionID);
    }

    @Override
    public String toString() {
        return name;
    }
}
