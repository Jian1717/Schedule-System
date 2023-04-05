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


public class MainFormController implements Initializable {
    private ObservableList<String> appointmentFilterList;
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


    public void customerAddClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomerForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 570, 300);
        stage.setScene(scene);
        stage.show();
    }

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

    public void customerDeleteClicked(ActionEvent actionEvent) throws SQLException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Delete an appointment");
        confirmationAlert.setContentText("Do you want continue deleting selected customer?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!(customerTableView.getSelectionModel().getSelectedItem()==null)) {
                int totalNumberOfAppointmentWithCustomer=DBHelper.getCustomerAppointmentList(customerTableView.getSelectionModel().getSelectedItem().getCustomerID()).size();
                if(totalNumberOfAppointmentWithCustomer>0){
                    Alert confirmationAlert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert1.setTitle("Confirmation Dialog");
                    confirmationAlert1.setHeaderText("Warming");
                    confirmationAlert1.setContentText("This customer has "+totalNumberOfAppointmentWithCustomer+" appointment(s).  Do you want continue deleting selected customer? This action will delete all appointments that associated with this customer.");
                    Optional<ButtonType> result1 = confirmationAlert1.showAndWait();
                    if(result1.get()==ButtonType.OK){
                        DBHelper.deleteAllAppointmentFromACustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
                        setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList());
                    }else {
                        return;
                    }
                }
                DBHelper.deleteCustomer(customerTableView.getSelectionModel().getSelectedItem().getCustomerID());
                setCustomerTableView(customerTableView,DBHelper.getCustomerList());
            } else {
                Alert productComponentListIsNotEmpty = new Alert(Alert.AlertType.ERROR, "Please select a customer to be deleted");
                productComponentListIsNotEmpty.show();
            }
        }
    }

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

    public void appointmentAddClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddAppointmentForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 630, 440);
        stage.setScene(scene);
        stage.show();
    }

    public void appointmentDeleteClicked(MouseEvent mouseEvent) throws SQLException {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation Dialog");
        confirmationAlert.setHeaderText("Delete an appointment");
        confirmationAlert.setContentText("Do you want continue deleting selected appointment?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (!(appointmentTableView.getSelectionModel().getSelectedItem()==null)) {
            if (result.get() == ButtonType.OK) {
                if(DBHelper.deleteAppointment(appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID())==1){
                  Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
                  informationAlert.setTitle("Information");
                  informationAlert.setHeaderText("Successful Deleted");
                  informationAlert.setContentText("Appointment No."+appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID()+" ("+appointmentTableView.getSelectionModel().getSelectedItem().getType()+") is cancelled.");
                  informationAlert.show();
                  setAppointmentTableView(appointmentTableView,DBHelper.getAppointmentList());
                }
            }
        } else {
            Alert productComponentListIsNotEmpty = new Alert(Alert.AlertType.ERROR, "Please select an appointment to be deleted");
            productComponentListIsNotEmpty.show();
        }
    }

    public void appointmentComboBoxClicked(ActionEvent actionEvent) throws SQLException {
        appointmentFilter(appointmentComboBox.getValue());
    }

    public void monthlySummaryClicked(ActionEvent actionEvent) throws SQLException, IOException {
            setReportForm("Monthly Summary Report",ReportHelper.getTypeAppointReport(),actionEvent);
    }
    public void contactAppointmentReportClicked(ActionEvent actionEvent) throws SQLException, IOException {
        setReportForm("Contact Appointment Report",ReportHelper.getContactAppointmentReport(),actionEvent);
    }
    public void customerAppointmentReportClicked(ActionEvent actionEvent) throws SQLException, IOException {
        setReportForm("Customer Appointment Report",ReportHelper.getCustomerAppointmentReport(),actionEvent);
    }
    public void logOffClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        Stage stage = (Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerNode().getScene().getWindow();
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentFilterList = FXCollections.observableArrayList();
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

    private void setCustomerTableView(TableView<Customer> tableView,ObservableList<Customer> customersList){
        tableView.setItems(customersList);
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("FullAddress"));
    }

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
