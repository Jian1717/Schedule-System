package Com.View_Controller;

import Com.DAO.DBHelper;
import Com.Model.Country;
import Com.Model.Customer;
import Com.Model.FirstLevelDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

/**AddCustomerFormController handle all the event, action and GUI components that happened in AddCustomerForm.*/
public class AddCustomerFormController implements Initializable {
    private boolean isModify=false;
    private String name;
    private String phone;
    private String address;
    private String postalCode;
    private int firstLevelDivisionId;

    @FXML
    private TextField customerIDTextField;
    @FXML
    private TextField customerNameTextField;
    @FXML
    private TextField customerPhoneTextField;
    @FXML
    private TextField customerAddressTextField;
    @FXML
    private TextField customerPostalCodeTextField;
    @FXML
    private ComboBox<FirstLevelDivision> customerFirstLevelDivisionComboBox;
    @FXML
    private ComboBox<Country> customerCountryComboBOX;
    /**This Method call backToMainForm method when mouseEvent detected.
     * User needs to confirm cancel action with confirmation alert.
     * @param mouseEvent cancel button clicked*/
    public void customerCancelClicked(MouseEvent mouseEvent) throws IOException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Cancel");
        confirmationAlert.setContentText("Are you sure if you want to process without saving current data? All the unsaved data will be lost.");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            backToMainForm(mouseEvent);
        }
    }
    /**This method add or update a customer when add button is clicked.
     * After successfully add or update a customer, it will call backToMainForm() method to change GUI into main form.
     * @param mouseEvent add button clicked*/
    public void customerAddButtonClicked(MouseEvent mouseEvent) throws SQLException, IOException {
        if(inputValidation()){
            if (isModify){
                getUserInput();
                if(DBHelper.updateCustomer(name, address, postalCode, phone, firstLevelDivisionId,Integer.valueOf(customerIDTextField.getText()))>0){
                    isModify=false;
                    backToMainForm(mouseEvent);
                }
            } else {
                getUserInput();
                if (DBHelper.insertCustomer(name, address, postalCode, phone, firstLevelDivisionId) > 0) {
                    backToMainForm(mouseEvent);
                }
            }
        }
    }
    /**This load the content in customerFirstLevelDivisionComboBox to corresponding country selected in customerCountryComBoBox.
     * @param actionEvent CountryComboBox is Selected*/
    public void CountrySelected(ActionEvent actionEvent) throws SQLException {
        customerFirstLevelDivisionComboBox.setItems(DBHelper.getFirstLevelDivisionDataList(customerCountryComboBOX.getSelectionModel().getSelectedItem().getCountryID()));
        customerFirstLevelDivisionComboBox.setVisibleRowCount(8);
    }
    /**Override initialize() method for initializable interface.
     * This method set up ComboBox's content in AddCustomerForm.
     * @param url url.
     * @param resourceBundle  resourceBoudle*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerCountryComboBOX.setItems(DBHelper.getCountryDataList());
            customerCountryComboBOX.setVisibleRowCount(8);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**This method preload AddCustomerForm with values from selected customer.
     * it will call getAppointment() method from DBhelper class to get information of selected customer.  And set correspond values in GUI component in addAppointmentForm.
     * There two lambda expression expression used in this method. Predicate getSelectedCustomerCountryDate takes a country object and get its country name.  And then compare it with selected customer country name.
     * it will return true for a match and false for a mismatch.  And second time I used Predicate functional interface is when setting up customerFirstLevelDivisionComboBox to selected customer's first level division value.
     * I called getItems() method on customerFirstLevelDivisionComboBox to get all the FirstLevelDivision data store in the comboBox.  I then I call the filter() method and pass Predicate as parameter.  It takes a FirstLevelDivision
     * and call getDivisionID() to get the associated DivisionID value.  Then it will compare value with selected customer. Returning true if there is a match or false for a mismatch. Last,setting the comboBox with first item in the filterList.
     * @param customerID selected appointment ID*/
    public void modifyCustomer(int customerID) throws SQLException {
        Customer selectCustomer = DBHelper.getCustomer(customerID);
        isModify=true;
        customerIDTextField.setText(String.valueOf(selectCustomer.getCustomerID()));
        customerPhoneTextField.setText(selectCustomer.getPhone());
        customerPostalCodeTextField.setText(selectCustomer.getPostalCode());
        customerNameTextField.setText(selectCustomer.getName());
        customerAddressTextField.setText(selectCustomer.getAddress());
        Predicate<Country> getSelectedCustomerCountryData = s-> {
            try {
                if(s.getCountryName().equals(DBHelper.getCountry(selectCustomer.getDivisionID()))){
                    return true;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
        };
        customerCountryComboBOX.setValue(customerCountryComboBOX.getItems().filtered(getSelectedCustomerCountryData).get(0));
        customerFirstLevelDivisionComboBox.setItems(DBHelper.getFirstLevelDivisionDataList(customerCountryComboBOX.getSelectionModel().getSelectedItem().getCountryID()));
        customerFirstLevelDivisionComboBox.setVisibleRowCount(8);
        customerFirstLevelDivisionComboBox.setValue(customerFirstLevelDivisionComboBox.getItems().filtered(s->{
            return s.getDivisionID()==selectCustomer.getDivisionID();
        }).get(0));
    }
    /**This method validates user input.
     * it will pop an error Alert if there is an invalid input.
     * @return boolean return true or false*/
    private boolean inputValidation(){
        String errorMessage="";
        if(customerAddressTextField.getText().isEmpty()){
            errorMessage+="Please fill out address \n";
        }
        if(customerNameTextField.getText().isEmpty()){
            errorMessage+="Please fill out name \n";
        }
        if(customerPostalCodeTextField.getText().isEmpty()){
            errorMessage+="Please fill out postal code \n";
        }
        if(customerPhoneTextField.getText().isEmpty()){
            errorMessage+="Please fill out phone \n";
        }
        if (customerCountryComboBOX.getSelectionModel().getSelectedItem()==null){
            errorMessage+="Please select a country \n";
        }
        if (customerFirstLevelDivisionComboBox.getSelectionModel().getSelectedItem()==null){
            errorMessage+="Please select a first level division";
        }
         if(errorMessage.isEmpty()){
             return true;
         }else {
             Alert errorAlert = new Alert(Alert.AlertType.ERROR);
             errorAlert.setTitle("Error");
             errorAlert.setHeaderText("Invalid User Input");
             errorAlert.setContentText(errorMessage);
             errorAlert.show();
             return false;
         }
    }
    /**This method read user input from AddCustomerForm.
     * it will save corresponding value to local variables*/
    private void getUserInput(){
        name=customerNameTextField.getText();
        phone=customerPhoneTextField.getText();
        address=customerAddressTextField.getText();
        postalCode=customerPostalCodeTextField.getText();
        firstLevelDivisionId=customerFirstLevelDivisionComboBox.getSelectionModel().getSelectedItem().getDivisionID();
    }
    /**This Method change GUI into main form.
     * @param mouseEvent cancel button clicked*/
    private void backToMainForm(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 630);
        stage.setScene(scene);
        stage.show();
    }

}
