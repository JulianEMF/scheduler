package Model;

/** The location class contains the constructor, getters, and setters */
public class Location {
    private String location;
    private int appointmentId;
    private String title;
    private String customer;
    private String start;
    private String end;
    private String locationName;

    public Location(String location, int appointmentId, String title, String customer, String start, String end){
        this.location = location;
        this.appointmentId = appointmentId;
        this.title = title;
        this.customer = customer;
        this.start = start;
        this.end = end;
    }

    /**
     * @param locationName
     */
    public Location(String locationName) {
        this.locationName = locationName;
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
     * @return customer
     */
    public String getCustomer(){
        return customer;
    }

    /**
     * @param customer
     */
    public void setCustomer(String customer){
        this.customer = customer;
    }

    /**
     * @return start
     */
    public String getStart(){
        return start;
    }

    /**
     * @param start
     */
    public void setStart(String start){
        this.start = start;
    }

    /**
     * @return end
     */
    public String getEnd(){
        return end;
    }

    /**
     * @param end
     */
    public void setEnd(String end){
        this.end = end;
    }

    /**
     * @return locationName
     */
    public String getLocationName(){
        return locationName;
    }

    /**
     * @param locationName
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     * @return locationName
     */
    @Override
    public String toString(){
        return(locationName);
    }

}
