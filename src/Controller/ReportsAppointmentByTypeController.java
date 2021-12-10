package Controller;

import Model.ReportByMonth;
import Model.ReportByType;
import Utils.DBReportByType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/** This method handles the operations between the classes in the model package and the ReportsByAppointment.fxml component. */
public class ReportsAppointmentByTypeController implements Initializable {
    // Table View for reports
    public TableView reportTypeTableView;
    public TableColumn reportTypeMonth;
    public TableColumn reportTypeAppointment;
    public TableColumn reportTypeTotal;
    public ComboBox<String> reportMonthSelection;

    /** This method populates the Combo box with the months were appointments exist */
    public void initialize(URL url, ResourceBundle resourceBundle){
        DBReportByType.getReportsByType();
        reportMonthSelection.setItems(ReportByMonth.defineMonths());
}
    /** This method populates the table view with the requested information
     *  The lambda expression here iterates over all the reports by type and adds them to the types list
     *  Using this lambda expression allows to iterate over all the reports by type, while using a simpler way than a for loop
     */
    public void onMonthSelection(ActionEvent actionEvent){
        // Resets the list of unique records
        ReportByType.deleteUniqueReports();
        if(reportMonthSelection == null){
            return;
        }else{
            // Create the variable for the selected month
            String selectedMonth = reportMonthSelection.getSelectionModel().getSelectedItem();
            List<String> types = new LinkedList<>();

            ReportByType.getAllReportsByType().forEach((report) -> {
                if(report.getMonth() == selectedMonth){
                    types.add(report.getType());
                }
            });

            // Create a unique set of values for the "types" list
            Set<String> uniqueSet = new HashSet<String>(types);
            // Iterate over the unique set counting the frequency of the values
            for (String temp : uniqueSet) {
                int total = Collections.frequency(types, temp);
                ReportByType newReport = new ReportByType(selectedMonth, temp, total);
                ReportByType.addUniqueReport(newReport);
            }
            // Adds all the unique values to the table view and columns
            reportTypeTableView.setItems(ReportByType.getUniqueReports());
            reportTypeMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            reportTypeAppointment.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportTypeTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        }
    }

    /** This method handles the cancel button function. It redirects to the main screen */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        // Reset all the reports
        ReportByType.deleteReports();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
