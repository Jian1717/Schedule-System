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

    @Override
    public String toString() {
        return divisionName;
    }
}
