import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utils.DBConnection;
import java.sql.SQLException;

/** The Main class is the starting point of the application */
public class Main extends Application {

    /** This class create the primary stage of the application */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/Login.fxml"));
        primaryStage.setTitle("Scheduling Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /** This method calls the method that initialize the connection to the database */
    public static void main(String[] args) throws SQLException {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
