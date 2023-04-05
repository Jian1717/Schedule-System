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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getUserID() {
        return userID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getContactID() {
        return contactID;
    }

    public String getStartTime(){
        return start.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
    public String getEndTime(){
        return end.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
    }
}
