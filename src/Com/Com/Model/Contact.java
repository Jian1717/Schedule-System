package Com.Model;

public class Contact {
    private String name;
    private String email;
    private int id;

    public Contact(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + " "+ email;
    }
}
