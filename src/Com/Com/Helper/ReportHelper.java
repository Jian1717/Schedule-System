package Com.Helper;

import Com.DAO.DBHelper;
import Com.Model.Appointment;
import Com.Model.Contact;
import Com.Model.Customer;

import java.sql.SQLException;
import java.util.HashMap;

public interface ReportHelper {
    public static String getTypeAppointReport() throws SQLException {
        HashMap<String,Integer> result= new HashMap<String,Integer>();
        StringBuffer report=new StringBuffer();
        DBHelper.getAppointmentList().forEach(s->{
            String key=s.getStart().getMonth()+" - "+s.getType();
            if(result.containsKey(key)){
                result.put(key,result.get(key)+1);
            }else{
                result.put(key,1);
            }

        });
        for (HashMap.Entry<String, Integer> resultValue : result.entrySet()) {
            report.append(resultValue.getKey()+" - "+resultValue.getValue()+"\n");
        }
        if(report.length()==0){
            report.append("No appointment in the system");
        }
        return report.toString();
    }
    public static String getContactAppointmentReport() throws SQLException {
        StringBuffer report=new StringBuffer();
        int length;
        for(Contact contact:DBHelper.getContactDataList()){
            report.append("Contact: "+contact.toString()+"\n");
            length=report.length();
            for(Appointment appointment:DBHelper.getContactAppointmentList(contact.getId())){
                report.append("Appointment ID: "+appointment.getAppointmentID()+" Title: "+appointment.getTitle()+" Type: "+appointment.getType()+" Description: "+appointment.getDescription()+" Start: "+appointment.getStartTime()+" End: "+appointment.getEndTime()+" Customer ID: "+appointment.getCustomerID()+"\n");
            }
            if(length==report.length()){
                report.append("No up coming appointment \n");
            }
        }

        return report.toString();
    }
    public static String getCustomerAppointmentReport() throws SQLException{
        StringBuffer report=new StringBuffer();
        for(Customer customer:DBHelper.getCustomerList()){
            report.append("Customer: "+customer.getName()+" ID: "+customer.getCustomerID()+"\n");
            int length= report.length();
            for(Appointment appointment:DBHelper.getCustomerAppointmentList(customer.getCustomerID())){
                report.append("Appointment ID: "+appointment.getAppointmentID()+" Title: "+appointment.getTitle()+" Type: "+appointment.getType()+" Description: "+appointment.getDescription()+" Start: "+appointment.getStartTime()+" End: "+appointment.getEndTime()+"\n");
            }
            if(length==report.length()){
                report.append("No up coming appointment \n");
            }
        }
        return report.toString();
    }
}
