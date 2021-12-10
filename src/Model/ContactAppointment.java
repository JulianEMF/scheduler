package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The ContactAppointment class holds the observable lists that contain all the appointmentsByContact. As well as the methods to access and edit them. */
public class ContactAppointment {
    private static ObservableList<ContactAppointment> appointmentsByContact = FXCollections.observableArrayList();
    private int appointmentId;
    private String title;
    private String type;
    private String description;
    private String start;
    private String end;
    private int customerId;

    public ContactAppointment(int appointmentId, String title, String type, String description, String start, String end, int customerId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    /**
     * @return appointmentId
     * */
    public int getAppointmentId(){
        return appointmentId;
    }

    /**
     * @param appointmentId
     * */
    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }

    /**
     * @return title
     * */
    public String getTitle(){
        return title;
    }

    /**
     * @param title
     * */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * @return type
     * */
    public String getType(){
        return type;
    }

    /**
     * @param type
     * */
    public void setType(String type){
        this.type = type;
    }

    /**
     * @return description
     * */
    public String getDescription(){
        return description;
    }

    /**
     * @param description
     * */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * @return start
     * */
    public String getStart(){
        return start;
    }

    /**
     * @param start
     * */
    public void setStart(String start){
        this.start = start;
    }

    /**
     * @return end
     * */
    public String getEnd(){
        return end;
    }

    /**
     * @param end
     * */
    public void setEnd(String end){
        this.end = end;
    }

    /**
     * @return customerId
     * */
    public int getCustomerId(){
        return customerId;
    }

    /**
     * @param customerId
     * */
    public void setCustomerId(int customerId){
        this.customerId = ContactAppointment.this.customerId;
    }

}
