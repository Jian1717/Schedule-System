package Com.View_Controller;
import Com.DAO.DBHelper;
import Com.Helper.LogHelper;
import Com.Helper.TimeHelper;
import Com.Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**LoginFormController handle all the event, action and GUI components that happened in LoginForm. */
public class LoginFormController implements Initializable {
    private LogHelper loginLogger;
    @FXML
    private ComboBox<String> languageDropdown;
    @FXML
    private Label locationDisplayLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label languageLabel;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextfield;
    @FXML
    private TextField passwordTextfield;
    /**This Method will check user credentials and log user login attempt to external file.
     * it will pop an error Alert if there is an unsuccessful user login attempt and change GUI to MainForm.
     * @param mouseEvent login button clicked*/
    public void loginButtonClicked(MouseEvent mouseEvent) throws SQLException,IOException {
        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();
        if (loginValidation()) {
            if(DBHelper.checkUserCredentials(username,password)){
                loginLogger.writeLoginLog("Username: "+username+" Is-A-Successful-login: True TimeStamp: "+ TimeHelper.timeStamp()+" "+ZoneId.systemDefault().getId());
                Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 880, 630);
                stage.setScene(scene);
                stage.show();
                isUpComingAppointmentIn15Min();
            }else {
                createAlert("InvalidInput","Warming","IncorrectUsername/Password");
                loginLogger.writeLoginLog("Username: "+username+" Is-A-Successful-login: False TimeStamp: "+ TimeHelper.timeStamp()+" "+ZoneId.systemDefault().getId());
            }
        } else {
            createAlert("InvalidInput","Warming", "MissingFiled");
        }
    }
    /**Override initialize() method for initializable interface.
     * This method set up LoginForm GUI base on user system default Locale and languageDropDown comboBox. Note:for this project only LoginForm will be translate with different language.
     * And there only two available language to be choose from(en/fr). All other Locale different than these two will be display in English.
     * @param url url.
     * @param resourceBundle  resourceBoudle*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLogger=new LogHelper();
        locationDisplayLabel.setText(ZoneId.systemDefault().getId() + "  ");
        ObservableList<String> languageList = FXCollections.observableArrayList();
        languageList.addAll("English", "Français");
        languageDropdown.setItems(languageList);
        languageDropdown.setTooltip(new Tooltip());
        if(Locale.getDefault().getDisplayLanguage().equals("Français")){
            languageDropdown.setValue("Français");
        }else {
            languageDropdown.setValue("English");
        }
        setLocaleDisplay();
    }
    /**This method will change login form GUI to default Locale. */
    private void setLocaleDisplay(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Com/Language",Locale.getDefault());
        usernameLabel.setText(resourceBundle.getString("Username"));
        passwordLabel.setText(resourceBundle.getString("Password"));
        languageLabel.setText(resourceBundle.getString("Language")+": ");
        loginButton.setText(resourceBundle.getString("Login"));
        languageDropdown.getTooltip().setText(resourceBundle.getString("Selectthelanguage"));
        }
    /**This method will set up languageDropdown comboBox with user selected values and change login form GUI to corresponding Locale.
     * It will set default Locale to user selected language.
     *@param actionEvent user selected item */
    public void languageDropdownSelected(ActionEvent actionEvent) {
        String selectedLanguage = languageDropdown.getValue();
        if(selectedLanguage.equals("Français")){
            Locale.setDefault(Locale.FRENCH);
            setLocaleDisplay();
        }
        if(selectedLanguage.equals("English")){
            Locale.setDefault(Locale.ENGLISH);
            setLocaleDisplay();
        }
    }
    /**This method validates user input.
     * it will pop an error Alert if there is no input for userTextfiled or passwordTextfield.
     * @return boolean return true or false*/
    private boolean loginValidation(){
        return (usernameTextfield.getText().isEmpty()||passwordTextfield.getText().isEmpty())?false:true;
    }
    /**This method create a pop up error Alert and display out put to default System Locale.
     * @param contentKey title key
     * @param headerKey header key
     * @param titleKey title key*/
    private void createAlert(String titleKey,String headerKey, String contentKey){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Com/Language",Locale.getDefault());
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(resourceBundle.getString(titleKey));
        errorAlert.setHeaderText(resourceBundle.getString(headerKey));
        errorAlert.setContentText(resourceBundle.getString(contentKey));
        errorAlert.show();
    }
    /**This method check if there is a appointment coming in next 15 minutes when user successfully Login and pop up information alert with appointment details.
     * It creates a current LocalDateTime value called now as reference point for all appointment start time.  It calls getAppointmentList to get all appointments in sqldatabase.
     * And filter it with Predicate. It will return false for start time from appointment is before the now valuable or start time is after now pulse 15 minutes and return
     * true for any other case.*/
    private void isUpComingAppointmentIn15Min() throws SQLException {
        LocalDateTime now = LocalDateTime.now();
        Alert informAlert = new Alert(Alert.AlertType.INFORMATION);
        String upcomingAppointment="";
        for (Appointment appointment:DBHelper.getAppointmentList().filtered(s->{
            if(s.getStart().isBefore(now)||s.getStart().isAfter(now.minusMinutes(15))){
                return false;
            }
            return true;
        })) {
            upcomingAppointment+="Appointment ID:  "+ appointment.getAppointmentID()+" Type: "+appointment.getType()+ " Start: "+appointment.getStartTime()+" End: "+appointment.getEndTime()+"\n";
        }
        if(upcomingAppointment.isEmpty()){
            informAlert.setTitle("Information");
            informAlert.setContentText("There is no up coming appointment in next 15 minutes");
            informAlert.show();
        }else {
            informAlert.setTitle("Information");
            informAlert.setContentText(upcomingAppointment);
            informAlert.show();
        }

    }

}

