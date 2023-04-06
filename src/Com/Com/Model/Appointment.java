package Com.Model;
import java.time.*;
import java.time.format.DateTimeFormatter;
/**This class define characteristics of an Appointment and its behaviors. */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String type;
    private String location;
    private LocalDateTime start;
    private LocalDateTime end;
    private int userID;
    private int customerID;
    private int contactID;
    /**This is default constructor for Inventory.
     * It creates appointment object.
     * @param title appointment title
     * @param appointmentID appointment ID
     * @param contactID  contact ID
     * @param customerID customer ID
     * @param description appointment description
     * @param end appointment end datetime
     * @param location appointment location
     * @param start appointment start datetime
     * @param type appointment type
     * @param userID user ID*/
    public Appointment(int appointmentID, String title, String description, String type, String location, LocalDateTime start, LocalDateTime end, int userID, int customerID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.type = type;
        this.location = location;
        this.start = start;
        this.end = end;
        this.userID = userID;
        this.contactID = contactID;
        this.customerID = customerID;
    }
    /**This method return appointment ID.
     * @return appointment ID*/
    public int getAppointmentID() {
        return appointmentID;
    }
    /**This method return appointment title.
     * @return appointment title*/
    public String getTitle() {
        return title;
    }
    /**This method return appointment description.
     * @return appointment description*/
    public String getDescription() {
        return description;
    }
    /**This method return appointment type.
     * @return appointment type*/
    public String getType() {
        return type;
    }
    /**This method return appointment location.
     * @return appointment location*/
    public String getLocation() {
        return location;
    }
    /**This method return appointment start datetime.
     * @return appointment start datetime*/
    public LocalDateTime getStart() {
        return start;
    }
    /**This method return appointment end datetime.
     * @return appointment end datetime*/
    public LocalDateTime getEnd() {
        return end;
    }
    /**This method return user ID.
     * @return user ID*/
    public int getUserID() {
        return userID;
    }
    /**This method return customer ID.
     * @return customer ID*/
    public int getCustomerID() {
        return customerID;
    }
    /**This method return contact ID.
     * @return contact ID*/
    public int getContactID() {
        return contactID;
    }
    /**This method return appointment start datetime in MM-dd-yyyy HH:mm:ss format.
     * @return appointment start datetime*/
    public String getStartTime(){
        return start.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
    /**This method return appointment end datetime in MM-dd-yyyy HH:mm:ss format.
     * @return appointment end datetime*/
    public String getEndTime(){
        return end.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
}
