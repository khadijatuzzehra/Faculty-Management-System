package FrontEndFMS;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static FrontEndFMS.Controller.AlertBox;
public class HomeStudentController {
    @FXML private Button schButton;

    public void displayCourses(javafx.event.ActionEvent actionEvent) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = formatter.format(new Date());
        if (dateInString.compareTo("31-07-2021") >0){
            schButton.setStyle("-fx-background-color: Gray");
            AlertBox("Alert Box","Online Registration Closed");

        }else{
            try{
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("regCourses.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void ScholarshipForm(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("scholarshipApplication.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void close(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
