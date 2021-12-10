package Controller;

import Model.Country;
import Model.FirstLevelDivisions;
import Utils.DBQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utils.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

/** This class handles the operations between the classes in the model package and the AddCustomer.fxml component */
public class AddCustomerController implements Initializable {
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField addressField;
    public TextField postalCodeField;
    public TextField phoneNumberField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<FirstLevelDivisions> divisionComboBox;
    public int divisionId;

    /** This method creates a list with the available countries from the database. Sets a comboBox with the list */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // FETCH ALL COUNTRIES
        ObservableList<Country> countriesList = Country.getAllCountries();
        countryComboBox.setPromptText("Please select a Country...");
        countryComboBox.setItems(countriesList);
    }

    /** This method populates the divisions comboBox depending on the selected country */
    public void onCountrySelection(){
        divisionComboBox.setDisable(false);
        divisionComboBox.setPromptText("Please select a Division...");

        if(countryComboBox == null){
            // Ignore
        }else{
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            divisionComboBox.setItems(FirstLevelDivisions.getAllDivisions(selectedCountry.getId()));
        }
    }

    /** This method retrieves the division ID based on the selected division */
    public void onDivisionSelection(){
        FirstLevelDivisions selectedDivision = divisionComboBox.getSelectionModel().getSelectedItem();
        try{
            String sql = "SELECT Division_ID from first_level_divisions WHERE Division=" + "'" + selectedDivision +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                divisionId = rs.getInt("Division_ID");
            }
        }catch(SQLException e){
            //Ignore
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

    /** This method handles the save process of a customer. It extracts the values from the form and insert them in the database */
    public void onSaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        try{
            Connection conn = DBConnection.getConnection();
            DBQuery.setStatement((conn)); //Create statement object
            Statement statement = DBQuery.getStatement();
            try{
                String customerName = customerNameField.getText();
                String address = addressField.getText();
                String postalCode = postalCodeField.getText();
                String phoneNumber = phoneNumberField.getText();
                String createdBy = "Admin";
                String lastUpdateBy = "Admin";

                String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +
                        "VALUES(" +
                        "'" + customerName +"'," +
                        "'" + address +"'," +
                        "'" + postalCode +"'," +
                        "'" + phoneNumber +"'," +
                        "'" + createdBy +"'," +
                        "'" + lastUpdateBy +"'," +
                        "'" + divisionId +"'" +
                        ")";

                statement.execute(insertStatement);

            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Customer");
                alert.setHeaderText("Not all fields are complete");
                alert.setContentText("Please make sure all fields are properly completed");
                alert.showAndWait();
                return;
            }

        }catch(NumberFormatException e) {
            // Ignore
        }

        // Clear all fields
        customerIdField.clear();
        customerNameField.clear();
        addressField.clear();
        postalCodeField.clear();
        phoneNumberField.clear();
        divisionComboBox.setDisable(true);

        // To Main Screen
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
