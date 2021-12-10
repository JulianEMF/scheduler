package Model;

import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The country class contains the constructor, getters, and setters. As well as a method to obtain a list of all the countries  */
public class Country {
    private int id;
    private String name;
    private String country;

    public Country(int id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * @param country
     */
    public void setItems(String country){
        this.country = country;
    }

    /**
     * @return countriesList
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryId, countryName);
                countriesList.add(C);
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
        return countriesList;
    }

    /**
     * @return name
     */
    @Override
    public String toString(){
        return(name);
    }
}
