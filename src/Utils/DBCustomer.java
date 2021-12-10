package Utils;

import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/** The DBCustomer class contains a list of customer. As well as methods to populate it and reset it */
public class DBCustomer {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * This method add new customers to the list
     * @param newCustomer
     */
    public static void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

    /**
     * This method queries the database and retrieves customers
     * @return customers
     */
    public static ObservableList<Customer> getCustomers() {
        String divisionName = "";
        try{
            String sql = "SELECT * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                LocalDateTime create = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                try {
                    String query = "SELECT * from first_level_divisions WHERE Division_ID = " + divisionId;
                    PreparedStatement prepStat = DBConnection.getConnection().prepareStatement(query);
                    ResultSet resultSet = prepStat.executeQuery();
                    while (resultSet.next()) {
                        divisionName = resultSet.getString("Division");
                    }
                } catch (SQLException e) {
                    // Ignore
                }

                Customer customer = new Customer(customerId, customerName, address, postalCode, phone, create, createdBy, lastUpdate, lastUpdatedBy, divisionId, divisionName);
                addCustomer(customer);
            }
        }catch(SQLException e){
            //Ignore
        }
        return customers;
    }

    /** This method deletes the content of customers list */
    public static void deleteAllCustomers() {
        customers.clear();
    }

    /**
     * This method queries the database to find a customer's ID
     * @param customerName
     * @return customerId
     */
    public static int findCustomerId(Customer customerName) {
        int customerId = 0;
        try {
            String sql = "SELECT Customer_ID from customers WHERE Customer_Name=" + "'" + customerName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               customerId = rs.getInt("Customer_ID");
            }
        } catch (SQLException e) {
            // Ignore
        }
        return customerId;
    }

    /**
     * This method queries the database to find a customer's name
     * @param customerId
     * @return customerName
     */
    public static String getCustomerName(int customerId){
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