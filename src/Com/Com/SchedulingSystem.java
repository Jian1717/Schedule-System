package Com;
import Com.DAO.DBconnection;
import Com.Model.Appointment;
import Com.Model.Customer;
import Com.View_Controller.LoginFormController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SchedulingSystem extends Application  {
    private ObservableList<Customer> listOfAllCustomer;
    private ObservableList<Appointment> listOfAllAppointment;
    public SchedulingSystem(){
    }

    public static void main(String[] args) throws SQLException {
        new DBconnection();
        launch(args);
        DBconnection.closeConnection();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulingSystem.class.getResource("/Com/View_Controller/LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        LoginFormController controller = new LoginFormController();
        primaryStage.show();
    }

}
