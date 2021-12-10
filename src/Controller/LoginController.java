package Controller;

import Model.User;
import Utils.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/** This class handles the operations between the classes in the model package and the Login.fxml component */
public class LoginController implements Initializable {
    public Label title;
    public Label instructions;
    public TextField usernameField;
    public TextField passwordField;
    public Label userLocation;
    public Label location;

    /** This method sets the labels and textfields with the correct language depending of the region */
    public void initialize(URL url, ResourceBundle resourceBundle){
        try{
            ResourceBundle rb = ResourceBundle.getBundle("Languages/Login", Locale.getDefault());
            title.setText(rb.getString("title"));
            instructions.setText(rb.getString("instructions"));
            usernameField.setPromptText(rb.getString("username"));
            passwordField.setPromptText(rb.getString("password"));
            userLocation.setText(rb.getString("userLocation"));
            location.setText(String.valueOf(Locale.getDefault()) + " - " + ZoneId.systemDefault());
        }catch(MissingResourceException e){
            // Ignore
        }
        DBUser.getUsers();
    }

    /** This method validates the username and password in the database. Generates an alert accordingly */
    public void onLogin(ActionEvent actionEvent) throws IOException {
        for(User u: User.getAllUsers()){
            if(usernameField.getText().equals(u.getUsername())){
                if(passwordField.getText().equals(u.getPassword())){
                    User.addActiveUser(u);
                    printLoginActivity(true);
                    Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Screen");
                    stage.setScene(scene);
                    stage.show();
                    MainScreenController.initialAppointmentCheck();
                    return;
                }else{
                    ResourceBundle rb = ResourceBundle.getBundle("Languages/Login", Locale.getDefault());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setHeaderText(rb.getString("verify"));
                    alert.setContentText(rb.getString("match"));
                    alert.showAndWait();

                    printLoginActivity(false);
                    passwordField.clear();
                    usernameField.clear();
                    return;
                }
            }
        }
        ResourceBundle rb = ResourceBundle.getBundle("Languages/Login", Locale.getDefault());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login");
        alert.setHeaderText(rb.getString("verify"));
        alert.setContentText(rb.getString("match"));
        alert.showAndWait();

        printLoginActivity(false);
        passwordField.clear();
        usernameField.clear();
    }

    /** This method writes the login attempts to a text file
     * @param success
     */
    public void printLoginActivity(Boolean success) throws IOException {
        String user = usernameField.getText();
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("Username: " + user);
        printWriter.println("On: " + now);
        if(success == true){
            printWriter.println("Attempt to login: Successful");
        }else{
            printWriter.println("Attempt to login: Unsuccessful");
        }
        printWriter.println("------------------------------");
        printWriter.close();
    }

}
