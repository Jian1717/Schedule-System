package Com.Model;

public class FirstLevelDivision {
    private int divisionID;
    private int countryID;
    private String divisionName;

    public FirstLevelDivision(int divisionID, int countryID, String divisionName) {
        this.divisionID = divisionID;
        this.countryID = countryID;
        this.divisionName = divisionName;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString() {
        return divisionName;
    }
}
