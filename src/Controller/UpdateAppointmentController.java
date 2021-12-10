package Controller;

import Interfaces.FindContactID;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utils.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/** This class handles the operations between the classes in the model package and the UpdateAppointment.fxml component */
public class UpdateAppointmentController implements Initializable {
    public TextField updateId;
    @FXML
    public TextField updateTitle;
    public TextField updateDescription;
    public TextField updateLocation;
    public ComboBox updateContact;
    public TextField updateType;
    public DatePicker updateStartDate;
    public ComboBox updateStartTime;
    public DatePicker updateEndDate;
    public ComboBox updateEndTime;
    public ComboBox updateCustomerId;
    public ComboBox updateUserId;
    public int appointmentId = 0;
    public LocalDateTime created;
    public int contactId;
    public int customerId;
    public int userId;
    public String createdBy;
    private static ZoneId localZone = ZoneId.systemDefault();

    /** This method sets up the combo boxes for time */
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Time set up
        LocalTime start = LocalTime.of(5,0);
        LocalTime firstAppointmentEnd = LocalTime.of(5, 15);
        LocalTime end = LocalTime.of(22, 45);
        while(start.isBefore(end.plusSeconds(1))){
            updateStartTime.getItems().add(start);
            updateEndTime.getItems().add(firstAppointmentEnd);
            start = start.plusMinutes(15);
            firstAppointmentEnd = firstAppointmentEnd.plusMinutes(15);
        }
        // Resets the Customer array
        DBCustomer.deleteAllCustomers();
        // Resets the Contacts array
        DBContact.deleteAllContacts();
        // Resets the Users array
        User.deleteUsers();
    }

    /** This method queries the database for the requested appointment
     * @param appointment
     */
    public void fetchAppointments(Appointment appointment) {
        ObservableList<Contact> contactsList = DBContact.getContacts();
        ObservableList<Customer> customersList = DBCustomer.getCustomers();
        ObservableList<User> usersList = DBUser.getUsers();
        String currentContact = "";

        // Queries the database to get the Division Name, country ID and country name
        try {
            String sql = "SELECT * from appointments WHERE Appointment_ID=" + "'" + appointment.getAppointmentId() + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointmentId = rs.getInt("Appointment_ID");
                LocalDate createDate = rs.getDate("Create_Date").toLocalDate();
                LocalTime createTime = rs.getTime("Create_Date").toLocalTime();
                String createDateTime = createDate + " " + createTime;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                created = LocalDateTime.parse(createDateTime, dtf);
                createdBy = rs.getString("Created_By");
                customerId = rs.getInt("Customer_ID");
                userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                // Query to find the selected contact
                try {
                    String query = "SELECT Contact_Name from contacts WHERE Contact_ID=" + contactId;
                    PreparedStatement prepStat = DBConnection.getConnection().prepareStatement(query);
                    ResultSet result = prepStat.executeQuery();
                    while (result.next()) {
                        currentContact = result.getString("Contact_Name");
                    }
                } catch (SQLException e) {
                    //IGNORE
                }

            }
        }catch(SQLException e){
            // Ignore
        }

        // Fill out the fields
        updateId.setText(String.valueOf(appointment.getAppointmentId()));
        updateTitle.setText(String.valueOf(appointment.getTitle()));
        updateDescription.setText(String.valueOf(appointment.getDescription()));
        updateLocation.setText(String.valueOf(appointment.getLocation()));
        updateContact.setItems(contactsList);
        updateContact.setValue(currentContact);
        updateType.setText(String.valueOf(appointment.getType()));
        updateStartDate.setValue(appointment.getStartDate());
        updateStartTime.setValue(appointment.getStartTime());
        updateEndDate.setValue(appointment.getEndDate());
        updateEndTime.setValue(appointment.getEndTime());
        updateCustomerId.setItems(customersList);
        updateCustomerId.setValue(customerId);
        updateUserId.setItems(usersList);
        updateUserId.setValue(userId);
        onContactSelection();
    }

    /** This method extracts the contact ID from the selected contact
     *  This lambda expression uses the FindContactID Interface. It queries the database and returns the contact ID
     * @return int contactId;
     */
    public int onContactSelection(){
        String contactName = updateContact.getSelectionModel().getSelectedItem().toString();
        FindContactID findContactID = contact -> {
            try {
                String sql = "SELECT Contact_ID from contacts WHERE Contact_Name=" + "'" + contactName + "'";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    contactId = rs.getInt("Contact_ID");
                }
            } catch (SQLException e) {
                // Ignore
            }
            return contactId;
        };
        return findContactID.findContactId(contactName);
    }

    /** This method extracts the customer ID from the selected contact
     * @return int customerId;
     */
    public int onCustomerSelection(){
        try{
            Customer customer = (Customer) updateCustomerId.getSelectionModel().getSelectedItem();
            customerId = Integer.parseInt(String.valueOf(customer));
        }catch(Exception e){
            // Ignore
        }
        return customerId;
    }

    /** This method extracts the user ID from the selected user
     @return int userId;
     * */
    public int onUserSelection(){
        try{
            User user = (User) updateUserId.getSelectionModel().getSelectedItem();
            userId = Integer.parseInt(String.valueOf(user));
        }catch(Exception e){
            // Ignore
        }
        return userId;
    }

    /** This method validates that the start date and time of the appointment is before the end time of the appointment
     * @return boolean
     */
    public boolean onValidStartTime(){
        LocalDate startDate = updateStartDate.getValue();
        LocalTime startTime = (LocalTime) updateStartTime.getValue();
        LocalDate endDate = updateEndDate.getValue();
        LocalTime endTime = (LocalTime) updateEndTime.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        if(startDateTime.isBefore(endDateTime)){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Time");
            alert.setHeaderText("Please select a different date or time");
            alert.setContentText("The start time of the appointment cannot be later than the end of the appointment");
            alert.showAndWait();
            return false;
        }
    }

    /** This method checks for an overlap with other appointments by comparing date and time
     * @return boolean;
     */
    public boolean overlapCheck(){
        Appointment.deleteAllAppointmentsByCustomerId();
        DBAppointment.getAppointmentsByCustomerId(customerId);

        LocalDate startDate = updateStartDate.getValue();
        LocalTime startTime = (LocalTime) updateStartTime.getValue();
        LocalDate endDate = updateEndDate.getValue();
        LocalTime endTime = (LocalTime) updateEndTime.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        for(Appointment a : Appointment.getAppointmentsByCustomerId()) {
            if (a.getAppointmentId() != appointmentId) {
                LocalDateTime appointmentStartDateTime = a.getStart().toLocalDateTime();
                LocalDateTime appointmentEndDateTime = a.getEnd().toLocalDateTime();

                if ((startDateTime.isAfter(appointmentStartDateTime) && endDateTime.isBefore(appointmentEndDateTime)) || (startDateTime.isAfter(appointmentStartDateTime) && startDateTime.isBefore(appointmentEndDateTime)) || (startDateTime.isEqual(appointmentStartDateTime)) || endDateTime.isEqual(appointmentEndDateTime) || (startDateTime.isBefore(appointmentStartDateTime) && (endDateTime.isBefore(appointmentEndDateTime) && endDateTime.isAfter(appointmentStartDateTime)))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Overlap");
                    alert.setHeaderText("Please select a different date or time");
                    alert.setContentText("The customer: " + DBCustomer.getCustomerName(a.getCustomerId()) + " has an appointment on: " + appointmentStartDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " until: " + appointmentEndDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    alert.showAndWait();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * return boolean
     * This method checks that the selected time for the appointment is between 8am and 10pm EST */
    public boolean easternTimeCheck(){
        LocalDate startDate = updateStartDate.getValue();
        LocalTime startTime = (LocalTime) updateStartTime.getValue();
        LocalDate endDate = updateEndDate.getValue();
        LocalTime endTime = (LocalTime) updateEndTime.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        ZonedDateTime startEst = startDateTime.atZone(localZone).withZoneSameInstant(ZoneId.of("US/Eastern"));
        ZonedDateTime endEst = endDateTime.atZone(localZone).withZoneSameInstant(ZoneId.of("US/Eastern"));

        String startHourFormatted = startEst.format(DateTimeFormatter.ofPattern("HH"));
        int hourStart = Integer.parseInt(startHourFormatted);

        String endHourFormatted = endEst.format(DateTimeFormatter.ofPattern("HH"));
        int hourEnd = Integer.parseInt(endHourFormatted);

        if((hourStart > 7) && (hourEnd < 22)){
            return true;
        }else{
            return false;
        }
    }

    /** This method handles the update process of an appointment. It extracts the values from the form and update them in the database */
    public void onSaveButton(ActionEvent actionEvent) throws IOException{
        if(onValidStartTime()){
            if(easternTimeCheck()) {
                try {
                    Connection conn = DBConnection.getConnection();
                    DBQuery.setStatement((conn)); //Create statement object
                    Statement statement = DBQuery.getStatement();
                    try {
                        if (overlapCheck() == true) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Time Zone Issue");
                            alert.setHeaderText("Appointments must fit Eastern Standard Time");
                            alert.setContentText("Please make sure that the start and end time for the appointment are between 8am and 10pm EST");
                            alert.showAndWait();
                            return;
                        }
                        String title = updateTitle.getText();
                        String description = updateDescription.getText();
                        String location = updateLocation.getText();
                        String type = updateType.getText();
                        LocalDate startDate = updateStartDate.getValue();
                        LocalTime startTime = (LocalTime) updateStartTime.getValue();
                        LocalDate endDate = updateEndDate.getValue();
                        LocalTime endTime = (LocalTime) updateEndTime.getValue();
                        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
                        LocalDateTime now = LocalDateTime.now();
                        ZonedDateTime startUtc = startDateTime.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
                        ZonedDateTime endUtc = endDateTime.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
                        ZonedDateTime nowUtc = now.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
                        Timestamp start = Timestamp.valueOf(startUtc.toLocalDateTime());
                        Timestamp end = Timestamp.valueOf(endUtc.toLocalDateTime());
                        Timestamp lastUpdate = Timestamp.valueOf(nowUtc.toLocalDateTime());
                        String lastUpdatedBy = User.getActiveUserName();
                        customerId = onCustomerSelection();
                        userId = onUserSelection();

                        String updateStatement = "UPDATE appointments " +
                                "SET Title=" + "'" + title + "', " +
                                "Description=" + "'" + description + "', " +
                                "Location=" + "'" + location + "', " +
                                "type=" + "'" + type + "', " +
                                "Start=" + "'" + start + "', " +
                                "End=" + "'" + end + "', " +
                                "Create_Date=" + "'" + created + "', " +
                                "Created_By=" + "'" + createdBy + "', " +
                                "Last_Update=" + "'" + lastUpdate + "', " +
                                "Last_Updated_By=" + "'" + lastUpdatedBy + "', " +
                                "Customer_ID=" + customerId + ", " +
                                "User_ID=" + userId + ", " +
                                "Contact_ID=" + contactId + " " +
                                "WHERE Appointment_ID=" + appointmentId;

                        statement.execute(updateStatement);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Appointment");
                        alert.setHeaderText("Not all fields are complete");
                        alert.setContentText("Please make sure all fields are properly completed");
                        alert.showAndWait();
                        return;
                    }
                } catch (SQLException e) {
                    // Ignore
                }
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Time Zone Issue");
                alert.setHeaderText("Appointments must fit Eastern Standard Time");
                alert.setContentText("Please make sure that the start and end time for the appointment are between 8am and 10pm EST");
                alert.showAndWait();
            }
        }
    }

    /** This method handles the cancel button function. It redirects to the main screen */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
