package Controller;

import Model.Appointment;
import Model.Customer;
import Model.User;
import Utils.DBConnection;
import Utils.DBCustomer;
import Utils.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import Utils.DBAppointment;

/** This method handles the operations between the classes in the model package and the MainScreen.fxml component. */
public class MainScreenController implements Initializable {
    // TableView for all appointments
    public TableView allSchedule;
    public TableColumn allAppointmentId;
    public TableColumn allTitle;
    public TableColumn allDescription;
    public TableColumn allLocation;
    public TableColumn allUserId;
    public TableColumn allContact;
    public TableColumn allType;
    public TableColumn allStartDateAndTime;
    public TableColumn allEndDateAndTime;
    public TableColumn allCustomerId;
    // TableView for customers
    public TableView customersTableView;
    public TableColumn customerIdColumn;
    public TableColumn customerNameColumn;
    public TableColumn addressColumn;
    public TableColumn postalCodeColumn;
    public TableColumn firstLevelDivisionColumn;
    public TableColumn phoneNumberColumn;

    // TableView for month appointments
    public TableView monthlySchedule;
    public TableColumn appointmentIdColumn;
    public TableColumn titleColumn;
    public TableColumn descriptionColumn;
    public TableColumn locationColumn;
    public TableColumn userIdColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;
    public TableColumn startDateAndTimeColumn;
    public TableColumn endDateAndTimeColumn;
    public TableColumn scheduleCustomerIdColumn;

    // TableView for week appointments
    public TableView weeklySchedule;
    public TableColumn weeklyAppointmentId;
    public TableColumn weeklyTitle;
    public TableColumn weeklyDescription;
    public TableColumn weeklyLocation;
    public TableColumn weeklyUserId;
    public TableColumn weeklyContact;
    public TableColumn weeklyType;
    public TableColumn weeklyStartDateAndTime;
    public TableColumn weeklyEndDateAndTime;
    public TableColumn weeklyCustomerId;

    // Determines what tab is currently active
    public String scheduleView = "all";

    /** This method populates the customers table */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        // Reset the list of customers
        DBCustomer.deleteAllCustomers();
        // Adds customer again when the main screen is loaded
        customersTableView.setItems(DBCustomer.getCustomers());
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        firstLevelDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    /** This method checks for appointments upcoming in the next 15 minutes after login */
    public static void initialAppointmentCheck(){
        for(Appointment a : Appointment.getAppointmentsAll()){
            Duration duration = Duration.between(a.getStart(), ZonedDateTime.now());
            int durationInSeconds = Integer.parseInt(String.valueOf(duration.getSeconds() * -1));
            if((durationInSeconds < 900) && (durationInSeconds>0)){
                Alert alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
                alertConfirmation.setTitle("Upcoming Appointment");
                alertConfirmation.setHeaderText("There is an appointment coming up!");
                alertConfirmation.setContentText("The appointment with ID: " + a.getAppointmentId() + " is scheduled for: " + a.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                alertConfirmation.showAndWait();
                return;
            }
        }
        Alert alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
        alertConfirmation.setTitle("Upcoming Appointment");
        alertConfirmation.setHeaderText("No appointments");
        alertConfirmation.setContentText("There are no appointments coming up in the next 15 minutes");
        alertConfirmation.showAndWait();
    }

    /** This method populates the Table view that shows all the appointments */
    public void onAllScheduleSelection(){
        scheduleView = "all";
        // Resets the Observable list that contains all appointments
        Appointment.deleteAllAppointmentsAll();
        // Adds monthly appointments again when the main screen is loaded
        DBAppointment.addAppointmentsDataAll();
        allSchedule.setItems(Appointment.getAppointmentsAll());
        allAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        allTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        allDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        allLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        allUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        allContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        allType.setCellValueFactory(new PropertyValueFactory<>("type"));
        allStartDateAndTime.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        allEndDateAndTime.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        allCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /** This method populates the Table view that shows the weekly appointments */
    public void onWeeklyScheduleSelection(){
        scheduleView = "week";
        // Resets the Observable list that contains all appointments
        Appointment.deleteAllAppointmentsWeekly();

        // Adds weekly appointments
        DBAppointment.addAppointmentsDataWeekly();
        weeklySchedule.setItems(Appointment.getAppointmentsWeekly());
        weeklyAppointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        weeklyTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        weeklyDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        weeklyLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        weeklyUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        weeklyContact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        weeklyType.setCellValueFactory(new PropertyValueFactory<>("type"));
        weeklyStartDateAndTime.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        weeklyEndDateAndTime.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        weeklyCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /** This method populates the Table view that shows the monthly appointments */
    public void onMonthlyScheduleSelection(){
        scheduleView = "month";
        // Resets the Observable list that contains all appointments
        Appointment.deleteAllAppointmentsMonthly();
        // Adds monthly appointments again when the main screen is loaded
        DBAppointment.addAppointmentsDataMonthly();
        monthlySchedule.setItems(Appointment.getAppointmentsMonthly());
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        endDateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        scheduleCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /** This method redirects the scene to add an appointment */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /** This method redirects the scene to update an appointment */
    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException {
        if(scheduleView == "all") {
            Appointment selectedAppointment = (Appointment) allSchedule.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
            loader.load();
            UpdateAppointmentController AppointmentController = loader.getController();
            AppointmentController.fetchAppointments(selectedAppointment);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(scene));
            stage.show();

        }else if(scheduleView == "month"){
            Appointment selectedAppointment = (Appointment) monthlySchedule.getSelectionModel().getSelectedItem();
            if(selectedAppointment == null) {
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
            loader.load();
            UpdateAppointmentController AppointmentController = loader.getController();
            AppointmentController.fetchAppointments(selectedAppointment);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(scene));
            stage.show();
        }else{
            Appointment selectedAppointment = (Appointment) weeklySchedule.getSelectionModel().getSelectedItem();
            if(selectedAppointment == null) {
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
            loader.load();
            UpdateAppointmentController AppointmentController = loader.getController();
            AppointmentController.fetchAppointments(selectedAppointment);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setTitle("Update Appointment");
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** This method deletes the selected appointment */
    public void onDeleteAppointment(ActionEvent actionEvent) throws  IOException{
        if(scheduleView == "all") {
            Appointment selectedAppointment = (Appointment) allSchedule.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                return;
            }
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete");
                alert.setHeaderText("Deleting the " + selectedAppointment.getTitle() + " appointment");
                alert.setContentText("Are you sure you want to delete the appointment with ID: " + selectedAppointment.getAppointmentId() + " and of type " + selectedAppointment.getType() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Connection conn = DBConnection.getConnection();
                    DBQuery.setStatement((conn)); //Create statement object
                    Statement statement = DBQuery.getStatement();
                    String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = " + selectedAppointment.getAppointmentId();
                    statement.execute(deleteStatement);
                    onAllScheduleSelection();

                    Alert alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
                    alertConfirmation.setTitle("Appointment Deletion");
                    alertConfirmation.setHeaderText("Success!");
                    alertConfirmation.setContentText("The appointment with ID: " + selectedAppointment.getAppointmentId() + " has been deleted");
                    alertConfirmation.showAndWait();
                }
            } catch (SQLException e) {
                // Ignore
            }
        }else if(scheduleView == "month"){
            Appointment selectedAppointment = (Appointment) monthlySchedule.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                return;
            }
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete");
                alert.setHeaderText("Deleting the " + selectedAppointment.getTitle() + " appointment");
                alert.setContentText("Are you sure you want to delete the appointment with ID: " + selectedAppointment.getAppointmentId() + " and of type " + selectedAppointment.getType() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Connection conn = DBConnection.getConnection();
                    DBQuery.setStatement((conn)); //Create statement object
                    Statement statement = DBQuery.getStatement();
                    String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = " + selectedAppointment.getAppointmentId();
                    statement.execute(deleteStatement);
                    onMonthlyScheduleSelection();

                    Alert alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
                    alertConfirmation.setTitle("Appointment Deletion");
                    alertConfirmation.setHeaderText("Success!");
                    alertConfirmation.setContentText("The appointment with ID: " + selectedAppointment.getAppointmentId() + " has been deleted");
                    alertConfirmation.showAndWait();
                }
            } catch (SQLException e) {
                // Ignore
            }
        }else{
            Appointment selectedAppointment = (Appointment) weeklySchedule.getSelectionModel().getSelectedItem();
            if (selectedAppointment == null) {
                return;
            }
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete");
                alert.setHeaderText("Deleting the " + selectedAppointment.getTitle() + " appointment");
                alert.setContentText("Are you sure you want to delete the appointment with ID: " + selectedAppointment.getAppointmentId() + " and of type " + selectedAppointment.getType() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Connection conn = DBConnection.getConnection();
                    DBQuery.setStatement((conn)); //Create statement object
                    Statement statement = DBQuery.getStatement();
                    String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = " + selectedAppointment.getAppointmentId();
                    statement.execute(deleteStatement);
                    onWeeklyScheduleSelection();

                    Alert alertConfirmation = new Alert(Alert.AlertType.INFORMATION);
                    alertConfirmation.setTitle("Appointment Deletion");
                    alertConfirmation.setHeaderText("Success!");
                    alertConfirmation.setContentText("The appointment with ID: " + selectedAppointment.getAppointmentId() + " has been deleted");
                    alertConfirmation.showAndWait();
                }
            } catch (SQLException e) {
                // Ignore
            }
        }
    }

    /** This method redirects the scene to add a customer */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /** This method redirects the scene to update a customer */
    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        Customer selectedCustomer = (Customer) customersTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/UpdateCustomer.fxml"));
        loader.load();

        UpdateCustomerController CustomerController = loader.getController();
        CustomerController.fetchCustomer(selectedCustomer);

        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setTitle("Modify Customer");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** This method deletes the selected customer */
    public void onDeleteCustomer(ActionEvent actionEvent) throws IOException{
        Customer selectedCustomer = (Customer) customersTableView.getSelectionModel().getSelectedItem();
        for(Appointment a : Appointment.getAppointmentsAll()) {
            if (a.getCustomerId() == selectedCustomer.getCustomerId()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Customer");
                alert.setHeaderText("Foreign key constraint");
                alert.setContentText("All the appointments for " + selectedCustomer.getCustomerName() + " must be deleted before deleting this customer");
                alert.showAndWait();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Deletion Confirmation");
        alert.setContentText("Are you sure you want to delete the customer: " + selectedCustomer.getCustomerName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                Connection conn = DBConnection.getConnection();
                DBQuery.setStatement((conn)); //Create statement object
                Statement statement = DBQuery.getStatement();
                String deleteStatement = "DELETE FROM customers WHERE Customer_ID = " + selectedCustomer.getCustomerId();
                statement.execute(deleteStatement);
                customersTableView.getItems().clear();
                customersTableView.setItems(DBCustomer.getCustomers());
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Delete Customer");
                confirmation.setHeaderText("Customer deleted");
                confirmation.setContentText("The customer: " + selectedCustomer.getCustomerName() + " has been deleted");
                confirmation.showAndWait();
                return;
            }catch(SQLException e){
                // Ignore
            }
        }
        if(selectedCustomer == null) {
            return;
        }
    }

    /** This method redirects the scene to reports by type */
    public void onToReportsByType(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportAppointmentsByType.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Report By Type");
        stage.setScene(scene);
        stage.show();
    }

    /** This method redirects the scene to reports by contact */
    public void onToContactAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportScheduleByContact.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Report By Customer");
        stage.setScene(scene);
        stage.show();
    }

    /** This method redirects the scene to reports by country */
    public void onToReportsByLocation(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/ReportByLocation.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Report By Country");
        stage.setScene(scene);
        stage.show();
    }
}
