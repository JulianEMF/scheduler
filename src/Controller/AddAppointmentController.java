package Controller;

import Interfaces.FindContactID;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utils.*;
import javafx.event.ActionEvent;
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

/** This class handles the operations between the classes in the model package and the AddAppointment.fxml component */
public class AddAppointmentController implements Initializable{
    public TextField appointmentId;
    public TextField appointmentTitle;
    public TextField appointmentDescription;
    public TextField appointmentLocation;
    public ComboBox<Contact> appointmentContact;
    public TextField appointmentType;
    public DatePicker appointmentStartDate;
    public DatePicker appointmentEndDate;
    public ComboBox appointmentStartTime;
    public ComboBox appointmentEndTime;
    public ComboBox<Customer> appointmentCustomerId;
    public ComboBox<User> appointmentUserId;
    public int contactId;
    public int customerId;
    public int userId;
    private static ZoneId localZone = ZoneId.systemDefault();

    /** This method populates the combo boxes used for contacts, customers and users. It also sets up the combo boxes for time */
    public void initialize(URL location, ResourceBundle resourceBundle){
        // Request contact data
        appointmentContact.setPromptText("Please select a contact...");
        appointmentCustomerId.setPromptText("Please select a customer...");
        appointmentUserId.setPromptText("Please select a user...");
        DBContact.deleteAllContacts();
        appointmentContact.setItems(DBContact.addContactsData());
        DBCustomer.deleteAllCustomers();
        appointmentCustomerId.setItems(DBCustomer.getCustomers());
        User.deleteUsers();
        appointmentUserId.setItems(DBUser.getUsers());

        // Time set up
        LocalTime start = LocalTime.of(5,0);
        LocalTime firstAppointmentEnd = LocalTime.of(5, 15);
        LocalTime end = LocalTime.of(22, 45);
        while(start.isBefore(end.plusSeconds(1))){
            appointmentStartTime.getItems().add(start);
            appointmentEndTime.getItems().add(firstAppointmentEnd);
            start = start.plusMinutes(15);
            firstAppointmentEnd = firstAppointmentEnd.plusMinutes(15);
        }
        appointmentStartTime.getSelectionModel().select(LocalTime.of(8,0));
        appointmentEndTime.getSelectionModel().select(LocalTime.of(8, 15));
    }

    /** This method extracts the customer ID from the selected customer */
    public void onCustomerSelection(){
        try{
            Customer customer = appointmentCustomerId.getSelectionModel().getSelectedItem();
            customerId = Integer.parseInt(String.valueOf(customer));
        }catch(Exception e){
            // Ignore
        }
    }

    /** This method validates that the start date and time of the appointment is before the end time of the appointment
     * @return boolean
     */
    public boolean onValidStartTime(){
        LocalDate startDate = appointmentStartDate.getValue();
        LocalTime startTime = (LocalTime) appointmentStartTime.getValue();
        LocalDate endDate = appointmentEndDate.getValue();
        LocalTime endTime = (LocalTime) appointmentEndTime.getValue();
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

    /** This method checks for overlapping appointments
     * @return boolean
     */
    public boolean overlapCheck(){
        Appointment.deleteAllAppointmentsByCustomerId();
        DBAppointment.getAppointmentsByCustomerId(customerId);

        LocalDate startDate = appointmentStartDate.getValue();
        LocalTime startTime = (LocalTime) appointmentStartTime.getValue();
        LocalDate endDate = appointmentEndDate.getValue();
        LocalTime endTime = (LocalTime) appointmentEndTime.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        for(Appointment a : Appointment.getAppointmentsByCustomerId()){
            LocalDateTime appointmentStartDateTime = a.getStart().toLocalDateTime();
            LocalDateTime appointmentEndDateTime = a.getEnd().toLocalDateTime();

            if((startDateTime.isAfter(appointmentStartDateTime) && endDateTime.isBefore(appointmentEndDateTime)) ||
                    (startDateTime.isAfter(appointmentStartDateTime) && startDateTime.isBefore(appointmentEndDateTime)) ||
                    (startDateTime.isEqual(appointmentStartDateTime)) || endDateTime.isEqual(appointmentEndDateTime) ||
                    (startDateTime.isBefore(appointmentStartDateTime) && (endDateTime.isAfter(appointmentStartDateTime)))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Overlap");
                alert.setHeaderText("Please select a different date or time");
                alert.setContentText("The customer: " + DBCustomer.getCustomerName(a.getCustomerId()) + " has an appointment on: " + appointmentStartDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " until: " + appointmentEndDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    /** This method extracts the contact ID from the selected contact
     * The lambda expression in this method uses the FindContactID Interface. It queries the database and return the contact ID
     * Using a lambda expression here saves a few lines of code, and eliminates the need to create a new method to store that code
     * @return contactId*/
    public int onContactSelection() {
        try{
            String contactName = appointmentContact.getSelectionModel().getSelectedItem().toString();

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
        }catch(Exception e){
            // Ignore
        }
        return 0;
    }

    /** This method extracts the user ID from the selected user */
    public void onUserSelection(){
        try{
            User user = appointmentUserId.getSelectionModel().getSelectedItem();
            userId = Integer.parseInt(String.valueOf(user));
        }catch(Exception e){
            // Ignore
        }
    }

    /**
     * return boolean
     * This method checks that the selected time for the appointment is between 8am and 10pm EST */
    public boolean easternTimeCheck(){
        LocalDate startDate = appointmentStartDate.getValue();
        LocalTime startTime = (LocalTime) appointmentStartTime.getValue();
        LocalDate endDate = appointmentEndDate.getValue();
        LocalTime endTime = (LocalTime) appointmentEndTime.getValue();
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

    /** This method handles the save process of an appointment. It extracts the values from the form and insert them in the database */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        if(onValidStartTime()){
            if(easternTimeCheck()){
                try{
                    Connection conn = DBConnection.getConnection();
                    DBQuery.setStatement((conn)); //Create statement object
                    Statement statement = DBQuery.getStatement();
                    try{
                        if(overlapCheck() == true){
                            return;
                        }
                        String title = appointmentTitle.getText();
                        String description = appointmentDescription.getText();
                        String location = appointmentLocation.getText();
                        String type = appointmentType.getText();
                        LocalDate startDate = appointmentStartDate.getValue();
                        LocalTime startTime = (LocalTime) appointmentStartTime.getValue();
                        LocalDate endDate = appointmentEndDate.getValue();
                        LocalTime endTime = (LocalTime) appointmentEndTime.getValue();
                        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
                        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
                        LocalDateTime now = LocalDateTime.now();
                        ZonedDateTime startUtc = startDateTime.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
                        ZonedDateTime endUtc = endDateTime.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
                        ZonedDateTime nowUtc = now.atZone(localZone).withZoneSameInstant(ZoneId.of("UTC"));
                        Timestamp start = Timestamp.valueOf(startUtc.toLocalDateTime());
                        Timestamp end = Timestamp.valueOf(endUtc.toLocalDateTime());
                        String createdBy = User.getActiveUserName();
                        Timestamp createDate = Timestamp.valueOf(nowUtc.toLocalDateTime());
                        Timestamp lastUpdate = Timestamp.valueOf(nowUtc.toLocalDateTime());
                        String lastUpdatedBy = User.getActiveUserName();

                        String insertStatement = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                                "VALUES(" +
                                "'" + title +"'," +
                                "'" + description +"'," +
                                "'" + location +"'," +
                                "'" + type +"'," +
                                "'" + start +"'," +
                                "'" + end +"'," +
                                "'" + createDate +"'," +
                                "'" + createdBy +"'," +
                                "'" + lastUpdate +"'," +
                                "'" + lastUpdatedBy +"'," +
                                "'" + customerId +"'," +
                                "'" + userId +"'," +
                                "'" + contactId +"'" +
                                ")";

                        statement.execute(insertStatement);
                    }catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Appointment");
                        alert.setHeaderText("Not all fields are complete");
                        alert.setContentText("Please make sure all fields are properly completed");
                        alert.showAndWait();
                        return;
                    }
                }catch(SQLException e){
                    // Ignore
                }
                Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
                return;
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

