package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Contact class holds the observable lists that contain all the contacts. As well as the methods to access and edit them. */
public class Contact {
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contact(int contactId, String contactName, String contactEmail){
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return contactId
     */
    public int getContactId(){
        return contactId;
    }

    /**
     * @param contactId
     */
    public void setContactId(int contactId){
        this.contactId = contactId;
    }

    /**
     * @return contactName
     */
    public String getContactName(){
        return contactName;
    }

    /**
     * @param contactName
     */
    public void setContactName(String contactName){
        this.contactName = contactName;
    }

    /**
     * @return contactEmail
     */
    public String getContactEmail(){
        return contactEmail;
    }

    /**
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail){
        this.contactEmail = contactEmail;
    }

    /**
     * @return contactName
     */
    @Override
    public String toString(){
        return(contactName);
    }

}
