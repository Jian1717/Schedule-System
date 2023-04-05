package Com.View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportFormController {
    @FXML
    private Label resultTitleLabel;
    @FXML
    private ScrollPane resultScrollPane;

    public void resultOKClicked(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 880, 630);
        stage.setScene(scene);
        stage.show();
    }
    public void setResultForm(String title,String content){
        resultTitleLabel.setText(title);
        resultScrollPane.setContent(new Text(content));
    }
}
