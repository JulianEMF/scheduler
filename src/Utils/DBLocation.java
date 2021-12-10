package Utils;

import Model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** The DBLocation class contains a list of locations by name as well as the methods to populate it*/
public class DBLocation {

    /**
     * This method retrieves appointments based on their location
     * @param location
     * @return locationList
     */
    public static ObservableList<Location> getLocationByName(String location) {
        ObservableList<Location> locationList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from appointments WHERE Location=" + "'" + location + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                int customerId = rs.getInt("Customer_ID");
                LocalDateTime dateTimeStart = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime dateTimeEnd = rs.getTimestamp("End").toLocalDateTime();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String start = dtf.format(dateTimeStart);
                String end = dtf.format(dateTimeEnd);
                String customerName = getCustomerName(customerId);

                Location newLocation = new Location(location, appointmentId, title, customerName, start, end);
                locationList.add(newLocation);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return locationList;
    }

    /**
     * This method retrieves distinct locations
     * @return allLocations
     */
    public static ObservableList<Location> getAllLocations() {
        ObservableList<Location> allLocations = FXCollections.observableArrayList();
        try {
            String sql = "SELECT DISTINCT Location from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String locationName = rs.getString("Location");
                Location newLocation = new Location(locationName);
                allLocations.add(newLocation);
            }
        } catch (SQLException e) {
            // Ignore
        }
        return allLocations;
    }

    /**
     * This method queries the database to find a customer's name base on their customerID
     * @param customerId
     * @return customerName
     */
    public static String getCustomerName(int customerId) {
        String customerName = "";
        try {
            String sql = "SELECT Customer_Name from customers WHERE Customer_ID=" + "'" + customerId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customerName = rs.getString("Customer_Name");
            }
        } catch (SQLException e) {
            // Ignore
        }
        return customerName;
    }
}
