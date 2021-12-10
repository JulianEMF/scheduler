package Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Contact;

/** The class DBContact contains a list with all the contacts. As well as methods to populate it */
public class DBContact {

    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();

    /**
     * This method queries the database for contacts
     * @return contactsList
     */
    public static ObservableList<Contact> getContacts(){
        ObservableList<Contact> contactsList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact newContact = new Contact(contactId, contactName, email);
                contactsList.add(newContact);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return contactsList;
    }

    /**
     * This method queries the database for contacts
     * @return contacts
     */
    public static ObservableList<Contact> addContactsData(){
        try{
            String sql = "SELECT * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contact newContact = new Contact(contactId, contactName, contactEmail);
                contacts.add(newContact);
            }
        }catch(SQLException e){
            //Ignore
        }
        return contacts;
    }

    /** This method deletes the content of the contacts list */
    public static void deleteAllContacts() {
        contacts.clear();
    }

    /**
     * This method queries the database to find a contactID
     * @return contactId
     */
    public static int findContactId(String contactName) {
        int contactId = 0;
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
    }
}
