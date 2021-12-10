package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** The class DBConnection creates the initial connection to the database */
public class DBConnection {
//    private static final String protocol = "jdbc";
//    private static final String vendorName = ":mysql:";
//    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
//    private static final String dbName = "WJ07po4";
//    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
//    private static final String username = "U07po4";
//    private static final String password = "53689096644";
    private static Connection conn = null;

    /**
     * This method establishes the connection with the database
     * return conn;
     * */
    public static Connection startConnection(){
        try{
            Class.forName(MYSQLJDBCDriver);
            String username = "root";
            String password = "yamaha13";
            String url = "jdbc:MySQL://localhost/schedulerdb";
//            conn = DriverManager.getConnection(jdbcURL, username, password);

            conn = DriverManager.getConnection(url, username, password);

            System.out.println("Connection successful!");
        }catch(SQLException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static Connection getConnection(){
        return conn;
    }

    /** This method closes the connection to the database */
    public static void closeConnection(){
        try{
            conn.close();
        }catch(Exception e){
            //Ignore
        }
    }
}
