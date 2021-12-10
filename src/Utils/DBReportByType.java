package Utils;

import Model.ReportByType;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/** The DBReportByType class contains a ReportsByType list, with its method to populate it*/
public class DBReportByType {
    /**
     *  This method queries the database for appointments by type and start
     * @return ReportByType
     */
    public static ObservableList<ReportByType> getReportsByType() {
        try {
            String sql = "SELECT Type, Start FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = rs.getDate("Start").toLocalDate();
                String type = rs.getString("Type");
                int total = 0;
                String month = date.getMonth().toString();
                ReportByType newReportByType = new ReportByType(month, type, total);
                ReportByType.addReportByType(newReportByType);
            }
        } catch (SQLException e) {
            // Ignore
        }
        return ReportByType.getAllReportsByType();
    }


    }





