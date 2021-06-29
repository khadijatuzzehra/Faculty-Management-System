package FrontEndFMS;

import FacultyManagement.Accountant;
import FacultyManagement.Courses;
import FacultyManagement.Teaches;
import javafx.application.Preloader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import static FrontEndFMS.Controller.AlertBox;

public class TeacherController {
    public Button FData;
    public Button FData1;
    @FXML private PasswordField txtPassword;@FXML private TextField emailId;
    @FXML private TextField empName;@FXML private TextField UserAge;
    @FXML private TextField contactNum;@FXML private TextField empQual;
    //LABELS//
    @FXML private Label regStatus;@FXML private Label loginStatus;

    //LOGIN FIELDS//
    @FXML private PasswordField loginPassword;
    @FXML private TextField loginEmail;

    //LEAVE APPLICATION//
    @FXML private TextField leaveApplicantMail;@FXML private TextField leaveReason;
    @FXML private DatePicker leaveStartDate;@FXML private DatePicker leaveEndDate;
    @FXML private PasswordField leaveConfirmation;@FXML private Label applicationStatus;
    private static String currInst="";

    //GPA//
    @FXML TableView<Teaches> tableIda ;@FXML TableColumn<Teaches, String> studentId;
    @FXML TableColumn<Teaches, String> studentName;
    @FXML TableColumn<Teaches, String> courseId;
    @FXML TableColumn<Teaches, Double> gpa;
    private ObservableList<Teaches> dataA;

    //ATTENDANCE//
    @FXML TableView<Courses> tableId ;
    @FXML TableColumn<Courses, String> StudentID;
    @FXML TableColumn<Courses, String> StudentName;
    @FXML TableColumn<Courses, String> CourseId;
    @FXML TableColumn<Courses, Double> StudentAttn;
    private ObservableList<Courses> data;

    public void loadGPA(ActionEvent event){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.dataA = FXCollections.observableArrayList();
            PreparedStatement execution=conn.prepareStatement("select registers.student_sid,student.name,registers.courses_crsid,registers.gpa from registers,student where registers.instructor=? and student.sid=registers.student_sid and student.sid=registers.student_sid");
            execution.setString(1,currInst);
            ResultSet rs1=execution.executeQuery();
            while (rs1.next()) {
                this.dataA.add(new Teaches(rs1.getString(1), rs1.getString(2), rs1.getString(3),rs1.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.studentId.setCellValueFactory(new PropertyValueFactory<Teaches, String>("sID"));
        this.studentName.setCellValueFactory(new PropertyValueFactory<Teaches, String>("sName"));
        this.courseId.setCellValueFactory(new PropertyValueFactory<Teaches,String>("cID"));
        this.gpa.setCellValueFactory(new PropertyValueFactory<Teaches, Double>("gpa"));
        this.tableIda.setItems(this.dataA);
    }

    public void setStudentGPA(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hireAccountant.fxml"));
        Controller controller = loader.getController();
        if(tableIda.getSelectionModel().getSelectedItem()==null)
            AlertBox("Error","Kindly Select a row");
        else{
            try {
                String sId=tableIda.getSelectionModel().getSelectedItem().getsID();
                String courseId=tableIda.getSelectionModel().getSelectedItem().getcID();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                //TAKE USER INPUT//
                TextInputDialog inputDialog=new TextInputDialog("Enter GPA (1-4)");
                inputDialog.initStyle(StageStyle.TRANSPARENT);
                inputDialog.initStyle(StageStyle.UNDECORATED);
                inputDialog.show();
                Button confirmation=(Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
                confirmation.setOnAction((ActionEvent) -> {
                    try {
                        if (inputDialog.getEditor().getText()!=null && (!inputDialog.getEditor().getText().matches("^[a-zA-Z ]*$"))){
                            String GPA = inputDialog.getEditor().getText();
                            double gpaVal=Double.parseDouble(GPA);
                            if (gpaVal<1 || gpaVal >4){
                                AlertBox("Alert", "Not Valid");
                            }
                            else{
                                PreparedStatement setAttn = conn.prepareStatement("update registers set GPA=? where student_sid=? and courses_crsid=?");
                                setAttn.setDouble(1, gpaVal);
                                setAttn.setString(2, sId);
                                setAttn.setString(3, courseId);
                                setAttn.executeQuery();
                                AlertBox("Alert", "GPA updated");
                            }
                        }
                        else{
                            AlertBox("Alert", "Please enter the new salary");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void loadAttendance(ActionEvent event){
        try {
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
            this.data = FXCollections.observableArrayList();
            PreparedStatement execution=conn.prepareStatement("select registers.student_sid,student.name,registers.courses_crsid,registers.attendance from registers,student where registers.instructor=? and student.sid=registers.student_sid and student.sid=registers.student_sid");
            execution.setString(1,currInst);
            ResultSet rs1=execution.executeQuery();
            while (rs1.next()) {
                this.data.add(new Courses(rs1.getString(1), rs1.getString(2), rs1.getString(3),rs1.getDouble(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.StudentID.setCellValueFactory(new PropertyValueFactory<Courses, String>("sID"));
        this.StudentName.setCellValueFactory(new PropertyValueFactory<Courses, String>("sName"));
        this.CourseId.setCellValueFactory(new PropertyValueFactory<Courses,String>("cID"));
        this.StudentAttn.setCellValueFactory(new PropertyValueFactory<Courses, Double>("attn"));
        this.tableId.setItems(this.data);
    }


    public void Attendance(ActionEvent actionEvent){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hireAccountant.fxml"));
        Controller controller = loader.getController();
        if(tableId.getSelectionModel().getSelectedItem()==null)
            AlertBox("Error","Kindly Select a row");
        else{
            try {
                String sId=tableId.getSelectionModel().getSelectedItem().getsID();
                String courseId=tableId.getSelectionModel().getSelectedItem().getcID();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                //trying something new//
                TextInputDialog inputDialog=new TextInputDialog("Enter Attendance (0-100)");
                inputDialog.initStyle(StageStyle.TRANSPARENT);
                inputDialog.initStyle(StageStyle.UNDECORATED);
                inputDialog.show();
                Button confirmation=(Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
                confirmation.setOnAction((ActionEvent) -> {
                    try {
                        if (inputDialog.getEditor().getText()!=null && (!inputDialog.getEditor().getText().matches("^[a-zA-Z ]*$"))){
                            String attn = inputDialog.getEditor().getText();
                            double attendance=Double.parseDouble(attn);
                            if (attendance<0 || attendance >100){
                                AlertBox("Alert", "Not Valid");
                            }
                            else{
                                PreparedStatement setAttn = conn.prepareStatement("update registers set attendance=? where student_sid=? and courses_crsid=?");
                                setAttn.setDouble(1, attendance);
                                setAttn.setString(2, sId);
                                setAttn.setString(3, courseId);
                                setAttn.executeQuery();
                                AlertBox("Alert", "Attendance Update");
                            }
                        }
                        else{
                            AlertBox("Alert", "Please enter the new salary");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });

            }catch (Exception e){
                e.printStackTrace();
            }
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
    public void homeTeacher(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeTeacher.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void markAttn(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("markAttendance.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void grading(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("setGPA.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void applyTeacher(javafx.event.ActionEvent event) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("applyTeacher.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String countEmp(){
        String empCount="";
        try {
            Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","HR","HR");
            PreparedStatement P2=conn.prepareStatement("SELECT MAX(to_number(EMPID)) FROM EMPLOYEE" );
            ResultSet resultSet2=P2.executeQuery();
            int a=0;
            while (resultSet2.next()) {
                a=resultSet2.getInt(1)+1;
            }
            empCount=Integer.toString(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        return empCount;
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public void RegisterUser(javafx.event.ActionEvent event){
        try {

            if (emailId.getText().isEmpty() || txtPassword.getText().isEmpty() || UserAge.getText().isEmpty() || empName.getText().isEmpty()){
                AlertBox("Registration Error","Empty Text field");
                regStatus.setText("Empty Text field");
            }
            boolean emailCheck=isValidEmailAddress(emailId.getText());
            if (!emailCheck || (txtPassword.getText().length() < 8) || (txtPassword.getText().length() > 10) || (UserAge.getText().matches("^[a-zA-Z ]*$")) || (contactNum.getText().matches("^[a-zA-Z ]*$")) || (!empQual.getText().matches("^[a-zA-Z ]*$")) || (!empName.getText().matches("^[a-zA-Z ]*$"))){
                AlertBox("Registration Error","Invalid Credentials");
                regStatus.setText("Improper email or password format");
            }
            else {
                String emailText = emailId.getText();
                String name = empName.getText();
                String age = UserAge.getText();
                int userAge = Integer.parseInt(age);
                String contact = contactNum.getText();
                String qualification = empQual.getText();
                String password = txtPassword.getText();
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement P1 = conn.prepareStatement("select EMPID from HR.EMPLOYEE WHERE EMAIL IN ?");
                P1.setString(1, emailText);
                ResultSet resultSet = P1.executeQuery();
                if (resultSet.next() == false) {
                    int a = 0;
                    String empId = countEmp();
                    PreparedStatement insertEmp = conn.prepareStatement("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    insertEmp.setString(1, empId);
                    insertEmp.setString(2, emailText);
                    insertEmp.setString(3, name);
                    insertEmp.setInt(4, 1500000);
                    insertEmp.setString(5, contact);
                    insertEmp.setInt(6, userAge);
                    insertEmp.setString(7, qualification);
                    insertEmp.setString(8, password);
                    insertEmp.executeQuery();

                    PreparedStatement insertAccountant = conn.prepareStatement("insert into teacher values (? ,'N',null, null)");
                    insertAccountant.setString(1, empId);
                    ResultSet resultSet3 = insertAccountant.executeQuery();
                    Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginTeacher.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                }
                else {
                    AlertBox("Registration Error","User Already Exists");
                }
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    public void LoginTeacher(ActionEvent event){
        try {
            if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()) {
                AlertBox("Registration Error", "Empty Text field");
                loginStatus.setText("Empty Text field");
            }
            else {
                String emailLogin=loginEmail.getText();
                String passwordLogin=loginPassword.getText();
                Connection LoginConnect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement P1 = LoginConnect.prepareStatement("select email,password from HR.EMPLOYEE WHERE EMAIL IN ?");
                P1.setString(1, emailLogin);
                PreparedStatement P2 = LoginConnect.prepareStatement("select hire_status from teacher where empid=(select empid from employee where employee.email= ?)");
                P2.setString(1, emailLogin);
                ResultSet loginCheck = P1.executeQuery();
                ResultSet loginCheck2 = P2.executeQuery();
                PreparedStatement P3 = LoginConnect.prepareStatement("select password from employee where empid in(select empid from teacher where empid in (select empid from employee where email=?))");
                P3.setString(1, emailLogin);
                ResultSet loginCheck3 = P3.executeQuery();

                PreparedStatement P4 = LoginConnect.prepareStatement("select name from employee where employee.email=?");
                P4.setString(1, emailLogin);
                ResultSet finalVar = P4.executeQuery();
                while (finalVar.next()){
                    currInst=finalVar.getString(1);
                }

                if (loginCheck.next() == false || loginCheck3.next()== false) {
                    AlertBox("Login Error", "No matched data");
                    loginStatus.setText("New User? Register now");
                }
                while (loginCheck2.next()){
                    if (loginCheck2.getString(1).equals("Y")) {
                        if (loginCheck.getString(2).equals(passwordLogin)){
                            Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeTeacher.fxml"));
                            Scene tableViewScene = new Scene(tableViewParent);
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(tableViewScene);
                            window.show();
                        }
                        else {
                            AlertBox("Login Error", "Invalid Password");
                            loginStatus.setText("Invalid Login Details");
                        }
                    } else {
                        AlertBox("Login Error", "No matched data");
                        loginStatus.setText("Sorry, You are not hired now.");
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showLeaveApplication(javafx.event.ActionEvent actionEvent) {
        try{
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("teacherLeaveApplication.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
            System.out.println(currInst);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //LEAVE APPLICATION//
    public String countLeaveId(){
        String LeaveIdCount="";
        try {
            Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","HR","HR");
            PreparedStatement LeaveId=conn.prepareStatement("SELECT MAX(to_number(LEAVEID)) FROM LEAVE" );
            ResultSet resultLeaveId= LeaveId.executeQuery();
            int a=0;
            while (resultLeaveId.next()) {
                a=resultLeaveId.getInt(1)+1;
            }
            LeaveIdCount=Integer.toString(a);
        }catch (Exception e){
            e.printStackTrace();
        }
        return LeaveIdCount;
    }
    public void submitLeaveApplication(ActionEvent event){
        if (leaveApplicantMail.getText().isEmpty() || leaveReason.getText().isEmpty() || leaveStartDate.getValue()==null || leaveEndDate.getValue()==null){
            AlertBox("Login Error", "Empty Data Fields");
            applicationStatus.setText("Fill in the text fields");
        }
        else{
            LocalDate sDate = leaveStartDate.getValue();
            String formattedStartDate = sDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            LocalDate eDate = leaveEndDate.getValue();
            String formattedEndDate = eDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            String applicantMail=leaveApplicantMail.getText();
            String leaveRes=leaveReason.getText();
            String leaveAppEmpID="";

            try {
                Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "HR", "HR");
                PreparedStatement lempid=conn.prepareStatement("select empid from employee where email=?");
                PreparedStatement lempid2=conn.prepareStatement("select password from employee where email=?");
                lempid.setString(1,applicantMail);
                lempid2.setString(1,applicantMail);
                ResultSet getEmp=lempid.executeQuery();
                ResultSet getPass=lempid2.executeQuery();
                PreparedStatement check=conn.prepareStatement("select reason from leave_applicants where leave_applicants_id=?");
                if ((getEmp.next() != false) && getPass.next()!=false) {
                    check.setString(1,getEmp.getString(1));
                    ResultSet checkRes=check.executeQuery();
                    if (getPass.getString(1).equals(leaveConfirmation.getText()) && (checkRes.next()==false)){
                        leaveAppEmpID = getEmp.getString(1);
                        PreparedStatement pStat2 = conn.prepareStatement("insert into LEAVE_APPLICANTS (LEAVE_APPLICANTS_ID,REASON,START_DATE,END_DATE,TEACHER_EMPID,ACCOUNTANT_EMPID) values (?, ?, TO_DATE(? , 'DD/MM/YYYY'),TO_DATE(? , 'DD/MM/YYYY'),null,null)");
                        pStat2.setString(1, leaveAppEmpID);
                        pStat2.setString(2,leaveRes);
                        pStat2.setString(3, formattedStartDate);
                        pStat2.setString(4, formattedEndDate);
                        pStat2.executeQuery();
                        String leaveId=countLeaveId();
                        PreparedStatement pStat3 = conn.prepareStatement("insert into LEAVE values (?, ?, ?, ?)");
                        pStat3.setString(1,leaveId);
                        pStat3.setString(2,"N");
                        pStat3.setString(3,null);
                        pStat3.setString(4,leaveAppEmpID);
                        pStat3.executeQuery();

                        Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeTeacher.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(tableViewScene);
                        window.show();
                    }
                    else {
                        AlertBox("Submission Error", "Matched Data Not Found or Already Submitted");
                        applicationStatus.setText("Check your password again");
                    }
                }
                else {
                    AlertBox("Submission Error", "Matched Data Not Found");
                    applicationStatus.setText("Check your email again");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
