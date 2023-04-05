package Com.View_Controller;

import Com.DAO.DBHelper;
import Com.Helper.ReportHelper;
import Com.Model.Appointment;
import Com.Model.Customer;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**MainFormController handle all the event, action and GUI components that happened in MainForm. */
public class MainFormController implements Initializable {
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private ComboBox<String> appointmentComboBox;
    @FXML
    private TableColumn<Customer,Integer> customerIDCol;
    @FXML
    private TableColumn<Customer,String> customerNameCol;
    @FXML
    private TableColumn<Customer,String> customerPhoneCol;
    @FXML
    private TableColumn<Customer,String> customerAddressCol;
    @FXML
    private TableColumn<Appointment,Integer> appointmentIDCol;
    @FXML
    private TableColumn<Appointment,Integer> appointmentUserIDCol;
    @FXML
    private TableColumn<Appointment,Integer> appointmentCustomerIDCol;
    @FXML
    private TableColumn<Appointment,String> appointmentTypeCol;
    @FXML
    private TableColumn<Appointment,String> appointmentTitleCol;
    @FXML
    private TableColumn<Appointment,String> appointmentLocationCol;
    @FXML
    private TableColumn<Appointment,String> appointmentDescriptionCol;
    @FXML
    private TableColumn<Appointment,String> appointmentStartTimeCol;
    @FXML
    private TableColumn<Appointment,String> appointmentEndTimeCol;
    @FXML
    private TableColumn<Appointment,Integer> appointmentContactCol;
    /**This Method change GUI into AddCustomerForm.
     * @param mouseEvent customer Add button clicked*/
    public void customerAddClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 570, 300);
        stage.setScene(scene);
        stage.show();
    }
    /**This Method change GUI into and preload AddCustomerForm with selected customer's values.
     * @param mouseEvent customer modify button clicked*/
    public void customerModifyClicked(MouseEvent mouseEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddCustomerForm.fxml"));
        loader.load();
        AddCustomerFormController modifyController = loader.getController();
        modifyController.modifyCustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.getRoot(), 570, 300);
        stage.setScene(scene);
        stage.show();
    }
    /**This method deleted selected customer from SQLdatabase.
     * User needs to confirm delete action with confirmation alert. If there is appointment associated with selected customer, this method will
     * deleting all appointment before deleting customer. Information alert is display when customer/associated appointment is successfully deleted.
     * Error alert is display when no user selection.
     * @param actionEvent delete button clicked*/
    public void customerDeleteClicked(ActionEvent actionEvent) throws SQLException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Delete an appointment");
        confirmationAlert.setContentText("Do you want continue deleting selected customer?");
        if (!(customerTableView.getSelectionModel().getSelectedItem()==null)) {
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                StringBuffer appointmentAssociated=new StringBuffer();
                DBHelper.getCustomerAppointmentList(customerTableView.getSelectionModel().getSelectedItem().getCustomerID()).forEach(s->{
                    appointmentAssociated.append("Appointment ID: "+s.getAppointmentID()+" Type: "+s.getType()+"\n");
                });
                if(!appointmentAssociated.toString().isEmpty()){
                    Alert confirmationAlert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert1.setTitle("Confirmation Dialog");
                    confirmationAlert1.setHeaderText("Warming");
                    confirmationAlert1.setContentText("This customer has "+appointmentAssociated.toString().split("\n").length +" appointment(s).  Do you want continue deleting selected customer? This action will delete All appointments that associated with this customer.");
                    Optional<ButtonType> result1 = confirmationAlert1.showAndWait();
                    if(result1.get()==ButtonType.OK){
                        DBHelper.deleteAllAppointmentFromACustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
                        setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList());
                        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                        informationAlert.setTitle("Information");
                        informationAlert.setHeaderText("Customer Successful Deleted");
                        informationAlert.setContentText("All following appointment are deleted:\n"+appointmentAssociated);
                        informationAlert.show();
                    }else {
                        return;
                    }
                }
                DBHelper.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
                setCustomerTableView(customerTableView,DBHelper.getCustomerList());
            }
        } else {
                Alert productComponentListIsNotEmpty = new Alert(Alert.AlertType.ERROR, "Please select a customer to be deleted");
                productComponentListIsNotEmpty.show();
            }
        }
    /**This Method change GUI into and preload AddAppointmentForm with selected appointment's values.
     * @param mouseEvent appointment modify button clicked*/
    public void appointmentModifyClicked(MouseEvent mouseEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddAppointmentForm.fxml"));
        loader.load();
        AddAppointmentFormController modifyController = loader.getController();
        modifyController.modifyAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.getRoot(), 630, 440);
        stage.setScene(scene);
        stage.show();
    }
    /**This Method change GUI into AddAppointmentForm.
     * @param mouseEvent appointment Add button clicked*/
    public void appointmentAddClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddAppointmentForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 630, 440);
        stage.setScene(scene);
        stage.show();
    }
    /**This method deleted selected appointment from SQLdatabase.
     * User needs to confirm delete action with confirmation alert. Information alert is display when appointment is successfully deleted.
     * Error alert is display when no user selection.
     * @param mouseEvent appointment delete button clicked*/
    public void appointmentDeleteClicked(MouseEvent mouseEvent) throws SQLException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Delete an appointment");
        confirmationAlert.setContentText("Do you want continue deleting selected appointment?");
        if (!(appointmentTableView.getSelectionModel().getSelectedItem()==null)) {
            Optional<ButtonType> result = confirmationAlert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if(DBHelper.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID())==1){
                  Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                  informationAlert.setTitle("Information");
                  informationAlert.setHeaderText("Successful Deleted");
                  informationAlert.setContentText("Appointment No."+appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID()+" ("+appointmentTableView.getSelectionModel().getSelectedItem().getType()+") is deleted.");
                  informationAlert.show();
                  setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList());
                }
            }
        } else {
            Alert productComponentListIsNotEmpty = new Alert(Alert.AlertType.ERROR, "Please select an appointment to be deleted");
            productComponentListIsNotEmpty.show();
        }
    }
    /**This method change appointmentTableView with user selected filter.
     *@param actionEvent appointment filter */
    public void appointmentComboBoxClicked(ActionEvent actionEvent) throws SQLException {
        appointmentFilter(appointmentComboBox.getValue());
    }
    /**This Method change GUI into ReportForm and preload it as monthly summary report.
     * @param actionEvent monthly summary menu item clicked*/
    public void monthlySummaryClicked(ActionEvent actionEvent) throws SQLException, IOException {
            setReportForm("Monthly Summary Report",ReportHelper.getTypeAppointReport(),actionEvent);
    }
    /**This Method change GUI into ReportForm and preload it as contact appointment report.
     * @param actionEvent customer contact report menu item clicked*/
    public void contactAppointmentReportClicked(ActionEvent actionEvent) throws SQLException, IOException {
        setReportForm("Contact Appointment Report",ReportHelper.getContactAppointmentReport(),actionEvent);
    }
    /**This Method change GUI into ReportForm and preload it as customer appointment report.
     * @param actionEvent customer appointment report menu item clicked*/
    public void customerAppointmentReportClicked(ActionEvent actionEvent) throws SQLException, IOException {
        setReportForm("Customer Appointment Report",ReportHelper.getCustomerAppointmentReport(),actionEvent);
    }
    /**This Method change GUI into LoginForm.
     * @param actionEvent logoff menu item clicked*/
    public void logOffClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerNode().getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }
    /**Override initialize() method for initializable interface.
     * This method set up initial value for appointmentComboBox, customerTableView and appointmentTableView. Assigning property value factory to TableColumn.
     * @param url url.
     * @param resourceBundle  resourceBoudle*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> appointmentFilterList = FXCollections.observableArrayList();
        appointmentFilterList.addAll("All","Month","Week");
        appointmentComboBox.setItems(appointmentFilterList);
        appointmentComboBox.setTooltip(new Tooltip("please select a filter"));
        appointmentComboBox.setValue("All");
        try {
            setCustomerTableView(customerTableView, DBHelper.getCustomerList());
            setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**This method set up customerTableView and CellValueFactory for tableColumn.
     *@param customersList list of all customers
     *@param tableView customer tableview*/
    private void setCustomerTableView(TableView<Customer> tableView,ObservableList<Customer> customersList){
        tableView.setItems(customersList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("FullAddress"));
    }
    /**This method set up appointmentTableView and CellValueFactory for tableColumn.
     *@param appointmentsList list of all customers
     *@param tableView appointment tableview*/
    private void setAppointmentTableView(TableView<Appointment> tableView, ObservableList<Appointment> appointmentsList){
        tableView.setItems(appointmentsList);
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("ContactID"));
        appointmentCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        appointmentUserIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }
    /**This method filter appointmentTableView in week, month and all view depends on user selection.
     * I write a switch statement for each case. This method creates a current LocalDateTime value called now as reference point for all appointment start time.
     * Default case will be display all appointment(No filter need). For week case, appointmentFilter() calls getAppointmentList to get all appointments in sqldatabase.
     * And filter it with Predicate. It will return false for start time from appointment is before the now valuable or start time is after now pulse 7 days and return
     * true for any other case. And for month case, it's similar process as week case.  It compares start time of an appointment to now pulse 1 month instead of now pulse 7 days.
     * */
    private void appointmentFilter(String filter) throws SQLException {
        LocalDateTime now=LocalDateTime.now();
        switch (filter){
            case "Week":
                setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList().filtered(s->{
                    if(s.getStart().isBefore(now)||s.getStart().isAfter(now.plusDays(7))){
                        return false;
                    }
                    return true;
                }));
                break;
            case "Month":
                setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList().filtered(s->{
                    if(s.getStart().isBefore(now)||s.getStart().isAfter(now.plusMonths(1))){
                        return false;
                    }
                    return true;
                }));
                break;
            default:
                setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList());
        }
    }
    /**This Method preload ReportForm to its corresponding report type.
     * @param title report title
     * @param content report detail
     * @param actionEvent report menu item clicked*/
    private void setReportForm(String title, String content,ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ReportForm.fxml"));
        loader.load();
        ReportFormController modifyController = loader.getController();
        modifyController.setResultForm(title,content);
        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentMenu().getParentPopup().getOwnerNode().getScene().getWindow();
        Scene scene = new Scene(loader.getRoot(), 550, 400);
        stage.setScene(scene);
        stage.show();
    }
}
