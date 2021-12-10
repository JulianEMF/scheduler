package Model;

import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The FirstLevelDivisions class contains the constructor, the getters, and setters. As well as an observable list with all divisions */
public class FirstLevelDivisions {
    private int id;
    private String division;

    public FirstLevelDivisions(int id, String division){
        this.id = id;
        this.division = division;
    }

    /**
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * @return division
     */
    public String getDivision(){
        return division;
    }

    /**
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * @param division
     */
    public void setDivision(String division){
        this.division = division;
    }

    /**
     * @param countryID
     * return divisionList
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions(int countryID) {
        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from first_level_divisions WHERE COUNTRY_ID = " + countryID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                FirstLevelDivisions D = new FirstLevelDivisions(divisionId, division);
                divisionsList.add(D);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return divisionsList;
    }

    /**
     * @return division
     */
    @Override
    public String toString(){
        return(division);
    }
}
