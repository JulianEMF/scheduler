package Utils;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/** The class DBAppointment contains several lists that categorize the appointments by all, monthly, weekly, and by customersID.
 * As well as methods to populate them
 * */
public class DBAppointment {
    private static ObservableList<Appointment> appointmentsAll = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsMonthly = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsWeekly = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsByCustomersId = FXCollections.observableArrayList();
    private static ZoneId zoneId = ZoneId.systemDefault();

    /**
     * This method obtains all the appointments
     * @return appointmentsAll
     */
    public static ObservableList<Appointment> addAppointmentsDataAll() {
        String contactName = "";
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(zoneId);
                ZonedDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(zoneId);
                ZonedDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(zoneId);
                String formattedStart = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String formattedEnd = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                try{
                    String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID =" + "'" + contactId + "'";
                    PreparedStatement prepStat = DBConnection.getConnection().prepareStatement(query);
                    ResultSet result = prepStat.executeQuery();
                    while (result.next()) {
                        contactName = result.getString("Contact_Name");
                    }
                }catch(SQLException e){
                    // Ignore
                }
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, formattedStart, formattedEnd, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
                Appointment.addAppointmentsAll(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsAll;
    }

    /**
     * This method obtains all the appointments of the month
     * @return appointmentsMonthly
     */
    public static ObservableList<Appointment> addAppointmentsDataMonthly() {
        String contactName = "";
        try {
            String sql = "SELECT * FROM appointments WHERE Start between NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 30 DAY))";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(zoneId);
                ZonedDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(zoneId);
                ZonedDateTime create = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(zoneId);
                String formattedStart = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String formattedEnd = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                try{
                    String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID =" + "'" + contactId + "'";
                    PreparedStatement prepStat = DBConnection.getConnection().prepareStatement(query);
                    ResultSet result = prepStat.executeQuery();
                    while (result.next()) {
                        contactName = result.getString("Contact_Name");
                    }
                }catch(SQLException e){
                    // Ignore
                }

                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, create, formattedStart, formattedEnd, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
                Appointment.addAppointmentsMonthly(appointment);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentsMonthly;
    }

    /**
     * This method obtains all the appointments of the week
     * @return appointmentsWeekly
     */
    public static ObservableList<Appointment> addAppointmentsDataWeekly() {
        String contactName = "";
        try {
            String sql = "SELECT * FROM appointments WHERE Start between NOW() AND (SELECT ADDDATE(NOW(), INTERVAL 7 DAY))";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(zoneId);
                ZonedDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(zoneId);
                ZonedDateTime create = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(zoneId);
                String formattedStart = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String formattedEnd = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                try{
                    String query = "SELECT Contact_Name FROM contacts WHERE Contact_ID =" + "'" + contactId + "'";
                    PreparedStatement prepStat = DBConnection.getConnection().prepareStatement(query);
                    ResultSet result = prepStat.executeQuery();
                    while (result.next()) {
                        contactName = result.getString("Contact_Name");
                    }
                }catch(SQLException e){
                    // Ignore
                }

                Appointment appointmentsWeekly = new Appointment(appointmentId, title, description, location, type, start, end, create, formattedStart, formattedEnd, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
                Appointment.addAppointmentsWeekly(appointmentsWeekly);
            }
        } catch (SQLException e) {
            // Ignore
        }
        return appointmentsWeekly;
    }

    /**
     * This method obtains all the appointments by CustomerID
     * @return appointmentsByCustomersId
     */
    public static ObservableList<Appointment> getAppointmentsByCustomerId(int custId){
        String contactName = "";
        try {
            String sql = "SELECT * FROM appointments WHERE Customer_ID=" + custId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime start = rs.getTimestamp("Start").toLocalDateTime().atZone(zoneId);
                ZonedDateTime end = rs.getTimestamp("End").toLocalDateTime().atZone(zoneId);
                ZonedDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime().atZone(zoneId);
                String formattedStart = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String formattedEnd = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                Appointment appointmentByCustomerId = new Appointment(appointmentId, title, description, location, type, start, end, createDate, formattedStart, formattedEnd, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId, contactName);
                Appointment.addAppointmentsByCustomersId(appointmentByCustomerId);

            }
        } catch (SQLException e) {
            // Ignore
        }
        return appointmentsByCustomersId;
    }
}
