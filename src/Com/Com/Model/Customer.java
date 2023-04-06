package Com.Model;

import Com.DAO.DBHelper;

import java.sql.SQLException;
/**This class define characteristics of a Customer and its behaviors. */
public class Customer {
    private int customerID;
    private String name;
    private String phone;
    private String address;
    private int divisionID;
    private String postalCode;
    /**This is default constructor for Customer.
    *It creates Customer object.
     * @param name customer name
     * @param customerID customer ID
     * @param address customer address
     * @param divisionID FirstLevelDivision ID
     * @param phone customer phone number
     * @param postalCode customer postal code*/
    public Customer(int customerID, String name, String phone, String address,int divisionID,String postalCode) {
        this.customerID = customerID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.divisionID=divisionID;
        this.postalCode=postalCode;
    }
    /**This method return customer ID.
     * @return customer ID*/
    public int getCustomerID() {
        return customerID;
    }
    /**This method return customer name.
     * @return customer name*/
    public String getName() {
        return name;
    }
    /**This method return customer phone.
     * @return customer phone*/
    public String getPhone() {
        return phone;
    }
    /**This method return customer address.
     * @return customer address*/
    public String getAddress() {
        return address;
    }
    /**This method return FirstLevelDivision ID.
     * @return FirstLevelDivision ID*/
    public int getDivisionID() {
        return divisionID;
    }
    /**This method return customer postal code.
     * @return customer postal code*/
    public String getPostalCode() {
        return postalCode;
    }
    /**This method return customer full address.
     * it will address in format of address, firstLevelDivision, postalCode, country.
     * @return customer full address*/
    public String getFullAddress() throws SQLException {
        return address+", "+ DBHelper.getDivision(divisionID)+", "+postalCode+", "+DBHelper.getCountry(divisionID);
    }
    /**This method override toString() method.
     * it return customer name for customer displaying in comboBox.
     * @return customer name*/
    @Override
    public String toString() {
        return name;
    }
}
