package Com.Model;
/**This class define characteristics of a Country and its behaviors. */
public class Country {
    private int countryID;
    private String countryName;
/**This is default constructor for Country.
 * It creates Country object.
 * @param countryID country ID
 * @param countryName country name*/
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }
    /**This method return country ID.
     * @return country ID*/
    public int getCountryID() {
        return countryID;
    }
    /**This method return country name.
     * @return country name*/
    public String getCountryName() {
        return countryName;
    }
    /**This method override toString() method.
     * it return country name for country displaying in comboBox.
     * @return country name*/
    @Override
    public String toString() {
        return countryName;
    }
}
