package Com.Model;
import java.time.*;
import java.time.format.DateTimeFormatter;

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

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getStartTime(){
        return start.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
    public String getEndTime(){
        return end.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
}
