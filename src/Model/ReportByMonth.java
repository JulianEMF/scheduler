package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The ReportByMonth class contains the constructor, getters and setters. As well as an observable list with all the months that contain appointments */
public class ReportByMonth {
    private static ObservableList<String> months = FXCollections.observableArrayList();
    private String month;

    /**
     * @param month
     */
    public ReportByMonth(String month){
        this.month = month;
    }
    /**
     * @return month
     */
    public String getMonth(){
        return month;
    }

    /**
     * @param month
     */
    public void SetMonth(String month){
        this.month = month;
    }

    /**
     * @return months
     */
    public static ObservableList<String> defineMonths(){
        for (ReportByType report : ReportByType.getAllReportsByType()) {
            if(!months.contains(report.getMonth())){
                months.add(report.getMonth());
            }
        }
        return months;
    }
}
