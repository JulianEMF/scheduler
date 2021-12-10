package Utils;

import Model.User;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** The DBUser class contains a list with users, as well as a method to populate it */
public class DBUser {

    /**
     * This method queries the database for all users
     * @return User.getAllUsers()
     */
    public static ObservableList<User> getUsers() {
        try{
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");

                User newUser = new User(userId, username, password);
                User.addUsers(newUser);
            }
        }catch(SQLException e){
            // Ignore
        }
        return User.getAllUsers();
    }

    /**
     * This method queries the database to find the user's ID based on their name
     * @param userName
     * @return userID
     */
    public static int findUserId(User userName) {
        int userId = 0;
        try {
            String sql = "SELECT User_ID from users WHERE User_Name=" + "'" + userName + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userId = rs.getInt("User_ID");
            }
        } catch (SQLException e) {
            // Ignore
        }
        return userId;
    }
}
