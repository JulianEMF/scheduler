package Utils;

import Model.ContactAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** The DBContactAppointment class contains a list of appointments by contact ID and a method to populate it*/
public class DBContactAppointment {

    /**
     * This method queries the database and retrieves appointments by contactID
     * @param contactId
     * @return appointmentsByContact
     */
    public static ObservableList<ContactAppointment> getAppointmentsByContactId(int contactId){
        ObservableList<ContactAppointment> appointmentsByContact = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from appointments WHERE Contact_ID=" + "'" + contactId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                LocalDateTime dateTimeStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime dateTimeEnd = rs.getTimestamp("End").toLocalDateTime();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String start = dtf.format(dateTimeStart);
                String end = dtf.format(dateTimeEnd);
                int customerId = rs.getInt("Customer_ID");

                ContactAppointment newContact = new ContactAppointment(appointmentId, title, type, description, start, end, customerId);
                appointmentsByContact.add(newContact);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return appointmentsByContact;
    }
}
