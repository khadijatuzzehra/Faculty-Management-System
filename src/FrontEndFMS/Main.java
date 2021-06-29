package FrontEndFMS;

import FacultyManagement.Accountant;
import FacultyManagement.accountantDisplay;
import FacultyManagement.teacherDisplay;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    public Button FData21;
    @FXML
    TableView<accountantDisplay> tableID;
    @FXML TableColumn<accountantDisplay, String> accountantID;
    @FXML TableColumn<accountantDisplay, String> accountantName;
    @FXML TableColumn<accountantDisplay, String> accountantStatus;
    @FXML TableColumn<accountantDisplay,String> accountantQualification;
    private ObservableList<accountantDisplay> data2;

    @FXML
    TableView<teacherDisplay> tableIDT;
    @FXML TableColumn<teacherDisplay, String> tID;
    @FXML TableColumn<teacherDisplay, String> tName;
    @FXML TableColumn<teacherDisplay, String> tStatus;
    @FXML TableColumn<teacherDisplay,String> tQualification;
    private ObservableList<teacherDisplay> data;

    public void loadAccountant(javafx.event.ActionEvent event){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data2 = FXCollections.observableArrayList();
            ResultSet rs1 = conn.createStatement().executeQuery("select employee.empid,employee.name,accountant.hire_status,employee.qualification from HR.accountant,HR.employee where accountant.empid=employee.empid");
            while (rs1.next()) {
                this.data2.add(new accountantDisplay(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.accountantID.setCellValueFactory(new PropertyValueFactory<accountantDisplay, String>("employeeId"));
        this.accountantName.setCellValueFactory(new PropertyValueFactory<accountantDisplay, String>("employeeName"));
        this.accountantStatus.setCellValueFactory(new PropertyValueFactory<accountantDisplay, String>("hireStatus"));
        this.accountantQualification.setCellValueFactory(new PropertyValueFactory<accountantDisplay, String>("qualification"));
        this.tableID.setItems(this.data2);
    }

    public void loadTeacher(javafx.event.ActionEvent event){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data = FXCollections.observableArrayList();
            ResultSet rs1 = conn.createStatement().executeQuery("select employee.empid,employee.name,teacher.hire_status,employee.qualification from HR.teacher,HR.employee where teacher.empid=employee.empid");
            while (rs1.next()) {
                this.data.add(new teacherDisplay(rs1.getString(1), rs1.getString(2), rs1.getString(3), rs1.getString(4)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.tID.setCellValueFactory(new PropertyValueFactory<teacherDisplay, String>("employeeId"));
        this.tName.setCellValueFactory(new PropertyValueFactory<teacherDisplay, String>("employeeName"));
        this.tStatus.setCellValueFactory(new PropertyValueFactory<teacherDisplay, String>("hireStatus"));
        this.tQualification.setCellValueFactory(new PropertyValueFactory<teacherDisplay, String>("qualification"));
        this.tableIDT.setItems(this.data);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Faculty Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void Status(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("StatusWindow.fxml"));
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
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loginAdmin(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginAdmin.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loginStudent(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginStudent.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loginAccountant(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginAccountant.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loginTeacher(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginTeacher.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showACC(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("showAccountants.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showATCHR(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("showTeachers.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showADM(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("adminDisplay.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showALL(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("ViewAll.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
