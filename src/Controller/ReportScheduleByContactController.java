package Controller;

import Interfaces.FindContactID;
import Model.Contact;
import Utils.DBContact;
import Utils.DBContactAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class handles the operations between the classes in the model package and the ReportScheduleByContact.fxml component */
public class ReportScheduleByContactController implements Initializable {
    public ComboBox<Contact> contactSelection;
    public TableView appointmentsByCustomer;
    public TableColumn appointmentId;
    public TableColumn title;
    public TableColumn type;
    public TableColumn description;
    public TableColumn start;
    public TableColumn end;
    public TableColumn customerId;

    /** This method populates the combo box with a list of contacts */
    public void initialize(URL url, ResourceBundle resourceBundle){
        contactSelection.setItems(DBContact.getContacts());
    }

    /** This method populates the table view with the requested information */
    public void onContactSelection(){
        String contactName =  contactSelection.getSelectionModel().getSelectedItem().toString();
        int contactId = DBContact.findContactId(contactName);
        appointmentsByCustomer.setItems(DBContactAppointment.getAppointmentsByContactId(contactId));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("title"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
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
