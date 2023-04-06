package Com.Model;
/**This class define characteristics of a Contact and its behaviors. */
public class Contact {
    private String name;
    private String email;
    private int id;
    /**This is default constructor for Contact.
     * It creates contact object.
     * @param email contact email
     * @param id contact id
     * @param name contact name*/
    public Contact(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }
    /**This method return contact ID.
     * @return contact ID*/
    public int getId() {
        return id;
    }
    /**This method override toString() method.
     * it return contact name and email as one String for contact displaying in comboBox.
     * @return contact name and email*/
    @Override
    public String toString() {
        return name + " "+ email;
    }
}
