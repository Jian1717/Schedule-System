package Com.Helper;

import java.sql.Timestamp;
import java.time.*;

public interface TimeHelper {
    LocalTime openHours=LocalTime.parse("08:00:00");
    LocalTime closeHours=LocalTime.parse("22:00:00");

    public static LocalDateTime timeStamp(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.now());
    }
    public static Timestamp toUTCTimestamp(LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
    }
    public static LocalDateTime toDefaultSystemTime(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }
    public static LocalTime toESTTime(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York")).toLocalTime();
    }
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
