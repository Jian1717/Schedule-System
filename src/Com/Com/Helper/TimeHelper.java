package Com.Helper;

import java.sql.Timestamp;
import java.time.*;
/**This interface contain useful methods that handling time in this application. */
public interface TimeHelper {
    LocalTime openHours=LocalTime.parse("08:00:00");
    LocalTime closeHours=LocalTime.parse("22:00:00");
    /**This method will create a LocalDateTime of current datetime in default system time.
     * @return current datetime*/
    public static LocalDateTime timeStamp(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }
    /**This method covert default system datetime into same datetime instant in UTC time.
     * It also covert LocalDatetime object to SQL Timestamp object.
     * @return Timestamp in UTC time*/
    public static Timestamp toUTCTimestamp(LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
    }
    /**This method covert LocalDateTime in UTC time into default system time zone.
     * @param localDateTime datetime in default system time zone
     * @return LocalDateTime in default system time zone*/
    public static LocalDateTime toDefaultSystemTime(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
    /**This method covert default system datetime into same datetime instant in UTC time.
     * @param localDateTime datetime in default system time zone
     * @return LocalDateTime in EST time zone*/
    public static LocalTime toESTTime(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime();
    }
    /**This method will compare two time period in order terminate if there is a time overlap between two period.
     * @param startDateTime1 period1 start datetime
     * @param endDateTime1 period1 end datetime
     * @param startDateTime2 period2 start datetime
     * @param endDateTime2 period2 start datetime
     * @return boolean true for overlapping.  false for not a overlapping*/
    public static boolean isOverlapTime(LocalDateTime startDateTime1,LocalDateTime endDateTime1,LocalDateTime startDateTime2,LocalDateTime endDateTime2){
        if(startDateTime1.isEqual(startDateTime2)){
            return true;
        }
        if(startDateTime1.isBefore(startDateTime2)&&endDateTime1.isAfter(startDateTime2)){
            return true;
        }
        if(startDateTime2.isBefore(startDateTime1)&&endDateTime2.isAfter(startDateTime1)){
            return true;
        }
        return false;
    }
    /**This method will check a time period is between 8:00 am - 10:00 pm est.
     * It will convert time period in default system time zone to EST.
     * @param startDateTime start datetime in default system time zone
     * @param endDateTime end datetime in default system time zone
     * @return boolean true for within hours.  false for not within hours*/
    public static boolean isInBusinessHours(LocalDateTime startDateTime,LocalDateTime endDateTime){
        boolean result=true;
        LocalTime startTime=toESTTime(startDateTime);
        LocalTime endTime=toESTTime(endDateTime);
        if(startTime.isBefore(openHours)){
            result=false;
        }
        if(endTime.isAfter(closeHours)){
            result=false;
        }
        return result;
    }
}
