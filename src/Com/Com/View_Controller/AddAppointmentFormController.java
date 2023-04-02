package Com.View_Controller;

import Com.DAO.DBHelper;
import Com.Model.Appointment;
import Com.Model.Contact;
import Com.Model.Customer;
import Com.Model.User;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddAppointmentFormController implements Initializable {
    private boolean isModify=false;
    private String title;
    private String location;
    private String description;
    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int userID;
    private int customerID;
    private int contactID;
    @FXML
    private TextField appointmentIDTextField;
    @FXML
    private TextField appointmentTitleTextField;
    @FXML
    private TextField appointmentDescriptionTextField;
    @FXML
    private TextField appointmentTypeTextField;
    @FXML
    private TextField appointmentStartTimeTextField;
    @FXML
    private TextField appointmentEndTimeTextField;
    @FXML
    private TextField appointmentLocationTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<User> userComboBox;
    @FXML
    private ComboBox<Contact> contactComboBox;

    public void appointmentAddButtonClicked(MouseEvent mouseEvent) throws SQLException, IOException {
        if(inputValidation()){
            if(isModify){
                getUserInput();
                if(DBHelper.updateAppointment(title,description,type,location,startDate,endDate,userID,customerID,contactID,Integer.parseInt(appointmentIDTextField.getText()))>0){
                    isModify=false;
                    backToMainForm(mouseEvent);
                }
            }else {
                getUserInput();
                if(DBHelper.insertAppointment(title,description,type,location,startDate,endDate,userID,customerID,contactID)>0){
                    backToMainForm(mouseEvent);
                }

            }
        }
    }

    public void appointmentCancelButtonClicked(MouseEvent mouseEvent) throws IOException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Cancel");
        confirmationAlert.setContentText("Are you sure if you want to process without saving current data? All the unsaved data will be lost.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            backToMainForm(mouseEvent);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerComboBox.setItems(DBHelper.getCustomerList());
            userComboBox.setItems(DBHelper.getUserDataList());
            contactComboBox.setItems(DBHelper.getContactDataList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private boolean inputValidation(){
        String errorMessage="";
        if(appointmentDescriptionTextField.getText().isEmpty()){
            errorMessage+="please fill out description \n";
        }
        if(appointmentTitleTextField.getText().isEmpty()){
            errorMessage+="please fill out title \n";
        }
        if(appointmentTypeTextField.getText().isEmpty()){
            errorMessage+="please fill out type \n";
        }
        if(appointmentLocationTextField.getText().isEmpty()){
            errorMessage+="please fill out location \n";
        }
        if(startDatePicker.getValue()==null){
            errorMessage+="please fill out start date \n";
        }
        if(appointmentStartTimeTextField.getText().isEmpty()){
            errorMessage+="please fill out start time \n";
        }
        if(endDatePicker.getValue()==null){
            errorMessage+="please fill out end date \n";
        }
        if(appointmentEndTimeTextField.getText().isEmpty()){
            errorMessage+="please fill out end time \n";
        }
        if (customerComboBox.getSelectionModel().getSelectedItem()==null){
            errorMessage+="Please select a customer \n";
        }
        if (userComboBox.getSelectionModel().getSelectedItem()==null){
            errorMessage+="Please select a user \n";
        }
        if (contactComboBox.getSelectionModel().getSelectedItem()==null){
            errorMessage+="Please select a contact \n";
        }
        if(errorMessage.isEmpty()){
            return true;
        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid User Input");
            errorAlert.setContentText(errorMessage);
            errorAlert.show();
            return false;
        }
    }
    private void getUserInput(){
        title=appointmentTitleTextField.getText();
        location=appointmentLocationTextField.getText();
        type=appointmentTypeTextField.getText();
        description=appointmentDescriptionTextField.getText();
        startDate=LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(appointmentStartTimeTextField.getText()));
        endDate=LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(appointmentEndTimeTextField.getText()));
        userID=Integer.valueOf(userComboBox.getValue().getUserID());
        contactID=Integer.valueOf(contactComboBox.getValue().getId());
        customerID=Integer.valueOf(customerComboBox.getValue().getCustomerID());

    }
    public void modifyAppointment(int appointmentID) throws SQLException {
        isModify=true;
        Appointment selectedAppointment=DBHelper.getAppointment(appointmentID);
        appointmentLocationTextField.setText(selectedAppointment.getLocation());
        appointmentDescriptionTextField.setText(selectedAppointment.getDescription());
        appointmentIDTextField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        appointmentTypeTextField.setText(selectedAppointment.getType());
        appointmentTitleTextField.setText(selectedAppointment.getTitle());
        appointmentEndTimeTextField.setText(selectedAppointment.getEnd().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        appointmentStartTimeTextField.setText(selectedAppointment.getStart().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        startDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        endDatePicker.setValue(selectedAppointment.getEnd().toLocalDate());
        contactComboBox.setValue(DBHelper.getContact(selectedAppointment.getContactID()));
        userComboBox.setValue(DBHelper.getUser(selectedAppointment.getUserID()));
        customerComboBox.setValue(DBHelper.getCustomer(selectedAppointment.getCustomerID()));
    }
    private void backToMainForm(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 860, 630);
        stage.setScene(scene);
        stage.show();
    }
}
