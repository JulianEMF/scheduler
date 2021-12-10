package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/** The Appointment class holds the observable lists that contain all the appointments. As well as the methods to access and edit them. */
public class Appointment{
    private static ObservableList<Appointment> appointmentsAll = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsMonthly = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsWeekly = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointmentsByCustomersId = FXCollections.observableArrayList();
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private ZonedDateTime create;
    private String formattedStart;
    private String formattedEnd;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    public Appointment(int appointmentId, String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, ZonedDateTime create, String formattedStart, String formattedEnd, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, String contactName){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create = create;
        this.formattedStart = formattedStart;
        this.formattedEnd = formattedEnd;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /** This method adds appointments to the appointmentsAll observable list.
     @param newAppointment the new appointment to be added
     */
    public static void addAppointmentsAll(Appointment newAppointment){
        appointmentsAll.add(newAppointment);
    }

    /** This method adds appointments to the appointmentsMonthly observable list.
     @param newAppointment the new appointment to be added
     */
    public static void addAppointmentsMonthly(Appointment newAppointment){
        appointmentsMonthly.add(newAppointment);
    }

    /** This method adds appointments to the appointmentsWeekly observable list.
     @param newAppointment the new appointment to be added
     */
    public static void addAppointmentsWeekly(Appointment newAppointment){
        appointmentsWeekly.add(newAppointment);
    }

    /** This method adds appointments to the appointmentsByCustomersId observable list.
     @param newAppointment the new appointment to be added
     */
    public static void addAppointmentsByCustomersId(Appointment newAppointment){
        appointmentsByCustomersId.add(newAppointment);
    }

    /** This method returns the list appointmentsAll
     @return ObservableList<Appointment> appointmentsAll
     */
    public static ObservableList<Appointment> getAppointmentsAll(){
        return appointmentsAll;
    }

    /** This method returns the list appointmentsMonthly
     @return ObservableList<Appointment> appointmentsMonthly
     */
    public static ObservableList<Appointment> getAppointmentsMonthly(){
        return appointmentsMonthly;
    }

    /** This method returns the list appointmentsWeekly
     @return ObservableList<Appointment> appointmentsWeekly
     */
    public static ObservableList<Appointment> getAppointmentsWeekly(){
        return appointmentsWeekly;
    }

    /** This method returns the list appointmentsByCustomersId
     @return ObservableList<Appointment> appointmentsByCustomersId
     */
    public static ObservableList<Appointment> getAppointmentsByCustomerId() {
        return appointmentsByCustomersId;
    }

    /** This method deletes the content of the list appointmentsAll */
    public static void deleteAllAppointmentsAll(){
        appointmentsAll.clear();
    }

    /** This method deletes the content of the list appointmentsMonthly */
    public static void deleteAllAppointmentsMonthly(){
        appointmentsMonthly.clear();
    }

    /** This method deletes the content of the list appointmentsWeekly */
    public static void deleteAllAppointmentsWeekly(){
        appointmentsWeekly.clear();
    }

    /** This method deletes the content of the list appointmentsByCustomersId */
    public static void deleteAllAppointmentsByCustomerId(){
        appointmentsByCustomersId.clear();
    }

    /**
     * @return appointmentId
     */
    public int getAppointmentId(){
        return appointmentId;
    }

    /**
     * @param appointmentId
     */
    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }

    /**
     * @return title
     */
    public String getTitle(){
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * @return description
     */
    public String getDescription(){
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * @return location
     */
    public String getLocation(){
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * @return type
     */
    public String getType(){
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * @return start.toLocalDate()
     */
    public LocalDate getStartDate(){
        return start.toLocalDate();
    }

    /**
     * @return start.toLocalTime
     */
    public LocalTime getStartTime(){
        return start.toLocalTime();
    }

    /**
     * @return end.toLocalDate();
     */
    public LocalDate getEndDate(){
        return end.toLocalDate();
    }

    /**
     * @return end.toLocalTime();
     */
    public LocalTime getEndTime(){
        return end.toLocalTime();
    }

    /**
     * @param start
     */
    public void setStart(ZonedDateTime start){
        this.start = start;
    }

    /**
     * @return start
     */
    public ZonedDateTime getStart(){
        return start;
    }

    /**
     * @param end
     */
    public void setEnd(ZonedDateTime end){
        this.end = end;
    }

    /**
     * @return end
     */
    public ZonedDateTime getEnd(){
        return end;
    }

    /**
     * @return create.toLocalDate();
     */
    public LocalDate getCreateDate(){
        return create.toLocalDate();
    }

    /**
     * @return create.toLocalTime();
     */
    public LocalTime getCreateTime(){
        return create.toLocalTime();
    }

    /**
     * @return formattedStart
     */
    public String getFormattedStart(){
        return formattedStart;
    }

    /**
     * @param formattedStart
     */
    public void setFormattedStart(String formattedStart){
        this.formattedStart = formattedStart;
    }

    /**
     * @return formattedEnd
     */
    public String getFormattedEnd(){
        return formattedEnd;
    }

    /**
     * @param formattedEnd
     */
    public void setFormattedEnd(String formattedEnd){
        this.formattedEnd = Appointment.this.formattedEnd;
    }

    /**
     * @return createdBy
     */
    public String getCreatedBy(){
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * @return lastUpdatedBy
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return customerId
     */
    public int getCustomerId(){
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
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
     * @return userId
     */
    public int getUserId(){
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(int userId){
        this.userId = userId;
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
}
