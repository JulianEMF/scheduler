package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The ReportByType class contains the constructor, getters and setters. As well as an observable list with all the reports by type and another one for unique reports */
public class ReportByType {
    private static ObservableList<ReportByType> allReportsByType = FXCollections.observableArrayList();
    private static ObservableList<ReportByType> uniqueReports = FXCollections.observableArrayList();
    private String month;
    private String type;
    private int total;

    public ReportByType ( String month, String type, int total){
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * @return month
     */
    public String getMonth(){
        return month;
    }

    /**
     * @param
     *
     */
    public void setMonth(String month){
        this.month = month;
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
     * @return total
     */
    public int getTotal(){
        return total;
    }

    /**
     * @param total
     */
    public void setTotal(int total){
        this.total = total;
    }

    /**
     * @param newReport
     */
    public static void addReportByType(ReportByType newReport){
        allReportsByType.add(newReport);
    }

    /**
     * @return allReportsByType
     */
    public static ObservableList<ReportByType> getAllReportsByType(){
        return allReportsByType;
    }

    /**
     * @param newReport
     */
    public static void addUniqueReport(ReportByType newReport){
        uniqueReports.add(newReport);
    }

    /**
     * @return uniqueReports
     */
    public static ObservableList<ReportByType> getUniqueReports(){
        return uniqueReports;
    }

    /** This method deletes the content of the allReportsByType list */
    public static void deleteReports(){
        allReportsByType.clear();
    }

    /** This method deletes the content of the uniqueReports list */
    public static void deleteUniqueReports(){
        uniqueReports.clear();
    }
}
