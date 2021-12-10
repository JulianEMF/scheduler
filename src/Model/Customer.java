package Model;

import java.time.LocalDateTime;

/** The country class contains the constructor, getters, and setters */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String divisionName;

    public Customer(int customerId, String customerName, String address, String postalCode, String phoneNumber, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionId, String divisionName) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }

    /**
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return postalCode
     */
    public String getPostalCode(){
        return postalCode;
    }

    /**
     * @param postalCode
     */
    public void setPostalCode(String postalCode){
        this.postalCode = postalCode;
    }

    /**
     * @return phoneNumber
     */
    public String getPhoneNumber(){
        return phoneNumber;
    }

    /**
     * @return createDate
     */
    public LocalDateTime getCreateDate(){
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
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
     * @return lastUpdateBy
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
     * @return lastUpdate
     */
    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }

    /**
     * @param lastUpdate
     */
    public void setLastUpdate(LocalDateTime lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return divisionId
     */
    public int getDivisionId(){
        return divisionId;
    }

    /**
     * @param division
     */
    public void setDivisionId(int division){
        this.divisionId = division;
    }

    /**
     * @return divisionName
     */
    public String getDivisionName(){
        return divisionName;
    }

    /**
     * @param divisionName
     */
    public void setDivisionName(String divisionName){
        this.divisionName = divisionName;
    }

    /**
     * @return customerId
     */
    @Override
    public String toString(){
        return String.valueOf(customerId);
    }

}