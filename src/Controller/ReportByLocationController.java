package Controller;

import Model.Location;
import Utils.DBLocation;
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

/** This class handles the operations between the classes in the model package and the ReportByLocation.fxml component */
public class ReportByLocationController implements Initializable {
    public ComboBox<Location> locationSelection;
    public TableView appointmentsByLocation;
    public TableColumn location;
    public TableColumn appointmentId;
    public TableColumn title;
    public TableColumn customer;
    public TableColumn start;
    public TableColumn end;


    /** This method populates the combo box that contains all the locations */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationSelection.setItems(DBLocation.getAllLocations());
    }

    /** This method populates the table view with the requested information */
    public void onLocationSelection() {
        String selectedLocation = locationSelection.getSelectionModel().getSelectedItem().toString();
        appointmentsByLocation.setItems(DBLocation.getLocationByName(selectedLocation));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        customer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
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
