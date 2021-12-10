package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The User class contains the constructor, getters, and setters. As well as an array with all the users, and another one with the active user*/
public class User {
    private int userId;
    private String username;
    private String password;
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<User> activeUser = FXCollections.observableArrayList();

    public User(int userId, String username, String password){
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * @return allUsers
     */
    public static ObservableList<User> getAllUsers(){
        return allUsers;
    }

    /**
     * @param newUser
     */
    public static void addUsers(User newUser){
        allUsers.add(newUser);
    }

    /** This method deletes the content of the allUsers list */
    public static void deleteUsers(){
        allUsers.clear();
    }

    /**
     * @return activeUser
     */
    public static ObservableList<User> getActiveUser(){
        return activeUser;
    }

    /**
     * @return username
     */
    public static String getActiveUserName(){
        String username = "";
        for(User u : getActiveUser()){
            username = u.username;
        }
        return username;
    }

    /**
     * @param newUser
     */
    public static void addActiveUser(User newUser){
        activeUser.add(newUser);
    }

    /**
     * @return userId
     */
    public int getUserId(){
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(int userId){
        this.userId = userId;
    }

    /**
     * @return username
     */
    public String getUsername(){
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword(){
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password){
        this.password = username;
    }

    /**
     * @return userId
     */
    @Override
    public String toString(){
        return String.valueOf(userId);
    }
}
