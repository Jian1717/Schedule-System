package Com.Model;
/**This class define characteristics of a User and its behaviors. */
public class User {
    private int userID;
    private String name;
/**This is default constructor for user.
 * It creates user object.
 * @param name user name
 * @param userID user ID*/
    public User(int userID, String name) {
        this.userID = userID;
        this.name = name;
    }
    /**This method return user ID.
     * @return user ID*/
    public int getUserID() {
        return userID;
    }
    /**This method override toString() method.
     * it return user name for user displaying in comboBox.
     * @return user name*/
    @Override
    public String toString() {
        return name;
    }
}
