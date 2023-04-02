package Com.View_Controller;
import Com.DAO.DBHelper;
import Com.Helper.LogHelper;
import Com.Helper.TimeHelper;
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
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;


public class LoginFormController implements Initializable {
    private ObservableList<String> languageList;
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

    public void loginButtonClicked(MouseEvent mouseEvent) throws SQLException,IOException {
        String username = usernameTextfield.getText();
        String password = passwordTextfield.getText();
        if (loginValidation()) {
            if(DBHelper.checkUserCredentials(username,password)){
                loginLogger.writeLoginLog("Username: "+username+" Is-A-Successful-login: True TimeStamp: "+ TimeHelper.timeStamp());
                Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 860, 630);
                stage.setScene(scene);
                stage.show();
            }else {
                createAlert("InvalidInput","Warming","IncorrectUsername/Password");
                loginLogger.writeLoginLog("Username: "+username+" Is-A-Successful-login: False TimeStamp: "+ TimeHelper.timeStamp());
            }
        } else {
            createAlert("InvalidInput","Warming", "MissingFiled");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginLogger=new LogHelper();
        locationDisplayLabel.setText(ZoneId.systemDefault().getId() + "  ");
        languageList = FXCollections.observableArrayList();
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
    private void setLocaleDisplay(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Com/Language",Locale.getDefault());
        usernameLabel.setText(resourceBundle.getString("Username"));
        passwordLabel.setText(resourceBundle.getString("Password"));
        languageLabel.setText(resourceBundle.getString("Language")+": ");
        loginButton.setText(resourceBundle.getString("Login"));
        languageDropdown.getTooltip().setText(resourceBundle.getString("Selectthelanguage"));
        }
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
    private boolean loginValidation(){
        return (usernameTextfield.getText().isEmpty()||passwordTextfield.getText().isEmpty())?false:true;
    }
    private void createAlert(String titleKey,String headerKey, String contentKey){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Com/Language",Locale.getDefault());
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(resourceBundle.getString(titleKey));
        errorAlert.setHeaderText(resourceBundle.getString(headerKey));
        errorAlert.setContentText(resourceBundle.getString(contentKey));
        errorAlert.show();
    }

}

