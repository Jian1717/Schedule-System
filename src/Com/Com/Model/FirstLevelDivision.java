package Com.Model;
/**This class define characteristics of a FirstLevelDivision and its behaviors. */
public class FirstLevelDivision {
    private int divisionID;
    private int countryID;
    private String divisionName;
/**This is default constructor for FirstLevelDivision.
 * It creates FirstLevelDivision object.
 * @param countryID country ID
 * @param divisionID firstLevelDivision ID
 * @param divisionName firstLevelDivision name*/
    public FirstLevelDivision(int divisionID, int countryID, String divisionName) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
    }
    /**This method return firstLevelDivision ID.
     * @return firstLevelDivision ID*/
    public int getDivisionID() {
        return divisionID;
    }
    /**This method override toString() method.
     * it return firstLevelDivision name for firstLevelDivision displaying in comboBox.
     * @return firstLevelDivision name*/
    @Override
    public String toString() {
        return divisionName;
    }
}
