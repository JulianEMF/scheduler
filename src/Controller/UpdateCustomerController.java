package Controller;

import Model.Country;
import Model.Customer;
import Model.FirstLevelDivisions;
import Utils.DBConnection;
import Utils.DBQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/** This class handles the operations between the classes in the model package and the UpdateCustomer.fxml component */
public class UpdateCustomerController {
    public TextField updateCustomerId;
    public TextField updateCustomerName;
    public TextField updateAddress;
    public TextField updatePostalCode;
    public TextField updatePhoneNumber;
    public ComboBox updateCountry;
    public ComboBox updateDivision;
    public int divisionId;

    /**
     * @param customer
     * This method queries the database for the requested customer */
    public void fetchCustomer(Customer customer) {
        ObservableList<Country> countriesList = Country.getAllCountries();

        String division = "";
        int countryId = 0;
        String countryName;
        divisionId = customer.getDivisionId();

        // Queries the database to get the Division Name, country ID and country name
        try {
            String sql = "SELECT * from first_level_divisions WHERE Division_ID=" + "'" + customer.getDivisionId() + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                division = rs.getString("Division");
                countryId = rs.getInt("COUNTRY_ID");
            }
            try {
                String sqlCountry = "SELECT Country from countries WHERE Country_ID=" + "'" + countryId + "'";
                PreparedStatement psCountry = DBConnection.getConnection().prepareStatement(sqlCountry);
                ResultSet rsCountry = psCountry.executeQuery();
                while (rsCountry.next()) {
                    countryName = rsCountry.getString("Country");
                    updateCountry.setValue(countryName);
                    ObservableList<FirstLevelDivisions> divisionsList = FirstLevelDivisions.getAllDivisions(countryId);
                    updateDivision.setItems(divisionsList);
                    updateDivision.setValue(division);
                }

            } catch (SQLException e) {
                // Ignore
            }
        } catch (SQLException e) {
            //Ignore
        }

        // Fill out the fields
        updateCustomerId.setText(String.valueOf(customer.getCustomerId()));
        updateCustomerName.setText(String.valueOf(customer.getCustomerName()));
        updateAddress.setText(String.valueOf(customer.getAddress()));
        updatePostalCode.setText(String.valueOf(customer.getPostalCode()));
        updatePhoneNumber.setText(String.valueOf(customer.getPhoneNumber()));
        updateCountry.setItems(countriesList);
    }

    /** This method populates the division combo box based on the selected country */
    public void onCountrySelection() {
        if (updateCountry == null) {
            // IGNORE FOR NOW
        } else {
            Country selectedCountry = (Country) updateCountry.getSelectionModel().getSelectedItem();
            updateDivision.setItems(FirstLevelDivisions.getAllDivisions(selectedCountry.getId()));
        }
    }

    /** This method retrieves the divisionId after a division has been selected*/
    public void onDivisionSelection() {
        FirstLevelDivisions selectedDivision = (FirstLevelDivisions) updateDivision.getSelectionModel().getSelectedItem();
        try {
            String sql = "SELECT Division_ID from first_level_divisions WHERE Division=" + "'" + selectedDivision + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                divisionId = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {
            //Ignore
        }
    }

    /** This method handles the update process of a customer. It extracts the values from the form and updates them in the database */
    public void onUpdateButton(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            Connection conn = DBConnection.getConnection();
            DBQuery.setStatement((conn)); //Create statement object
            Statement statement = DBQuery.getStatement();
            try{
                String customerName = updateCustomerName.getText();
                String address = updateAddress.getText();
                String postalCode = updatePostalCode.getText();
                String phoneNumber = updatePhoneNumber.getText();
                LocalDate createDate = LocalDate.now();
                String createdBy = "Admin";
                LocalDate lastUpdate = LocalDate.now();
                String lastUpdateBy = "Admin";

                String updateStatement = "UPDATE customers SET " +
                        "Customer_Name= " + "'" + customerName + "', " +
                        "Address= " + "'" + address + "', " +
                        "Postal_Code= " + "'" + postalCode + "', " +
                        "Phone= " + "'" + phoneNumber + "', " +
                        "Created_By= " + "'" + createdBy + "', " +
                        "Division_ID= " + "'" + divisionId + "'" +
                        " WHERE Customer_ID= " + updateCustomerId.getText();

                statement.execute(updateStatement);

                // Confirm rows affected
//                if (statement.getUpdateCount() > 0) {
//                    System.out.println(statement.getUpdateCount() + " rows updated");
//                    ;
//                } else {
//                    System.out.println("No change");
//                }
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Customer");
                alert.setHeaderText("Not all fields are complete");
                alert.setContentText("Please make sure all fields are properly completed");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            // Ignore
        }

        // Clear all fields
        updateCustomerId.clear();
        updateCustomerName.clear();
        updateAddress.clear();
        updatePostalCode.clear();
        updatePhoneNumber.clear();

        // To Main Screen
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /** This method handles the cancel button function. It redirects to the main screen */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}